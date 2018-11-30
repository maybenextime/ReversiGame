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


public class board extends JPanel {
    private static BufferedImage blackPiece;
    private static BufferedImage whitePiece;
    gamePlay gamePlay = new gamePlay();
    private int whitePoint = 2;
    private int blackPoint = 2;
    private boolean isFinished;
    char curColor;
    char sugColor;
    char[][] currentBoard = gamePlay.getCurrentBoard();
    int humanTurn = 2;
    bot bot;
    private Pair<Integer, Integer> lastMove;
    JButton reset = new JButton();
    private JButton back = new JButton();
    private JButton next = new JButton();

    private List<Pair<char[][], Pair<Integer, Integer>>> listBoardState = new ArrayList<>();
    int currentIndex = -1;


    board() {
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
            Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            img = new ImageIcon(newimg);
            back = new JButton(img);
            back.setToolTipText("BACK");
        } catch (IOException e) {
            e.printStackTrace();
        }
        back.setBounds(450, 100, 50, 50);
        back.setBackground(Color.GRAY);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex > 0) {
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            currentBoard[i][j] = listBoardState.get(currentIndex-1).getKey()[i][j];
                        }
                    }
                    lastMove = listBoardState.get(currentIndex-1).getValue();
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
            Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            img = new ImageIcon(newimg);
            next = new JButton(img);
            next.setToolTipText("next");
        } catch (IOException e) {
            e.printStackTrace();
        }
        next.setBounds(450, 150, 50, 50);
        next.setBackground(Color.GRAY);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex < listBoardState.size() - 1) {
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            currentBoard[i][j] = listBoardState.get(currentIndex+1).getKey()[i][j];
                        }
                    }
                    lastMove = listBoardState.get(currentIndex+1).getValue();
                    currentIndex++;
                    gamePlay.currentBoard = currentBoard;
                    pointUpdate();
                    repaint();
                }
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
            g.fillRect(38 + 50 * lastMove.getValue(), 38 + 50 * lastMove.getKey(), 50, 50);
        }
        drawboard(g);
        drawPiece(g);

    }

    private void drawboard(Graphics g) {
        g.setFont(new Font("1", 1, 15));
        g.setColor(Color.black);
        for (int i = 0; i < 8; i++) {
            g.drawString(String.valueOf(i + 1), 5 + (i + 1) * 50, 15);
            g.drawString(String.valueOf(i + 1), 5, 15 + (i + 1) * 50);
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
        if (sugColor == 'g') g.setColor(Color.GREEN);
        if (sugColor == 'r') g.setColor(Color.RED);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (currentBoard[i][j] == 'b') {
                    g.drawImage(blackPiece, 38 + 50 * j, 38 + 50 * i, 45, 45, null);
                }
                if (currentBoard[i][j] == 'w') {
                    g.drawImage(whitePiece, 38 + 50 * j, 38 + 50 * i, 45, 45, null);
                }
                if (currentBoard[i][j] == sugColor) {
                    g.fillArc(38 + 50 * j, 38 + 50 * i, 45, 45, 0, 360);
                }
            }
        }
    }

    private void botMove() {
        if (humanTurn == 1) {
            if (isFinished) return;
            gamePlay.currentColor = this.curColor;
            bot.attribute(this.gamePlay);
            Pair<Integer, Integer> botMove = bot.move();
            lastMove = botMove;
            gamePlay.check(currentBoard, botMove.getKey(), botMove.getValue(), curColor);
            for (Pair<Integer, Integer> pair : gamePlay.list) {
                gamePlay.changePieces(currentBoard, botMove.getKey(), botMove.getValue(), pair.getKey(), pair.getValue(), curColor);
            }
            gamePlay.list.clear();
            gamePlay.noEffectBoard();
            colorInNextTurn();
            gamePlay.psbMove(curColor);
            board.this.pointUpdate();
            board.this.removeAll();
            board.this.repaint();
            isFinished = board.this.gamePlay.isFinished();
            if (isFinished) {
                if (blackPoint > whitePoint)
                    JOptionPane.showMessageDialog(board.this, "Black Win (" + blackPoint + " - " + whitePoint + ")");
                else if (whitePoint > blackPoint)
                    JOptionPane.showMessageDialog(board.this, "White Win (" + whitePoint + " - " + blackPoint + ")");
                else JOptionPane.showMessageDialog(board.this, "DRAW");
                return;
            }
            humanTurn = 0;
            currentIndex++;
            updateListBoard(currentBoard, currentIndex);
        }
    }

    private void colorInNextTurn() {
        if (curColor == 'w') {
            curColor = 'b';
            sugColor = 'r';
        } else {
            curColor = 'w';
            sugColor = 'g';
        }
    }

    private void pointUpdate() {
        this.gamePlay.colorPoint();
        blackPoint = board.this.gamePlay.getBlackPoint();
        whitePoint = board.this.gamePlay.getWhitePoint();
    }

    void updateListBoard(char[][] board, int currentIndex) {
        char[][] cBoard = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cBoard[i][j] = board[i][j];
            }
        }
        if (currentIndex == listBoardState.size()) {
            listBoardState.add( new Pair<>(cBoard, lastMove));
        } else {
            listBoardState.set(currentIndex, new Pair<>(cBoard, lastMove));
            for (int i = currentIndex + 1; i < listBoardState.size(); i++) {
                listBoardState.remove(i);
            }
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
            if (humanTurn == 0) {
                int col = (e.getX() - 38) / 50;
                int row = (e.getY() - 38) / 50;
                if (col < 8 && row < 8)
                    if (gamePlay.isRightMove(row, col, sugColor)) {
                        gamePlay.currentColor = board.this.curColor;
                        lastMove = new Pair<>(row, col);
                        gamePlay.check(currentBoard, row, col, curColor);
                        for (Pair<Integer, Integer> pair : gamePlay.list) {
                            gamePlay.changePieces(currentBoard, row, col, pair.getKey(), pair.getValue(), curColor);
                        }
                        gamePlay.list.clear();
                        gamePlay.noEffectBoard();
                        colorInNextTurn();
                        gamePlay.psbMove(curColor);
                        board.this.pointUpdate();
                        isFinished = board.this.gamePlay.isFinished();
                        board.this.removeAll();
                        board.this.repaint();
                        if (isFinished) {
                            if (blackPoint > whitePoint)
                                JOptionPane.showMessageDialog(board.this, "Black Win (" + blackPoint + " - " + whitePoint + ")");
                            else if (whitePoint > blackPoint)
                                JOptionPane.showMessageDialog(board.this, "White Win (" + whitePoint + " - " + blackPoint + ")");
                            else JOptionPane.showMessageDialog(board.this, "DRAW");
                            return;
                        }
                        humanTurn = 1;

                        botMove();
                    }
            }
            if (humanTurn == 1) {
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

