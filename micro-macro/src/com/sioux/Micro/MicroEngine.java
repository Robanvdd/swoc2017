package com.sioux.Micro;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sioux.BotProcess;
import com.sioux.game_objects.Game;
import com.sioux.game_objects.GameResult;

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
    private Integer tickCounter;
    private MicroTick state;
    private Map<String, BotProcess> scripts;
    private boolean gameRunning;

    // Game independent things
    private GsonBuilder gsonBuilder;
    private Gson gson;
    private Random random;

    public MicroEngine() {
        // Game specific stuff
        this.state = null;
        this.scripts = null;
        this.tickCounter = 0;
        this.gameRunning = false;

        // Game independent things
        this.gsonBuilder = new GsonBuilder();
        this.gsonBuilder.registerTypeAdapter(Point.Double.class, new PointAdapter());
        this.gson = this.gsonBuilder.create();
        this.random = new Random();
    }

    /**
     * Start a micro game. Takes an starting game state and returns the resulting end game state and winner.
     * @param start Starting state of the game
     * @return End state of the game and the winner
     */
    public GameResult Run(Game start) {
        Initialize(start);
        SaveGameState();

        while (gameRunning) {
            if (!this.state.getArena().Playable()) {
                break;
            }
            else if (this.tickCounter > 1000) {
                this.state.getArena().Shrink(10);
            }

            System.err.printf("[Tick %d, Arena %d-%d]%n",
                    this.tickCounter,
                    this.state.getArena().getHeight(),
                    this.state.getArena().getWidth());

            SendGameState();
            WaitForCommands();
            SaveGameState();

            this.tickCounter++;
        }

        Uninitialize();
        return new GameResult(GetGame(), GetWinner());
    }

    /**
     * Lifecycle
     */

    private void Initialize(Game start) {
        // TODO Game data -> MicroTick state

        final int botRadius = 15;
        String[] playerNames = {"Player1", "Player2"};
        String[] playerColors = {"#32FF0000", "#320000FF"};
        int[] playerNrBots = {5, 5};

        int numberOfBots = GetTotalNumberOfBots(playerNrBots);
        Dimension size = DetermineArenaSize(numberOfBots, botRadius);
        MicroArena arena = new MicroArena(size);
        this.state = new MicroTick(arena);
        this.scripts = new HashMap<>();

        InitPlayers(playerNames, playerColors);
        PlaceBots(playerNrBots, botRadius);
        StartScripts(playerNames);

        this.tickCounter = 0;
        this.gameRunning = true;
    }

    private void Uninitialize() {
        for (Map.Entry<String, BotProcess> entry : scripts.entrySet()) {
            if (entry.getValue().isRunning()) {
                entry.getValue().close();
            }
        }
    }

    /**
     *  Construction
     * */

    private int GetTotalNumberOfBots(int[] playerNrBots)
    {
        int sumBots = 0;
        for (int i = 0; i < playerNrBots.length; i++)
            sumBots += playerNrBots[i];
        return sumBots;
    }

    private Dimension DetermineArenaSize(int nrBots, int botRadius)
    {
        final int baseArenaWidth = 1000;
        final int baseArenaHeight = 1000;
        final int extraArenaHeightPerBot = botRadius * 2;
        final int extraArenaWidthPerBot = botRadius * 2;

        Dimension size = new Dimension(baseArenaWidth, baseArenaHeight);
        if (nrBots > 16)
        {
            size.width += (nrBots - 16) * extraArenaWidthPerBot;
            size.height += (nrBots - 16) * extraArenaHeightPerBot;
        }

        return size;
    }

    private void InitPlayers(String[] playerNames, String[] playerColors)
    {
        for (int i = 0; i < playerNames.length; i++) {
            MicroPlayer player = new MicroPlayer(playerNames[i], playerColors[i]);
            state.Add(player);
        }
    }

    private ArrayList<Point.Double> GetRandomPositionsInArena(int nrBots, int botRadius)
    {
        ArrayList<Point.Double> positions = new ArrayList<>();
        for (int i = 0; i < nrBots; i++)
        {
            int x = random.nextInt(state.getArena().getWidth() - botRadius) + botRadius;
            int y = random.nextInt(state.getArena().getHeight() - botRadius) + botRadius;
            positions.add(new Point.Double(x, y));
        }
        return positions;
    }

    private void PlaceBots(int[] playerNrBots, int botRadius)
    {
        final int botHealthPoints = 100;

        int nrBots = GetTotalNumberOfBots(playerNrBots);
        ArrayList<Point.Double> positions = GetRandomPositionsInArena(nrBots, botRadius);
        while (BotsCollide(positions, botRadius))
            positions = GetRandomPositionsInArena(nrBots, botRadius);

        int index = 0;
        for (int i = 0; i < playerNrBots.length; i++)
        {
            for (int j = 0; j < playerNrBots[i]; j++)
            {
                MicroBot bot = new MicroBot("bot" + j, botHealthPoints, positions.get(index), botRadius);
                state.getPlayers().get(i).Add(bot);
            }
        }
    }

    private void StartScripts(String[] playerNames)
    {
        for (int i = 0; i < playerNames.length; i++) {
            final String dir = "../test-scripts/readyplayerone/1/micro/";
            final String cmd = "./run.cmd";

            try {
                scripts.put(playerNames[i], new BotProcess(dir, cmd));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Logic
     */

    private void SendGameState() {
        for (MicroPlayer player : state.getPlayers()) {
            this.state.setPlayer(player.getName());
            String stateJson = gson.toJson(this.state, MicroTick.class);
            scripts.get(player.getName()).writeLine(stateJson);
        }
        this.state.setPlayer("");
    }

    private void WaitForCommands() {
        for (MicroPlayer player : state.getPlayers()) {
            String inputJson = scripts.get(player.getName()).readLine(1000);
            MicroInput input = gson.fromJson(inputJson, MicroInput.class);

            if (input != null) {
                ExecuteCommands(player, input);
            }
        }
    }

    private void ExecuteCommands(MicroPlayer player, MicroInput input) {
        for (BotInput command : input.getCommands()) {
            if (command.getName() == null) continue;
            Optional<MicroBot> result = player.getBots().stream()
                    .filter(bot -> bot.getName().equals(command.getName())).findFirst();

            if (!result.isPresent()) continue;
            MicroBot bot = result.get();

            bot.Move(command.getMove(), this.state.getArena());

            MicroProjectile projectile = bot.Shoot(command.getShoot(), player.getName(), this.tickCounter);
            if (projectile != null) {
                state.Add(projectile);
            }
        }
        ProcessHits();
    }

    private void ProcessHits() {
        for (MicroProjectile projectile : this.state.getProjectiles()) {
            Point.Double newPos = Utils.PolarToCartesian(5.0, projectile.getDirection());
            projectile.Move(newPos.x, newPos.y);
        }
        for (MicroPlayer player : this.state.getPlayers()) {
            List<MicroProjectile> targets = this.state.getProjectiles().stream()
                    .filter(p -> !p.getSource().equalsIgnoreCase(player.getName())).collect(Collectors.toList());
            for (MicroProjectile projectile : targets) {
                if (!player.getName().equalsIgnoreCase(projectile.getSource())) {
                    for (MicroBot bot : player.getBots()) {
                        if (bot.Hit(projectile)) {
                            this.state.getProjectiles().remove(projectile);
                            CheckForWinner(this.state.getPlayers());
                        }
                    }
                }
            }
        }
    }

    private void CheckForWinner(List<MicroPlayer> players)
    {
        ArrayList<MicroPlayer> playersWithBots = new ArrayList<MicroPlayer>();
        for (int i = 0; i < players.size(); i++)
        {
            if (players.get(i).getBots().size() > 0)
                playersWithBots.add(players.get(i));
        }

        if (playersWithBots.size() <= 1) // 1 -> winner, 0 -> draw
        {
            gameRunning = false;
        }
    }

    private void SaveGameState() {
        final String path = "../test-scripts/readyplayerone/1/micro/ticks/tick_" + this.tickCounter + ".json";
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

    private Game GetGame() {
        return null;
    }

    private String GetWinner() {
        String winner = new String();
        for (int i = 0; i < state.getPlayers().size(); i++)
        {
            if (state.getPlayers().get(i).getBots().size() > 0)
            {
                winner = state.getPlayers().get(i).getName();
                break;
            }
        }
        return winner;
    }
}
