import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class BotEasy implements Bot {
    private GamePlay gamePlay;
    private List<CoordValue.Coord> listPosMove;
    private static  final int[][] boardValue = new int[][]{
            {100, -1, 5, 2, 2, 5, -1, 100},
            {-1, -20, 1, 1, 1, 1, -20, -1},
            {5, 1, 1, 1, 1, 1, 1, 5},
            {2, 1, 1, 0, 0, 1, 1, 2},
            {2, 1, 1, 0, 0, 1, 1, 2},
            {5, 1, 1, 1, 1, 1, 1, 5},
            {-1, -20, 1, 1, 1, 1, -20, -1},
            {100, -1, 5, 2, 2, 5, -1, 100},
    };

    @Override
    public CoordValue.Coord move() {
        int highestScore = -20;
        CoordValue.Coord goodMove;
        List<CoordValue.Coord> listOfGoodMove = new ArrayList<>();
        for (CoordValue.Coord move : listPosMove) {
            if (boardValue[move.getRow()][move.getCol()] >= highestScore) {
                if (boardValue[move.getRow()][move.getCol()] == highestScore) {
                    listOfGoodMove.add(move);
                } else {
                    listOfGoodMove.clear();
                    listOfGoodMove.add(move);
                }
                highestScore = boardValue[move.getRow()][move.getCol()];
            }
        }
        new Random().nextInt(listPosMove.size());
        goodMove = listOfGoodMove.get(new Random().nextInt(listOfGoodMove.size()));
        return goodMove;
    }

    @Override
    public void attribute(GamePlay game) {
        this.gamePlay = game;
        this.listPosMove = game.moveList(game.currentBoard, game.currentColor);
    }
}
