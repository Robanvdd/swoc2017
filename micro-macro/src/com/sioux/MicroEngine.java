package com.sioux;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.sioux.game_objects.Game;
import com.sioux.game_objects.GameResult;

import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by Ferdinand on 4-7-17.
 */
public class MicroEngine {
    private MicroTick state;
    private Map<String, BotProcess> scripts;
    private Gson gson;
    private GsonBuilder gsonBuilder;
    private Integer tickCounter;
    private Boolean gameRunning;

    MicroEngine() {
        this.state = new MicroTick();
        this.scripts = new HashMap<>();
        this.gsonBuilder = new GsonBuilder();
        this.gsonBuilder.registerTypeAdapter(Point.class, new PointAdapter());
        this.gson = this.gsonBuilder.create();
        this.tickCounter = 0;
        this.gameRunning = false;
    }

    public GameResult Run(Game start) {
        Initialize(start);

        while (gameRunning) {
            GameLogic();

            // Run for a specific amount of ticks.
            gameRunning = (++this.tickCounter < 100);
        }

        Uninitialize();

        return new GameResult(GetGame(), GetWinner());
    }

    private void Initialize(Game start) {
        // TODO Game data -> MicroTick state

        String[] colors = {"#FF0000", "#0000FF"};
        for (int i = 0; i < colors.length; i++) {
            MicroPlayer player = new MicroPlayer("player" + i, colors[i]);

            for (int j = 0; j < 4; j++) {
                Point position = new Point(10 * i, 10 * j);
                player.Add(new MicroBot("bot" + j, 100, position));
            }

            state.Add(player);

            final String dir = "../test-scripts/readyplayerone/1/micro/";
            final String cmd = "python3.5 ./code/micro.py"; //"./run.sh";

            try {
                scripts.put(player.name, new BotProcess(dir, cmd));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        gameRunning = true;
    }

    private void Uninitialize() {
        for (Map.Entry<String, BotProcess> entry : scripts.entrySet()) {
            if (entry.getValue().isRunning()) {
                entry.getValue().close();
            }
        }
    }

    private void GameLogic() {
        SendGameState();
        WaitForCommands();
        SaveGameState();
    }

    private void SendGameState() {
        for (MicroPlayer player : state.players) {
            String stateJson = gson.toJson(this.state, MicroTick.class);
            scripts.get(player.name).writeLine(stateJson);
        }
    }

    private void WaitForCommands() {
        for (MicroPlayer player : state.players) {
            String inputJson = scripts.get(player.name).readLine(1000);
            MicroInput input = gson.fromJson(inputJson, MicroInput.class);

            ExecuteCommands(player, input);
        }
    }

    private void ExecuteCommands(MicroPlayer player, MicroInput input) {
        for (BotInput command : input.bots) {
            if (command.name == null) continue;
            Optional<MicroBot> result = player.bots.stream().filter(b -> b.name.equals(command.name)).findFirst();

            if (!result.isPresent()) continue;
            MicroBot bot = result.get();
            bot.Move(command.move);
            bot.Shoot(command.shoot);
        }
    }

    private Point PolarToCartesian(Integer distance, Integer angle) {
        Double radian = Math.toRadians(angle);
        Double x = distance * Math.cos(radian);
        Double y = distance * Math.sin(radian);
        return new Point(x.intValue(), y.intValue());
    }

    private Point ArenaBoundsCheck(Point pos) {
        Integer x = Math.min(Math.max(pos.x, 0), this.state.arena.width);
        Integer y = Math.min(Math.max(pos.y, 0), this.state.arena.height);
        return new Point(x, y);
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

    private Game GetGame() {
        return null;
    }

    private String GetWinner() {
        return new String();
    }

    private class MicroTick {
        private MicroArena arena;
        private List<MicroPlayer> players;
        private List<MicroProjectile> projectiles;

        public MicroTick() {
            this.arena = new MicroArena(1000, 1000);
            this.players = new ArrayList<>();
            this.projectiles = new ArrayList<>();
        }

        public void Add(MicroPlayer player) {
            this.players.add(player);
        }

        public void Add(MicroProjectile projectile) {
            this.projectiles.add(projectile);
        }
    }

    private class MicroArena {
        private Integer height;
        private Integer width;

        public MicroArena(Integer height, Integer width) {
            this.height = height;
            this.width = width;
        }
    }

    private class MicroPlayer {
        private String name;
        private String color;
        private List<MicroBot> bots;

        public MicroPlayer(String name, String color) {
            this.name = name;
            this.color = color;
            this.bots = new ArrayList<>();
        }

        public void Add(MicroBot bot) {
            this.bots.add(bot);
        }
    }

    private class MicroBot {
        private String name;
        private Integer hitpoints;
        private Point position;

        public MicroBot(String name, Integer hp, Point pos) {
            this.name = name;
            this.hitpoints = hp;
            this.position = pos;
        }

        public void Move(Move cmd) {
            if (cmd == null) return;
            Point newPos = PolarToCartesian(cmd.speed, cmd.direction);
            newPos = ArenaBoundsCheck(newPos);
            position.translate(newPos.x, newPos.y);
        }

        public MicroProjectile Shoot(Shoot cmd) {
            // TODO
            return new MicroProjectile();
        }

        public Boolean Hit(Shoot cmd) {
            // TODO
            return false;
        }
    }

    private class MicroProjectile {
        private String name;
        private Point position;
    }

    private class MicroInput {
        private List<BotInput> bots;

        public MicroInput() {
            bots = new ArrayList<>();
        }
    }

    private class BotInput {
        private String name;
        private Move move;
        private Shoot shoot;
    }

    private class Move {
        private Integer direction;
        private Integer speed;
    }

    private class Shoot {
        private Integer direction;
    }

    private class PointAdapter extends TypeAdapter<Point> {
        public Point read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return null;
            }
            String[] point = reader.nextString().split(",");
            Integer x = Integer.parseInt(point[0]);
            Integer y = Integer.parseInt(point[1]);
            return new Point(x, y);
        }

        public void write(JsonWriter writer, Point value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            String point = value.getX() + "," + value.getY();
            writer.value(point);
        }
    }
}
