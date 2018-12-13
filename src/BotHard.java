public class BotHard implements Bot {
    private GamePlay game = new GamePlay();
    private static int maxDepth = 5;

    @Override
    public CoordValue.Coord move() {
        MiniMax miniMax = new MiniMax(game, maxDepth, game.currentColor);
        return miniMax.scoutDecision(game.currentBoard, game.currentColor);
    }

    @Override
    public void attribute(GamePlay game) {
        this.game = game;
    }
}

