import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class game {
    private JFrame frame = new JFrame();
    private JPanel panelCont = new JPanel();
    private board board = new board();
    private firstMenu firstMenu = new firstMenu();
    private JButton start = new JButton("START");
    private JButton reset ;
    private CardLayout c1 = new CardLayout();


    private game() {
        panelCont.setLayout(c1);
        start.setBounds(200, 410, 100, 50);
        firstMenu.start=start;
        panelCont.add(firstMenu, "1");
        c1.show(panelCont, "1");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCont.add(board, "2");
                board.reset=reset;
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
                        firstMenu.humanColor = 'b';
                    }
                    if (firstMenu.choseWhite.isSelected()) {
                        firstMenu.humanColor = 'w';
                    }

                    if (firstMenu.firstPlay.isSelected()) {
                        board.humanTurn = 0;

                    }
                    if (firstMenu.secondPlay.isSelected()) {
                        board.humanTurn = 1;
                    }
                    if (board.humanTurn == 0) {
                        board.curColor = firstMenu.humanColor;
                        if (board.curColor == 'w') {
                            board.sugColor = 'g';
                        } else {
                            board.sugColor = 'r';
                        }
                    } else {
                        if (firstMenu.humanColor == 'w') {
                            board.curColor = 'b';
                            board.sugColor = 'r';
                        } else {
                            board.curColor = 'w';
                            board.sugColor = 'g';
                        }
                    }

                    if (firstMenu.botHard.isSelected()) {
                        board.bot = new botHard();
                    }
                    if (firstMenu.botNormal.isSelected()) {
                        board.bot = new botNormal();
                    }
                    if (firstMenu.botEasy.isSelected()) {
                        board.bot = new botEasy();
                    }
                    board.gamePlay.psbMove(board.curColor);
                    if (board.humanTurn==0) {
                        board.currentIndex++;
                        board.updateListBoard(board.currentBoard, board.currentIndex);
                    }
                    board.repaint();
                    c1.show(panelCont, "2");

                }
            }
        });
        try {
            ImageIcon img = new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("reset.png")));
            Image image = img.getImage();
            Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
            img = new ImageIcon(newimg);
            reset = new JButton(img);
            reset.setToolTipText("RESET GAME");
        } catch (IOException e) {
            e.printStackTrace();
        }
        reset.setBounds(450, 250, 50, 50);
        reset.setBackground(Color.GRAY);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board= new board();
                board.repaint();
                c1.show(panelCont, "1");
            }});

        frame.add(panelCont);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(510, 530);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                game game = new game();

            }
        });

    }
}
