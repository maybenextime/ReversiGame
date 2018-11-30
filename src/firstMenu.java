import javax.swing.*;
import java.awt.*;

public class firstMenu extends JPanel{
    JButton start = new JButton();
    JRadioButton choseWhite = new JRadioButton("white");
    JRadioButton choseBlack = new JRadioButton("Black");
    JRadioButton firstPlay = new JRadioButton("GO FIRST");
    JRadioButton secondPlay = new JRadioButton("GO SECOND");
    JRadioButton botEasy = new JRadioButton("Easy");
    JRadioButton botNormal = new JRadioButton("Normal");
    JRadioButton botHard = new JRadioButton("Hard");
    char humanColor;

    public firstMenu() {
        this.init();
    }
    public void init() {
        this.setLayout(null);
        choseWhite.setBackground(new Color(0xFFF2D6));
        choseWhite.setBounds(120, 100, 100, 50);
        choseBlack.setBackground(new Color(0xFFF2D6));
        choseBlack.setBounds(120, 150, 100, 50);
        firstPlay.setBackground(new Color(0xFFF2D6));
        firstPlay.setBounds(120, 230, 100, 50);
        secondPlay.setBackground(new Color(0xFFF2D6));
        secondPlay.setBounds(120, 280, 100, 50);
        botEasy.setBackground(new Color(0xFFF2D6));
        botEasy.setBounds(120, 360, 100, 50);
        botNormal.setBackground(new Color(0xFFF2D6));
        botNormal.setBounds(220, 360, 100, 50);
        botHard .setBackground(new Color(0xFFF2D6));
        botHard.setBounds(320, 360, 100, 50);
        this.add(choseBlack);
        this.add(choseWhite);
        this.add(firstPlay);
        this.add(secondPlay);
        this.add(botEasy);
        this.add(botNormal);
        this.add(botHard);
        ButtonGroup bgTurn = new ButtonGroup();
        bgTurn.add(firstPlay);
        bgTurn.add(secondPlay);
        ButtonGroup bgColor = new ButtonGroup();
        bgColor.add(choseBlack);
        bgColor.add(choseWhite);
        ButtonGroup bgBot = new ButtonGroup();
        bgBot.add(botEasy);
        bgBot.add(botNormal);
        bgBot.add(botHard);
    }

    @Override
    public void paintComponent(Graphics g) {
        this.add(start);
        g.setColor(new Color(0xFFF2D6));
        g.fillRect(0, 0, 510, 530);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", Font.BOLD, 25));
        g.drawString("REVERSI", 170, 50);
        g.setFont(new Font("Serif", Font.BOLD, 10));
        g.drawString("SELECT YOUR COLOR ", 50, 100);
        g.drawString("SELECT YOUR TURN ", 50, 230);
        g.drawString("SELECT MODE BOT", 50, 360);
    }


}
