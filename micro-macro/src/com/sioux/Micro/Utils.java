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

    public static Point.Double VectorBetweenPoints(Point.Double from, Point.Double to)
    {
        return new Point.Double(to.x - from.x, to.y - from.y);
    }

    public static double DotProduct(Point.Double v1, Point.Double v2)
    {
        return v1.x*v2.x + v1.y*v2.y;
    }

    public static double PerpDotProduct(Point.Double v1, Point.Double v2)
    {
        return -v1.y*v2.x+v1.x*v2.y;
    }

    public static double VectorLength(Point.Double v)
    {
        return Math.sqrt(DotProduct(v, v));
    }

    public static void NormalizeVector(Point.Double v)
    {
        double length = VectorLength(v);
        v.x /= length;
        v.y /= length;
    }

    public static double AngleBetweenUnitVectors(Point.Double vFrom, Point.Double vTo)
    {
        return Math.atan2(PerpDotProduct(vFrom, vTo), DotProduct(vFrom, vTo));
    }

    public static double RadiansToDegrees(double radians)
    {
        return radians * 180.0 / Math.PI;
    }

    public static double DirectionBetweenPoints(Point.Double from, Point.Double to)
    {
        Point.Double vectorPoints = VectorBetweenPoints(from, to);
        NormalizeVector(vectorPoints);
        Point.Double zeroVector = new Point.Double(-1, 0);
        double angleRadians = AngleBetweenUnitVectors(zeroVector, vectorPoints);
        double angleDegrees = RadiansToDegrees(angleRadians);
        return (180.0 - angleDegrees) % 360.0;
    }

    public static boolean ProjectileInsideArena(MicroProjectile projectile, MicroArena arena) {
        int radius = projectile.getRadius();
        double x = projectile.getPosition().x;
        double y = projectile.getPosition().y;

        return (x - radius > 0 && x + radius < arena.getWidth())
                && (y - radius > 0 && y + radius < arena.getHeight());
    }
}
