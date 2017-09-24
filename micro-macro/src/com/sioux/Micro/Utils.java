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
        return (bot.getPosition().x - bot.getRadius() > 0 || bot.getPosition().x + bot.getRadius() < arena.getWidth())
                || (bot.getPosition().y - bot.getRadius() < 0 || bot.getPosition().y + bot.getRadius() < arena.getHeight());
    }
}
