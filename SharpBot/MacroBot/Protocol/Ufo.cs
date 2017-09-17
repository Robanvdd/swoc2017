using Bot.Protocol;

namespace MacroBot.Protocol
{
    public sealed class Ufo
    {
        public int Id { get; set; }
        public string Type { get; set; }
        public bool InFight { get; set; }
        public Position Coord { get; set; }

    }
}