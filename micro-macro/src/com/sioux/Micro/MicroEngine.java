package com.sioux.Micro;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sioux.BotProcess;
import com.sioux.Macro.MacroInput;
import com.sioux.Macro.MacroOutput;
import com.sioux.Macro.MacroPlayer;
import com.sioux.Micro.Command.Move;
import com.sioux.Micro.Command.Shoot;
import com.sioux.Micro.Command.ShootAt;
import com.sioux.Micro.Configuration.Arena;
import com.sioux.Micro.Configuration.Bot;
import com.sioux.Micro.Configuration.Script;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ferdinand on 4-7-17.
 */
public class MicroEngine {
    // Game specific stuff
    private MicroTick state;
    private Map<Integer, BotProcess> scripts;
    private boolean gameRunning;

    // Game independent things
    private GsonBuilder gsonBuilder;
    private Gson gson;
    private Random random;

    public MicroEngine() {
        // Game specific stuff
        this.state = null;
        this.scripts = null;
        this.gameRunning = false;

        // Game independent things
        this.gsonBuilder = new GsonBuilder();
        this.gson = this.gsonBuilder.create();
        this.random = new Random();
    }

    /**
     * Start a micro game. Takes an starting game state and returns the resulting end game state and winner.
     * @param input Starting state of the game
     * @return End state of the game and the winner
     */
    public MacroOutput Run(MacroInput input) {
        try {
            Initialize(input);
            SaveGameState();

            while (gameRunning) {
                if (!state.getArena().Playable()) {
                    break;
                } else if (state.getTick() > Arena.ShrinkThreshold) {
                    state.getArena().Shrink(Arena.ShrinkFactor);
                }

                System.err.printf("[Tick %d, Arena %d-%d]%n",
                        state.getTick(),
                        state.getArena().getHeight(),
                        state.getArena().getWidth());

                SendGameState();
                WaitForCommands();
                SaveGameState();

                state.IncreaseTick();
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            Uninitialize();
        }

        return GetGameResult();
    }

    /**
     * Lifecycle
     */

    private void Initialize(MacroInput input) {
        int numberOfBots = GetTotalNumberOfBots(input.getPlayers());
        Dimension size = DetermineArenaSize(numberOfBots, Bot.Radius);
        MicroArena arena = new MicroArena(size);
        this.state = new MicroTick(input.getGameId(), input.getTicks(), arena);
        this.scripts = new HashMap<>();

        InitPlayers(input.getPlayers());
        StartScripts();

        this.gameRunning = true;
    }

    private void Uninitialize() {
        for (Map.Entry<Integer, BotProcess> entry : scripts.entrySet()) {
            if (entry.getValue().isRunning()) {
                entry.getValue().close();
            }
        }
    }

    /**
     *  Construction
     * */

    private int GetTotalNumberOfBots(List<MacroPlayer> players)
    {
        return players.stream().map(MacroPlayer::getUfos)
                .filter(ufos -> ufos != null)
                .mapToInt(List::size)
                .sum();
    }

    private Dimension DetermineArenaSize(int nrBots, int botRadius)
    {
        final int baseArenaWidth = Arena.Width;
        final int baseArenaHeight = Arena.Height;
        final int extraArenaHeightPerBot = botRadius * 2;
        final int extraArenaWidthPerBot = botRadius * 2;

        Dimension size = new Dimension(baseArenaWidth, baseArenaHeight);
        if (nrBots > Arena.SizeModifierThreshold)
        {
            size.width += (nrBots - Arena.SizeModifierThreshold) * extraArenaWidthPerBot;
            size.height += (nrBots - Arena.SizeModifierThreshold) * extraArenaHeightPerBot;
        }

        return size;
    }

    private void InitPlayers(List<MacroPlayer> players)
    {
        int nrOfBots = GetTotalNumberOfBots(players);
        Iterator<Point.Double> positions = GetRandomPositionsInArena(nrOfBots, Bot.Radius);

        for (MacroPlayer macro : players) {
            MicroPlayer micro = new MicroPlayer(macro.getId(), macro.getName(), macro.getColor(), macro.getBot());

            for (int botID : macro.getUfos()) {
                String botName = Bot.GenericName + botID;
                micro.addBot(new MicroBot(botID, botName, Bot.HitPoints, positions.next(), Bot.Radius));
            }

            state.Add(micro);
        }
    }

    private Iterator<Point.Double> GetRandomPositionsInArena(int nrBots, int botRadius)
    {
        int retries = 10;
        ArrayList<Point.Double> positions;

        do {
            positions = new ArrayList<>();

            for (int i = 0; i < nrBots; i++) {
                int x = random.nextInt(state.getArena().getWidth() - botRadius) + botRadius;
                int y = random.nextInt(state.getArena().getHeight() - botRadius) + botRadius;
                positions.add(new Point.Double(x, y));
            }
        } while (BotsCollide(positions, Bot.Radius) && retries-- > 0);

        return positions.listIterator();
    }

    private void StartScripts()
    {
        for (MicroPlayer player : state.getPlayers()) {

            try {
                final String dir = player.getScript();
                final String cmd = Script.GetScriptCommand();
                scripts.put(player.getId(), new BotProcess(dir, cmd));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Logic
     */

    private void SendGameState() {
        for (MicroPlayer player : state.getPlayers()) {
            state.setPlayer(player.getId(), player.getName());
            String stateJson = gson.toJson(state, MicroTick.class);
            if(!scripts.get(player.getId()).writeLine(stateJson)) {
                System.err.println("Failed to send game state to " + player.getId());
            }
        }
        state.clearPlayer();
    }

    private void WaitForCommands() {
        for (MicroPlayer player : state.getPlayers()) {
            String inputJson = scripts.get(player.getId()).readLine(1000);
            MicroInput input = gson.fromJson(inputJson, MicroInput.class);

            if (input == null) {
                System.err.println("Failed to receive commands from " + player.getId());
                continue;
            }

            ExecuteCommands(player, input);
            ProcessCollisions();
            ProcessProjectiles();
            ProcessHits();
            CheckForWinner(state.getPlayers());
        }
    }

    private void ExecuteCommands(MicroPlayer player, MicroInput input) {
        for (BotInput commands : input.getCommands()) {
            Optional<MicroBot> result = player.getBots().stream()
                    .filter(bot -> bot.getId() == commands.getId()).findFirst();
            if (!result.isPresent()) continue;

            MicroBot bot = result.get();
            if (!bot.isAlive()) continue;

            Move moveCmd = commands.getMove();
            if (moveCmd != null && bot.canMove()) {
                bot.Move(moveCmd);
            }

            Shoot shootCmd = commands.getShoot();
            if (shootCmd != null && bot.canShoot(state.getTick())) {
                bot.Shoot(state.getTick());
                state.Add(new MicroProjectile(bot.getPosition(), shootCmd.getDirection(), player.getId()));
            }

            ShootAt shootAtCmd = commands.getShootAt();
            if (shootAtCmd != null && bot.canShoot(state.getTick())) {
                bot.Shoot(state.getTick());
                Point.Double target = new Point.Double(shootAtCmd.getX(), shootAtCmd.getY());
                double direction = Utils.DirectionBetweenPoints(bot.getPosition(), target);
                state.Add(new MicroProjectile(bot.getPosition(), direction, player.getId()));
            }
        }
    }

    private void ProcessCollisions() {
        for (MicroPlayer player : state.getPlayers()) {
            for (MicroBot bot : player.getBots()) {
                if (bot.isAlive() && !Utils.BotInsideArena(bot, state.getArena())) {
                    bot.destroy();
                }
            }
        }

        ArrayList<MicroProjectile> projectilesToRemove = new ArrayList<>();
        for (MicroProjectile projectile : state.getProjectiles()) {
            if (!Utils.ProjectileInsideArena(projectile, state.getArena())) {
                projectilesToRemove.add(projectile);
            }
        }

        for (MicroProjectile projectile : projectilesToRemove) {
            state.Remove(projectile);
        }
    }

    private void ProcessProjectiles() {
        for (MicroProjectile projectile : state.getProjectiles()) {
            projectile.Move();
        }
    }

    private void ProcessHits() {
        for (MicroPlayer player : state.getPlayers()) {
            List<MicroProjectile> targets = state.getProjectiles().stream()
                    .filter(p -> !(p.getSource() == player.getId())).collect(Collectors.toList());
            for (MicroProjectile projectile : targets) {
                if (!(player.getId() == projectile.getSource())) {
                    for (MicroBot bot : player.getBots()) {
                        if (bot.Hit(projectile)) {
                            state.getProjectiles().remove(projectile);
                        }
                    }
                }
            }
        }
    }

    private void CheckForWinner(List<MicroPlayer> players)
    {
        List<MicroPlayer> playersWithLivingBots =
                players.stream().filter(p -> p.hasLivingBots()).collect(Collectors.toList());

        if (playersWithLivingBots.size() <= 1)
            gameRunning = false;
    }

    private void SaveGameState() {
        final String path = state.getTicksFolder() + "tick_" + state.getTick() + ".json";
        final String data = gson.toJson(state, MicroTick.class);

        try {
            File file = new File(path);
            file.getParentFile().mkdir();
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(data);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean BotsCollide(ArrayList<Point.Double> positions, int botRadius)
    {
        int botRadiusSquared = botRadius*botRadius;
        for (int i = 0; i < positions.size(); i++)
        {
            for (int j = i+1; j < positions.size(); j++)
            {
                int dx = (int)positions.get(i).x - (int)positions.get(j).x;
                int dy = (int)positions.get(i).y - (int)positions.get(j).y;
                int distanceSquared = dx*dx + dy*dy;
                if (distanceSquared < botRadiusSquared)
                    return true;
            }
        }

        return false;
    }

    private MacroOutput GetGameResult() {
        List<MacroPlayer> players = new ArrayList<>();

        for (MicroPlayer micro : state.getPlayers()) {
            List<Integer> ufos = new ArrayList<>();
            for (MicroBot bot : micro.getBots()) {
                if (bot.isAlive()) {
                    ufos.add(bot.getId());
                }
            }
            players.add(new MacroPlayer(micro.getId(), micro.getName(), micro.getScript(), ufos, micro.getColor()));
        }

        return new MacroOutput(players, state.getGameId(), GetWinner());
    }

    private int GetWinner() {
        int winner = 0;
        for (MicroPlayer player : state.getPlayers())
        {
            if (player.hasLivingBots())
                winner = player.getId();
        }
        return winner;
    }
}
