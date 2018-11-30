import com.sun.deploy.util.ArrayUtil;
import javafx.util.Pair;


import java.util.*;

public class botHard implements bot {
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

    gamePlay game = new gamePlay();

    private char[][] currentBoard ;
    private char curColor;
    private char sugColor;
    private List<Pair<Integer, Integer>> listPosMove = game.listOfPsbMove;

    private int maxScoreMove(char[][] board, char sugColor) {

        Integer maxScore = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == sugColor) {
                    if (boardValue[i][j] > maxScore) maxScore = boardValue[i][j];
                }
            }
        }
        return maxScore;
    }

    @Override
    public Pair<Integer, Integer> move() {
        List<Integer> listDiffScore = new ArrayList<>();
        List<Pair<Integer, Integer>> listBestScore = new ArrayList<>();
        for (Pair<Integer, Integer> pair : game.listOfPsbMove) {
            gamePlay fiction = new gamePlay();
           // fiction.currentBoard= Arrays.stream(game.currentBoard).toArray(char[][]::new);
            for (int i=0; i<8;i++){
                for(int j=0;j<8;j++){
                    fiction.currentBoard[i][j] =this.currentBoard[i][j];
                }
            }
            fiction.listOfPsbMove= new ArrayList<>(game.listOfPsbMove);
            fiction.check(fiction.currentBoard, pair.getKey(), pair.getValue(), curColor);
            for (Pair<Integer, Integer> pair2 : fiction.list) {
                fiction.changePieces(fiction.currentBoard, pair.getKey(), pair.getValue(), pair2.getKey(), pair2.getValue(), curColor);
            }
            fiction.list.clear();
            fiction.noEffectBoard();
            changeColor();
            fiction.psbMove(curColor);
            Integer max = maxScoreMove(fiction.currentBoard, sugColor);
            if (max == 0) {
                if (curColor == 'b' && fiction.getBlackPoint() > fiction.getWhitePoint()) max = 100000;
                else if (curColor == 'w' && fiction.getBlackPoint() < fiction.getWhitePoint()) max = 100000;
                else max = -100000;
                listDiffScore.add(max);
            } else listDiffScore.add(boardValue[pair.getKey()][pair.getValue()] - max);
            changeColor();
        }
        int bestScore = Collections.max(listDiffScore);
        for (int i = 0; i < listDiffScore.size(); i++) {
            if (listDiffScore.get(i) == bestScore) listBestScore.add(listPosMove.get(i));
        }
        return listBestScore.get(new Random().nextInt(listBestScore.size()));
    }

    @Override
    public void attribute(gamePlay game) {
        this.game = game;
        this.listPosMove = game.listOfPsbMove;
        this.curColor= game.currentColor;
        this.currentBoard= game.currentBoard;
    }

    private void changeColor() {
        if (curColor == 'w') {
            curColor = 'b';
            sugColor = 'r';
        } else {
            curColor = 'w';
            sugColor = 'g';
        }
    }
}
