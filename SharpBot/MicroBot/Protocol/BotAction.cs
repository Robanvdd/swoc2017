namespace MicroBot.Protocol
{
    public sealed class BotAction
    {
        public string Name { get; set; }
        public Movement Movement { get; set; }
        public Shoot Shoot { get; set; }
    }
}