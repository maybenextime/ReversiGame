import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Game {
    private JFrame frame = new JFrame();
    private JPanel panelCont = new JPanel();
    private Board board = new Board();
    private FirstMenu firstMenu = new FirstMenu();
    private JButton start = new JButton("START");
    private JButton reset;
    private CardLayout c1 = new CardLayout();


    private Game() {
        panelCont.setLayout(c1);
        start.setBounds(200, 410, 100, 50);
        firstMenu.start = start;
        panelCont.add(firstMenu, "1");
        c1.show(panelCont, "1");
        start.addActionListener(e -> {
            panelCont.add(board, "2");
            board.reset = reset;
            if (!firstMenu.choseBlack.isSelected() && !firstMenu.choseWhite.isSelected())
                JOptionPane.showMessageDialog(frame, "Select Color");
            if (!firstMenu.firstPlay.isSelected() && !firstMenu.secondPlay.isSelected())
                JOptionPane.showMessageDialog(frame, "Select Turn");
            if (!firstMenu.botHard.isSelected() && !firstMenu.botNormal.isSelected() && !firstMenu.botEasy.isSelected())
                JOptionPane.showMessageDialog(frame, "Select Bot");
            if ((firstMenu.choseBlack.isSelected() || firstMenu.choseWhite.isSelected()) &&
                    (firstMenu.firstPlay.isSelected() || firstMenu.secondPlay.isSelected()) &&
                    (firstMenu.botHard.isSelected() || firstMenu.botNormal.isSelected() || firstMenu.botEasy.isSelected())) {
                if (firstMenu.choseBlack.isSelected()) {
                    firstMenu.humanColor = CellState.BLACK;
                }
                if (firstMenu.choseWhite.isSelected()) {
                    firstMenu.humanColor = CellState.WHITE;
                }

                if (firstMenu.firstPlay.isSelected()) {
                    board.turn = Turn.HUMAN;

                }
                if (firstMenu.secondPlay.isSelected()) {
                    board.turn = Turn.BOT;
                }
                if (board.turn == Turn.HUMAN) {
                    board.curColor = firstMenu.humanColor;
                    if (board.curColor == CellState.WHITE) {
                        board.sugColor = CellState.SWHITE;
                    } else {
                        board.sugColor = CellState.SBLACK;
                    }
                } else {
                    if (firstMenu.humanColor == CellState.WHITE) {
                        board.curColor = CellState.BLACK;
                        board.sugColor = CellState.SBLACK;
                    } else {
                        board.curColor = CellState.WHITE;
                        board.sugColor = CellState.SWHITE;
                    }
                }

                if (firstMenu.botHard.isSelected()) {
                    board.bot = new BotHard();
                }
                if (firstMenu.botNormal.isSelected()) {
                    board.bot = new BotNormal();
                }
                if (firstMenu.botEasy.isSelected()) {
                    board.bot = new BotEasy();
                }
                board.gamePlay.effectBoard(board.currentBoard, board.curColor);
                if (board.turn == Turn.HUMAN) {
                    board.currentIndex++;
                    board.updateListBoard(board.currentBoard, board.currentIndex);
                }
                board.repaint();
                c1.show(panelCont, "2");

            }
        });
        try {
            ImageIcon img = new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("reset.png")));
            Image image = img.getImage();
            Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            img = new ImageIcon(newimg);
            reset = new JButton(img);
            reset.setToolTipText("RESET GAME");
        } catch (IOException e) {
            e.printStackTrace();
        }
        reset.setBounds(450, 250, 50, 50);
        reset.setBackground(Color.GRAY);
        reset.addActionListener(e -> {
            board = new Board();
            board.repaint();
            c1.show(panelCont, "1");
        });

        frame.add(panelCont);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(510, 530);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            Game game = new Game();

        });

    }
}
