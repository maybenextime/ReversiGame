import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class botNormal implements bot {
    gamePlay gamePlay;
    private List<Pair<Integer, Integer>> listPosMove;
    private int[][] boardValue = new int[][]{
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
    public Pair<Integer, Integer> move() {
        int highestScore = -20;
        Pair<Integer, Integer> goodMove = null;
        List<Pair<Integer, Integer>> listOfGoodMove = new ArrayList<>();
        for (Pair<Integer, Integer> pair : listPosMove) {
            if (boardValue[pair.getKey()][pair.getValue()] >= highestScore) {
                if (boardValue[pair.getKey()][pair.getValue()] == highestScore) {
                    listOfGoodMove.add(pair);
                } else {
                    listOfGoodMove.clear();
                    listOfGoodMove.add(pair);
                }
                highestScore = boardValue[pair.getKey()][pair.getValue()];
            }
        }
        new Random().nextInt(listPosMove.size());
        goodMove = listOfGoodMove.get(new Random().nextInt(listOfGoodMove.size()));
        return goodMove;
    }

    @Override
    public void attribute(gamePlay game) {
        this.gamePlay = game;
        this.listPosMove = game.listOfPsbMove;
    }
}
