import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;


class gamePlay  {
    private int blackPoint;
    private int whitePoint;
    private boolean isFinished = false;
    char currentColor;

    List<Pair<Integer, Integer>> list = new ArrayList<>();
    List<Pair<Integer, Integer>> listOfPsbMove = new ArrayList<>();


    private char[][] newBoard = new char[][]
            {
                    {'0', '0', '0', '0', '0', '0', '0', '0'},
                    {'0', '0', '0', '0', '0', '0', '0', '0'},
                    {'0', '0', '0', '0', '0', '0', '0', '0'},
                    {'0', '0', '0', 'b', 'w', '0', '0', '0'},
                    {'0', '0', '0', 'w', 'b', '0', '0', '0'},
                    {'0', '0', '0', '0', '0', '0', '0', '0'},
                    {'0', '0', '0', '0', '0', '0', '0', '0'},
                    {'0', '0', '0', '0', '0', '0', '0', '0'}
            };
    char[][] currentBoard = newBoard;

    char[][] getCurrentBoard() {
        return currentBoard;
    }


    gamePlay(){ }

    // possible Move
    void psbMove(char curColor) {
        if (curColor == '\u0000') return;
        listOfPsbMove.clear();
        char sgColor;//suggestColor (r:red for black pieces; g:green for white pieces)
        if (curColor == 'b') sgColor = 'r';
        else sgColor = 'g';
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (currentBoard[i][j] == '0') {
                    check(currentBoard, i, j, curColor);
                    if (list.size() > 0) listOfPsbMove.add(new Pair<>(i, j));
                    list.clear();
                }
            }
        }
    }

    void check(char board[][], int row, int col, char curColor) {
        char _xColor = '0'; // opposite Color
        char suggestColor;
        if (curColor == 'w') {
            _xColor = 'b';
            suggestColor = 'g';
        } else {
            _xColor = 'w';
            suggestColor = 'r';
        }
        //up
        if (col > 0) {
            if (board[row][col - 1] == _xColor) {
                for (int i = 2; col - i > -1; i++) {
                    if (board[row][col - i] == curColor) {
                        board[row][col] = suggestColor;
                        list.add(new Pair<>(row, col - i));
                        break;
                    }
                    if (board[row][col - i] == '0') break;
                    if (board[row][col - i] == suggestColor) break;
                }
            }
        }
        //down
        if (col < 7) {
            if (board[row][col + 1] == _xColor) {
                for (int i = 2; col + i < 8; i++) {
                    if (board[row][col + i] == curColor) {
                        board[row][col] = suggestColor;
                        list.add(new Pair<>(row, col + i));
                        break;
                    }
                    if (board[row][col + i] == '0') break;
                    if (board[row][col + i] == suggestColor) break;
                }
            }
        }

        //right
        if (row < 7) {
            if (board[row + 1][col] == _xColor) {
                for (int i = 2; row + i < 8; i++) {
                    if (board[row + i][col] == curColor) {
                        board[row][col] = suggestColor;
                        list.add(new Pair<>(row + i, col));

                        break;
                    }
                    if (board[row + i][col] == '0') break;
                    if (board[row + i][col] == suggestColor) break;
                }
            }
        }
        //left
        if (row > 0) {
            if (board[row - 1][col] == _xColor) {
                for (int i = 2; row - i > -1; i++) {
                    if (board[row - i][col] == curColor) {
                        board[row][col] = suggestColor;
                        list.add(new Pair<>(row - i, col));
                        break;
                    }
                    if (board[row - i][col] == '0') break;
                    if (board[row - i][col] == suggestColor) break;
                }
            }
        }
        //upleft
        if (row > 0 && col > 0) {
            if (board[row - 1][col - 1] == _xColor) {
                for (int i = 2; row - i > -1 && col - i > -1; i++) {
                    if (board[row - i][col - i] == curColor) {
                        board[row][col] = suggestColor;
                        list.add(new Pair<>(row - i, col - i));
                        break;
                    }
                    if (board[row - i][col - i] == '0') break;
                    if (board[row - i][col - i] == suggestColor) break;
                }
            }
        }

        //upRight
        if (row < 7 && col > 0)
            if (board[row + 1][col - 1] == _xColor) {
                for (int i = 2; row + i < 8 && col - i > -1; i++) {
                    if (board[row + i][col - i] == curColor) {
                        board[row][col] = suggestColor;
                        list.add(new Pair<>(row + i, col - i));
                        break;
                    }
                    if (board[row + i][col - i] == '0') break;
                    if (board[row + i][col - i] == suggestColor) break;
                }
            }
        //downLeft
        if (row > 0 && col < 7)
            if (board[row - 1][col + 1] == _xColor) {
                for (int i = 2; col + i < 8 && row - i > -1; i++) {
                    if (board[row - i][col + i] == curColor) {
                        board[row][col] = suggestColor;
                        list.add(new Pair<>(row - i, col + i));
                        break;
                    }
                    if (board[row - i][col + i] == '0') break;
                    if (board[row - i][col + i] == suggestColor) break;
                }
            }
        //downRight
        if (row < 7 && col < 7)
            if (board[row + 1][col + 1] == _xColor) {
                for (int i = 2; col + i < 8 && row + i < 8; i++) {
                    if (board[row + i][col + i] == curColor) {
                        board[row][col] = suggestColor;
                        list.add(new Pair<>(row + i, col + i));
                        break;
                    }
                    if (board[row + i][col + i] == '0') break;
                    if (board[row + i][col + i] == suggestColor) break;
                }
            }

    }

    void changePieces(char board[][], int row1, int col1, int row2, int col2, char curColor) {
        int signRow;
        int signCol;
        int row0 = row1;
        int col0 = col1;
        if (row2 > row1) signRow = 1;
        else if (row2 == row1) signRow = 0;
        else signRow = -1;
        if (col2 > col1) signCol = 1;
        else if (col2 == col1) signCol = 0;
        else signCol = -1;
        while (row0 != row2 || col0 != col2) {
            board[row0][col0] = curColor;

            row0 += signRow;
            col0 += signCol;

        }
    }

    void noEffectBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (currentBoard[i][j] != 'b' && currentBoard[i][j] != 'w') currentBoard[i][j] = '0';
            }
        }
    }



    void colorPoint() {
        blackPoint = 0;
        whitePoint = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (currentBoard[i][j] == 'b') blackPoint += 1;
                if (currentBoard[i][j] == 'w') whitePoint += 1;
            }
        }

    }


    int getBlackPoint() {
        return blackPoint;
    }

    int getWhitePoint() {
        return whitePoint;
    }

    boolean isFinished() {
        if (blackPoint + whitePoint == 64) return true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (currentBoard[i][j] == 'r' || currentBoard[i][j] == 'g') {
                    return false;
                }
            }
        }
        return true;
    }

    boolean isRightMove(int rowNumb, int colNumb, char c) {
        if (rowNumb >= 8 || colNumb >= 8) return false;
        return (currentBoard[rowNumb][colNumb] == c);
    }


}
