import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuComponent extends JComponent implements ActionListener {

    private JButton startBtn = new JButton("Start");
    private JButton exitBtn = new JButton("Exit");

    private static BufferedImage background;
    private static BufferedImage emblem;

    private Font largeFont = new Font("Century", Font.BOLD, 28);
    private Font detailsFont = new Font("Century", Font.PLAIN, 16);
    private Font btnFont = new Font("Century", Font.PLAIN, 20);

    public MenuComponent() {
        startBtn.addActionListener(this);
        exitBtn.addActionListener(this);
    }

    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        try {
            background = ImageIO.read(new File("resources/images/background.jpeg"));
            emblem = ImageIO.read(new File("resources/images/blackjack.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g2.drawImage(background, 0, 0, null);
        g2.drawImage(emblem, 480, 240, null);

        g2.setFont(largeFont);
        g2.setColor(Color.WHITE);
        g2.drawString("Blackjack", 540, 180);

        g2.setFont(detailsFont);
        g2.drawString("Brought to you by Daniel Bartolini", 470, 240);

        exitBtn.setBounds(400, 500, 90, 60);
        exitBtn.setFont(btnFont);

        startBtn.setBounds(720, 500, 90, 60);
        startBtn.setFont(btnFont);

        super.add(startBtn);
        super.add(exitBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Event delegation for Menu component
        JButton selected = (JButton) e.getSource();

        if (selected == exitBtn) {
            System.exit(0);
        } else if (selected == startBtn) {
            Main.currentState = Main.STATE.GAME;

            Main.menuFrame.dispose();

            Main.gameRefreshThread.start();
            Main.gameCheckThread.start();
        }
    }
}