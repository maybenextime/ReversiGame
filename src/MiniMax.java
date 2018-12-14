import java.util.ArrayList;

class MiniMax {
    private int maxDepth;
    private GamePlay gamePlay;
    private CellState colorBot;

    MiniMax(GamePlay gamePlay, int maxDepth, CellState colorBot) {
        this.maxDepth = maxDepth;
        this.gamePlay = gamePlay;
        this.colorBot = colorBot;
    }

    CoordValue.Coord scoutDecision(CellState[][] board, CellState curColor) {
        CoordValue.MoveScore moveScore = this.scout(board, Turn.BOT, 0,-99999,99999, curColor);
        return moveScore.getMove();
    }

    private void copyBoard(CellState srcBoard[][], CellState newBoard[][]) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                newBoard[i][j] = srcBoard[i][j];
            }
        }
    }

    private static final int[][] boardValue = new int[][]{
            {100, -1, 5, 2, 2, 5, -1, 100},
            {-1, -20, 1, 1, 1, 1, -20, -1},
            {5, 1, 1, 1, 1, 1, 1, 5},
            {2, 1, 1, 0, 0, 1, 1, 2},
            {2, 1, 1, 0, 0, 1, 1, 2},
            {5, 1, 1, 1, 1, 1, 1, 5},
            {-1, -20, 1, 1, 1, 1, -20, -1},
            {100, -1, 5, 2, 2, 5, -1, 100},
    };

    private void changeColor(GamePlay gamePlay) {
        if (gamePlay.currentColor == CellState.BLACK) {
            gamePlay.currentColor = CellState.WHITE;
        } else gamePlay.currentColor = CellState.BLACK;
    }

    private int evaluateBoard(CellState[][] board) {
        int value = 0;
        CellState colorHuman = CellState.WHITE;
        if (colorBot == CellState.WHITE)
            colorHuman = CellState.BLACK;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == this.colorBot) {
                    value += boardValue[i][j];
                } else if (board[i][j] == colorHuman) {
                    value -= boardValue[i][j];
                }
            }
        }
        return value;
    }

    private CoordValue.MoveScore scout(CellState[][] board, Turn turn, int depth,int alpha, int beta, CellState curColor) {
        if ((depth == this.maxDepth)) {
            return new CoordValue.MoveScore(null, evaluateBoard(board));
        } else {
            int bestScore = -99999;
            if (turn != Turn.BOT) bestScore = 99999;
            CoordValue.Coord bestMove;
            ArrayList<CoordValue.Coord> moveList = new ArrayList<>(gamePlay.moveList(board, curColor));

            if (moveList.size() == 0) {
                return new CoordValue.MoveScore(null, evaluateBoard(board));
            } else {
                bestMove = moveList.get(0);
                for (int i = 0; i < moveList.size(); i++) {
                    CoordValue.Coord move = moveList.get(i);
                    Turn nexTurn = Turn.HUMAN;
                    if (turn == Turn.HUMAN) {
                        nexTurn = Turn.BOT;
                    }
                    CellState[][] copyBoard = new CellState[8][8];
                    copyBoard(board, copyBoard);
                    GamePlay gamePlayScout = new GamePlay(copyBoard, curColor);
                    gamePlayScout.applyMove(copyBoard, move, curColor);
                    changeColor(gamePlayScout);
                    gamePlayScout.effectBoard(gamePlayScout.currentBoard, gamePlayScout.currentColor);
                    CoordValue.MoveScore current = scout(gamePlayScout.currentBoard, nexTurn, depth + 1,alpha,beta, gamePlayScout.currentColor);
                    if (turn == Turn.BOT) {
                        if (current.getScore() > bestScore) {
                            bestScore = current.getScore();
                            bestMove = move;
                        }
                        alpha= Math.max(current.getScore(),alpha);
                        if (beta<=alpha){
                            break;
                        }
                    } else if (turn == Turn.HUMAN) {
                        if (current.getScore() < bestScore) {
                            bestScore = current.getScore();
                            bestMove = move;
                        }
                        beta=Math.min(beta, current.getScore());
                        if(beta<=alpha){
                            break;
                        }
                    }
                }
                return new CoordValue.MoveScore(bestMove, bestScore);
            }
        }
    }
}