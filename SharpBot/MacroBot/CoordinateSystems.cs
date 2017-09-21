using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MacroBot
{
    public struct CartesianCoord
    {
        public CartesianCoord(double x, double y)
        {
            X = x;
            Y = y;
        }

        double X;
        double Y;

        public PolarCoord AsPolorCoord()
        {
            return AsPolorCoord(X, Y);
        }

        public static PolarCoord AsPolorCoord(double x, double y)
        {
            return new PolarCoord(Math.Pow(x * x + y * y, 0.5), Math.Atan2(y, x));
        }
    }

    public struct PolarCoord
    {
        public enum AngleType
        {
            Degree,
            Radian,
        }

        public PolarCoord(double radius, double angle, AngleType type = AngleType.Radian)
        {
            Radius = radius;
            AngleRadian = type == AngleType.Degree ? MathEx.DegreeToRadian(angle) : angle;
        }

        double Radius;
        double AngleRadian;

        public double AngleDegree { get { return MathEx.RadianToDegree(AngleRadian); } }

        public CartesianCoord AsCartesianCoord()
        {
            return AsCartesianCoord(Radius, AngleRadian, AngleType.Radian);
        }

        public static CartesianCoord AsCartesianCoord(double radius, double angle, AngleType type = AngleType.Radian)
        {
            var angleRadian = type == AngleType.Degree ? MathEx.DegreeToRadian(angle) : angle;
            return new CartesianCoord(radius * Math.Cos(angleRadian), radius * Math.Sin(angleRadian));
        }
    }
}
