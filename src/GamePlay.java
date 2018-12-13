import java.util.ArrayList;
import java.util.List;


class GamePlay {
    private int blackPoint;
    private int whitePoint;
    CellState currentColor;
    private CellState[][] newBoard = new CellState[8][8];
    CellState[][] currentBoard = newBoard;

    private List<CoordValue.Coord> list = new ArrayList<>();


    CellState[][] getCurrentBoard() {
        return currentBoard;
    }


    GamePlay() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                currentBoard[x][y] = CellState.EMPTY;
            }
        }
        currentBoard[4][3] = CellState.WHITE;
        currentBoard[3][4] = CellState.WHITE;
        currentBoard[3][3] = CellState.BLACK;
        currentBoard[4][4] = CellState.BLACK;
    }

    GamePlay(CellState[][] board, CellState currentColor) {
        this.currentBoard = board;
        this.currentColor = currentColor;
    }

    void effectBoard(CellState board[][], CellState curColor) {
        if (curColor == null) return;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == CellState.EMPTY) {
                    check(board, new CoordValue.Coord(i, j), curColor);
                    list.clear();
                }
            }
        }
    }

    private List<CoordValue.Coord> listSrcPiece(CellState[][] board, CoordValue.Coord coord, CellState curColor) {
        check(board, coord, curColor);
        ArrayList<CoordValue.Coord> listSrcPiece = new ArrayList<>(list);
        list.clear();
        return listSrcPiece;
    }

    List<CoordValue.Coord> moveList(CellState board[][], CellState currentColor) {
        List<CoordValue.Coord> listMove = new ArrayList<>();
        CellState sugColor = CellState.SWHITE;
        if (currentColor == CellState.BLACK) sugColor = CellState.SBLACK;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == sugColor) {
                    listMove.add(new CoordValue.Coord(i, j));
                }
            }
        }
        return listMove;
    }

    private void check(CellState board[][], CoordValue.Coord coord, CellState curColor) {
        if (coord.getCol() > 0) isPosMove(board, curColor, coord, true, false, false, false);
        if (coord.getCol() < 7) isPosMove(board, curColor, coord, false, true, false, false);
        if (coord.getRow() > 0) isPosMove(board, curColor, coord, false, false, true, false);
        if (coord.getRow() < 7) isPosMove(board, curColor, coord, false, false, false, true);
        if (coord.getRow() > 0 && coord.getCol() > 0) isPosMove(board, curColor, coord, true, false, true, false);
        if (coord.getRow() < 7 && coord.getCol() > 0) isPosMove(board, curColor, coord, true, false, false, true);
        if (coord.getRow() > 0 && coord.getCol() < 7) isPosMove(board, curColor, coord, false, true, true, false);
        if (coord.getRow() < 7 && coord.getCol() < 7) isPosMove(board, curColor, coord, false, true, false, true);
    }

    private void isPosMove(CellState[][] board, CellState curColor, CoordValue.Coord coord, boolean up, boolean down, boolean left, boolean right) {
        int numbCol = 0;
        int numbRow = 0;
        if (up) numbCol = -1;
        if (down) numbCol = 1;
        if (left) numbRow = -1;
        if (right) numbRow = 1;
        CellState _xColor ; // opposite Color
        CellState suggestColor;
        if (curColor == CellState.WHITE) {
            _xColor = CellState.BLACK;
            suggestColor = CellState.SWHITE;
        } else {
            _xColor = CellState.WHITE;
            suggestColor = CellState.SBLACK;
        }
        if (board[coord.getRow() + numbRow][coord.getCol() + numbCol] == _xColor) {
            for (int i = 2; coord.getCol() + numbCol * i > -1 && coord.getCol() + numbCol * i < 8 && coord.getRow() + numbRow * i > -1 && coord.getRow() + numbRow * i < 8; i++) {
                if (board[coord.getRow() + numbRow * i][coord.getCol() + numbCol * i] == curColor) {
                    board[coord.getRow()][coord.getCol()] = suggestColor;
                    list.add(new CoordValue.Coord(coord.getRow() + numbRow * i, coord.getCol() + numbCol * i));
                    break;
                }
                if (board[coord.getRow() + numbRow * i][coord.getCol() + numbCol * i] == CellState.EMPTY) break;
                if (board[coord.getRow() + numbRow * i][coord.getCol() + numbCol * i] == suggestColor) break;
            }
        }
    }

    private void changePieces(CellState board[][], CoordValue.Coord coord1, CoordValue.Coord coord2, CellState curColor) {
        int signRow;
        int signCol;
        int row0 = coord1.getRow();
        int col0 = coord1.getCol();

        signRow = Integer.compare(coord2.getRow(), coord1.getRow());
        signCol = Integer.compare(coord2.getCol(), coord1.getCol());
        while (row0 != coord2.getRow() || col0 != coord2.getCol()) {
            board[row0][col0] = curColor;
            row0 += signRow;
            col0 += signCol;

        }
    }

    private void noEffectBoard(CellState board[][]) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != CellState.BLACK && board[i][j] != CellState.WHITE) board[i][j] = CellState.EMPTY;
            }
        }
    }

    void colorPoint(CellState board[][]) {
        blackPoint = 0;
        whitePoint = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == CellState.BLACK) blackPoint += 1;
                if (board[i][j] == CellState.WHITE) whitePoint += 1;
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
                if (currentBoard[i][j] == CellState.SBLACK || currentBoard[i][j] == CellState.SWHITE) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean isRightMove(CoordValue.Coord coord, CellState state) {
        if (coord.getRow() >= 8 || coord.getCol() >= 8) return false;
        return (currentBoard[coord.getRow()][coord.getCol()] == state);
    }

    void applyMove(CellState[][] board, CoordValue.Coord coord, CellState curColor) {
        check(board, coord, curColor);
        ArrayList<CoordValue.Coord> listSrcPiece = new ArrayList<>(listSrcPiece(board, coord, curColor));
        for (CoordValue.Coord coord2 : listSrcPiece) {
            changePieces(currentBoard, coord, coord2, curColor);
        }
        noEffectBoard(currentBoard);
    }

}


