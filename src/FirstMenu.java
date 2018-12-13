import javax.swing.*;
import java.awt.*;

public class FirstMenu extends JPanel{
    JButton start = new JButton();
    JRadioButton choseWhite = new JRadioButton("white");
    JRadioButton choseBlack = new JRadioButton("Black");
    JRadioButton firstPlay = new JRadioButton("GO FIRST");
    JRadioButton secondPlay = new JRadioButton("GO SECOND");
    JRadioButton botEasy = new JRadioButton("Easy");
    JRadioButton botNormal = new JRadioButton("Normal");
    JRadioButton botHard = new JRadioButton("Hard");
    CellState humanColor;
    private static Color backGroundColor= new Color(0xFFF2D6);
    private static int button_width= 100;
    private static int button_height= 50;



    FirstMenu() {
        this.init();
    }
    private void init() {
        this.setLayout(null);
        choseWhite.setBackground(backGroundColor);
        choseWhite.setBounds(120, 100, button_width, button_height);
        choseBlack.setBackground(backGroundColor);
        choseBlack.setBounds(120, 150, button_width, button_height);
        firstPlay.setBackground(backGroundColor);
        firstPlay.setBounds(120, 230, button_width, button_height);
        secondPlay.setBackground(backGroundColor);
        secondPlay.setBounds(120, 280, button_width, button_height);
        botEasy.setBackground(backGroundColor);
        botEasy.setBounds(120, 360, button_width, button_height);
        botNormal.setBackground(backGroundColor);
        botNormal.setBounds(220, 360, button_width, button_height);
        botHard .setBackground(backGroundColor);
        botHard.setBounds(320, 360, button_width, button_height);
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
        g.setColor(backGroundColor);
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
