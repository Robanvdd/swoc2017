namespace MicroBot.Protocol
{
    public sealed class BotAction
    {
        public int Id { get; set; }
        public Movement Movement { get; set; }
        public MoveTo MoveTo { get; set; }
        public Shoot Shoot { get; set; }
        public ShootAt ShootAt { get; set; }
    }
}