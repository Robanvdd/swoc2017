using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Bot.Protocol
{
    public struct Position
    {
        public float X { get; set; }
        public float Y { get; set; }

        public override string ToString()
        {
            return String.Format("{0},{1} (X,Y)", X, Y);
        }
    }

    public class PositionConverter : JsonConverter
    {
        public override bool CanConvert(Type objectType)
        {
            return objectType == typeof(Position);
        }

        public override object ReadJson(JsonReader reader, Type objectType, object existingValue, JsonSerializer serializer)
        {
            var pos = (reader.Value as string)?.Split(',');
            if (pos == null)
                throw new ArgumentException("Invalid definition");
            return new Position { X = float.Parse(pos[0]), Y = float.Parse(pos[1]) };
        }

        public override void WriteJson(JsonWriter writer, object value, JsonSerializer serializer)
        {
            var point = (Position)value;
            writer.WriteRawValue(String.Format("{0},{1}", point.X, point.Y));
        }
    }
}
