import javafx.util.Pair;

import java.util.List;
import java.util.Random;

public class botEasy implements bot {
    gamePlay gamePlay;
    private List<Pair<Integer, Integer>> listPosMove;

    @Override
    public Pair<Integer, Integer> move() {
        return listPosMove.get(new Random().nextInt(listPosMove.size()));
    }

    @Override
    public void attribute(gamePlay game) {
        this.gamePlay = game;
        this.listPosMove = game.listOfPsbMove;
    }


}
