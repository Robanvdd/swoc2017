package com.sioux.Micro;

import java.awt.*;

public class Utils {
    public static Point.Double PolarToCartesian(Double radius, Double angle) {
        Double radian = Math.toRadians(angle);
        Double x = radius * Math.cos(radian);
        Double y = radius * Math.sin(radian);
        return new Point.Double(x, y);
    }

    public static Point.Double ClampPoint(Point.Double pos, int xMin, int xMax, int yMin, int yMax) {
        Double x = Math.min(Math.max(pos.x, xMin), xMax);
        Double y = Math.min(Math.max(pos.y, yMin), yMax);
        return new Point.Double(x, y);
    }

    public static Boolean BotInsideArena(MicroBot bot, MicroArena arena) {
        int radius = bot.getRadius();
        double x = bot.getPosition().x;
        double y = bot.getPosition().y;

        return (x - radius > 0 && x + radius < arena.getWidth())
                && (y - radius > 0 && y + radius < arena.getHeight());
    }
}
