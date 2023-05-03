import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    // window resolution
    int aW = 1280;
    int aH = 800;

    // colors
    Color backgroundColor = new Color(58, 145, 65);

    public GUI() {
        this.setSize(aW + 6, aH + 29);
        this.setTitle("Blackjack");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // kill the thread, terminate process, on close

        Board board = new Board();
        board.setPreferredSize(new Dimension(aW, aH));
        this.add(board); // set panel inside frame (Frame -> Panel)
        this.pack();
        this.setVisible(true);

    }

    public class Board extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.setColor(backgroundColor);
            g.fillRect(0, 0, aW, aH);
        }
    }
}
