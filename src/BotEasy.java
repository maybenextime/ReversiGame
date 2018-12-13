import java.util.List;
import java.util.Random;

public class BotEasy implements Bot {
    private GamePlay gamePlay;
    private List<CoordValue.Coord> listPosMove;

    @Override
    public CoordValue.Coord move() {
        return listPosMove.get(new Random().nextInt(listPosMove.size()));
    }

    @Override
    public void attribute(GamePlay game) {
        this.gamePlay = game;
        this.listPosMove = game.moveList(game.currentBoard, game.currentColor);
    }


}
