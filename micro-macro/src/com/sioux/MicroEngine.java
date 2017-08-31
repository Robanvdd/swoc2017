package com.sioux;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.sioux.game_objects.Game;
import com.sioux.game_objects.GameResult;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    public MicroEngine() {
        this.state = new MicroTick();
        this.scripts = new HashMap<>();
        this.gsonBuilder = new GsonBuilder();
        this.gsonBuilder.registerTypeAdapter(Point.Double.class, new PointAdapter());
        this.gson = this.gsonBuilder.create();
        this.tickCounter = 0;
    }

    public GameResult Run(Game start) {
        Initialize(start);

        while (true) {
            if (!this.state.arena.Playable()) {
                break;
            }
            else if (this.tickCounter > 1000) {
                this.state.arena.Shrink(10);
            }

            SendGameState();
            WaitForCommands();
            SaveGameState();

            this.tickCounter++;
        }

        Uninitialize();

        return new GameResult(GetGame(), GetWinner());
    }

    private void Initialize(Game start) {
        // TODO Game data -> MicroTick state

        String[] colors = {"#32FF0000", "#320000FF"};
        for (int i = 0; i < colors.length; i++) {
            MicroPlayer player = new MicroPlayer("player" + i, colors[i]);

            for (int j = 0; j < 4; j++) {
                Point.Double position = new Point.Double(10 * i, 10 * j);
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
            bot.Shoot(command.shoot, player.name);
        }
        ProcessHits();
    }

    private void ProcessHits() {
        for (MicroProjectile projectile : this.state.projectiles) {
            Point.Double newPos = PolarToCartesian(5.0, projectile.direction);
            projectile.position.x += newPos.x;
            projectile.position.y += newPos.y;
        }
        for (MicroPlayer player : this.state.players) {
            List<MicroProjectile> targets = this.state.projectiles.stream()
                    .filter(p -> !p.source.equalsIgnoreCase(player.name)).collect(Collectors.toList());
            for (MicroProjectile projectile : targets) {
                if (!player.name.equalsIgnoreCase(projectile.source)) {
                    for (MicroBot bot : player.bots) {
                        if (bot.Hit(projectile)) {
                            this.state.projectiles.remove(projectile);
                        }
                    }
                }
            }

        }
    }

    private Point.Double PolarToCartesian(Double radius, Double angle) {
        Double radian = Math.toRadians(angle);
        Double x = radius * Math.cos(radian);
        Double y = radius * Math.sin(radian);
        return new Point.Double(x, y);
    }

    private Point.Double ArenaBoundsCheck(Point.Double pos) {
        Double x = Math.min(Math.max(pos.x, 0), this.state.arena.width);
        Double y = Math.min(Math.max(pos.y, 0), this.state.arena.height);
        return new Point.Double(x, y);
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

        public void Shrink(Integer delta) {
            this.height = Math.max(this.height - delta, 0);
            this.width = Math.max(this.width - delta, 0);
        }

        public boolean Playable() {
            return this.height > 0 && this.width > 0;
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
        private Point.Double position;
        private transient Integer tickShoot;

        public MicroBot(String name, Integer hp, Point.Double pos) {
            this.name = name;
            this.hitpoints = hp;
            this.position = pos;
            this.tickShoot = 0;
        }

        public void Move(Move cmd) {
            if (cmd == null || !this.isAlive()) return;
            Point.Double newPos = PolarToCartesian(cmd.speed, cmd.direction);
            newPos = ArenaBoundsCheck(newPos);
            this.position.x += newPos.x;
            this.position.y += newPos.y;
        }

        public void Shoot(Shoot cmd, String source) {
            if (cmd == null || !this.isAlive() || !this.canShoot())  return;
            else this.tickShoot = tickCounter;
            state.projectiles.add(new MicroProjectile(this.position, cmd.direction, source));
        }

        public Boolean Hit(MicroProjectile projectile) {
            if (projectile == null || !this.isAlive()) return false;

            Double botRadius = 15.0;
            Double projectileRadius = 5.0;
            Double dx = projectile.position.getX() - this.position.getX();
            Double dy = projectile.position.getY() - this.position.getY();
            Double radii = botRadius + projectileRadius;

            if (( dx * dx ) + ( dy * dy ) < radii * radii) {
                this.hitpoints -= projectile.damage;
                return true;
            }
            return false;
        }

        public Boolean isAlive() {
            return this.hitpoints > 0;
        }

        public Boolean canShoot() {
            return (this.tickShoot + 30) < tickCounter;
        }
    }

    private class MicroProjectile {
        private Point.Double position;
        private Double direction;
        private transient String source;
        private transient Integer damage;

        public MicroProjectile(Point.Double position, Double direction, String source) {
            this.position = position;
            this.direction = direction;
            this.source = source;
            this.damage = 5;
        }
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
        private Double direction;
        private Double speed;
    }

    private class Shoot {
        private Double direction;
    }

    private class PointAdapter extends TypeAdapter<Point.Double> {
        public Point.Double read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return null;
            }
            String[] point = reader.nextString().split(",");
            Double x = Double.parseDouble(point[0]);
            Double y = Double.parseDouble(point[1]);
            return new Point.Double(x, y);
        }

        public void write(JsonWriter writer, Point.Double value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            String point = value.getX() + "," + value.getY();
            writer.value(point);
        }
    }
}
