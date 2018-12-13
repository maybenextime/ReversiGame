import javafx.util.Pair;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Board extends JPanel {
    GamePlay gamePlay = new GamePlay();
    CellState[][] currentBoard = gamePlay.getCurrentBoard();
    private List<Pair<CellState[][], CoordValue.Coord>> listBoardState = new ArrayList<>();
    private boolean isFinished;
    private CoordValue.Coord lastMove;
    Bot bot;
    CellState curColor;
    CellState sugColor;
    Turn turn = Turn.NONE;
    private int whitePoint = 2;
    private int blackPoint = 2;
    JButton reset = new JButton();
    private JButton back = new JButton();
    private JButton next = new JButton();
    int currentIndex = -1;
    private static int sizePiece=45;
    private static int sizeSquare=50;
    private static BufferedImage whitePiece;
    private static BufferedImage blackPiece;





    Board() {
        this.setPreferredSize(new Dimension(510, 530));
        this.init();
        this.setLayout(null);
        this.add(reset);
    }

    private void init() {
        this.addMouseListener(new Mouse());
        try {
            blackPiece = ImageIO.read(getClass().getResourceAsStream("black.png"));
            whitePiece = ImageIO.read(getClass().getResourceAsStream("white.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.add(reset);
        try {
            ImageIcon img = new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("back.png")));
            Image image = img.getImage();
            Image newimg = image.getScaledInstance(sizeSquare, sizeSquare, java.awt.Image.SCALE_SMOOTH);
            img = new ImageIcon(newimg);
            back = new JButton(img);
            back.setToolTipText("BACK");
        } catch (IOException e) {
            e.printStackTrace();
        }
        back.setBounds(450, 100, sizeSquare, sizeSquare);
        back.setBackground(Color.GRAY);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex > 0) {
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            currentBoard[i][j] = listBoardState.get(currentIndex - 1).getKey()[i][j];
                        }
                    }
                    lastMove = listBoardState.get(currentIndex - 1).getValue();
                    gamePlay.currentBoard = currentBoard;
                    currentIndex--;
                    pointUpdate();
                    repaint();
                }
            }
        });
        this.add(back);

        try {
            ImageIcon img = new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("next.png")));
            Image image = img.getImage();
            Image newimg = image.getScaledInstance(sizeSquare, sizeSquare, java.awt.Image.SCALE_SMOOTH);
            img = new ImageIcon(newimg);
            next = new JButton(img);
            next.setToolTipText("next");
        } catch (IOException e) {
            e.printStackTrace();
        }
        next.setBounds(450, 150, sizeSquare, sizeSquare);
        next.setBackground(Color.GRAY);
        next.addActionListener(e -> {
            if (currentIndex < listBoardState.size() - 1) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        currentBoard[i][j] = listBoardState.get(currentIndex + 1).getKey()[i][j];
                    }
                }
                lastMove = listBoardState.get(currentIndex + 1).getValue();
                currentIndex++;
                gamePlay.currentBoard = currentBoard;
                pointUpdate();
                repaint();
            }
        });
        this.add(next);

    }

    public void paintComponent(Graphics g) {
        this.add(reset);
        this.add(back);
        this.add(next);
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, 510, 470);
        g.setColor(Color.white);
        g.fillRect(0, 470, 510, 60);
        if (lastMove != null) {
            g.setColor(Color.orange);
            g.fillRect(38 + sizeSquare * lastMove.getCol(), 38 + sizeSquare * lastMove.getRow(), sizeSquare, sizeSquare);
        }
        drawboard(g);
        drawPiece(g);
    }

    private void drawboard(Graphics g) {
        g.setFont(new Font("1", Font.BOLD, 15));
        g.setColor(Color.black);
        for (int i = 0; i < 8; i++) {
            g.drawString(String.valueOf(i + 1), 5 + (i + 1) * sizeSquare, 15);
            g.drawString(String.valueOf(i + 1), 5, 15 + (i + 1) * sizeSquare);
        }
        g.setColor(Color.white);
        g.fillRect(25, 25, 3, 420);
        g.fillRect(25, 25, 420, 3);
        g.fillRect(25, 445, 423, 3);
        g.fillRect(445, 25, 3, 423);
        for (int i = 0; i < 9; i++) {
            g.fillRect(35 + 50 * i, 35, 1, 400);
            g.fillRect(35, 35 + 50 * i, 400, 1);
        }
        g.setColor(new Color(0x920D10));
        g.setFont(new Font("1", Font.BOLD, 20));
        g.drawString("White: " + whitePoint + "   -   Black: " + blackPoint, 150, 490);
    }

    private void drawPiece(Graphics g) {
        if (sugColor == CellState.SWHITE) g.setColor(Color.GREEN);
        if (sugColor == CellState.SBLACK) g.setColor(Color.RED);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (currentBoard[i][j] == CellState.BLACK) {
                    g.drawImage(blackPiece, 38 + sizeSquare * j, 38 + sizeSquare * i, sizePiece, sizePiece, null);
                }
                if (currentBoard[i][j] == CellState.WHITE) {
                    g.drawImage(whitePiece, 38 + sizeSquare * j, 38 + sizeSquare * i, sizePiece, sizePiece, null);
                }
                if (currentBoard[i][j] == sugColor) {
                    g.fillArc(38 + sizeSquare * j, 38 + sizeSquare * i, sizePiece, sizePiece, 0, 360);
                }
            }
        }
    }

    private void botMove() {
        if (turn == Turn.BOT) {
            gamePlay.currentColor = this.curColor;
            if (isFinished) return;
            bot.attribute(this.gamePlay);
            CoordValue.Coord botMove = bot.move();
            lastMove = botMove;
            applyMoveToBoard(gamePlay, botMove);
            turn = Turn.HUMAN;
            currentIndex++;
            updateListBoard(currentBoard, currentIndex);
        }
    }

    private void colorInNextTurn() {
        if (curColor == CellState.WHITE) {
            curColor = CellState.BLACK;
            sugColor = CellState.SBLACK;
        } else {
            curColor = CellState.WHITE;
            sugColor = CellState.SWHITE;
        }
    }

    private void pointUpdate() {
        this.gamePlay.colorPoint(currentBoard);
        blackPoint = Board.this.gamePlay.getBlackPoint();
        whitePoint = Board.this.gamePlay.getWhitePoint();
    }

    void updateListBoard(CellState[][] board, int currentIndex) {
        CellState[][] cBoard = new CellState[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cBoard[i][j] = board[i][j];
            }
        }
        if (currentIndex == listBoardState.size()) {
            listBoardState.add(new Pair<>(cBoard, lastMove));
        } else {
            listBoardState.set(currentIndex, new Pair<>(cBoard, lastMove));
            for (int i = currentIndex + 1; i < listBoardState.size(); i++) {
                listBoardState.remove(i);
            }
        }
    }


    private void applyMoveToBoard(GamePlay gamePlay, CoordValue.Coord coord) {

        gamePlay.currentColor = Board.this.curColor;
        gamePlay.applyMove(gamePlay.currentBoard, coord, gamePlay.currentColor);
        colorInNextTurn();
        gamePlay.effectBoard(currentBoard, curColor);
        gamePlay.currentColor = Board.this.curColor;
        Board.this.pointUpdate();
        isFinished = Board.this.gamePlay.isFinished();
        Board.this.removeAll();
        Board.this.repaint();
        if (isFinished) {
            if (blackPoint > whitePoint)
                JOptionPane.showMessageDialog(Board.this, "Black Win (" + blackPoint + " - " + whitePoint + ")");
            else if (whitePoint > blackPoint)
                JOptionPane.showMessageDialog(Board.this, "White Win (" + whitePoint + " - " + blackPoint + ")");
            else JOptionPane.showMessageDialog(Board.this, "DRAW");
        }
    }



    private class Mouse implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (turn == Turn.HUMAN) {
                int col = (e.getX() - 38) / 50;
                int row = (e.getY() - 38) / 50;
                if (col < 8 && row < 8)
                    if (gamePlay.isRightMove(new CoordValue.Coord(row, col), sugColor)) {
                        lastMove = new CoordValue.Coord(row, col);
                        applyMoveToBoard(gamePlay, new CoordValue.Coord(row, col));
                        turn = Turn.BOT;
                        botMove();
                    }
            }
            if (turn == Turn.BOT) {
                botMove();
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}

