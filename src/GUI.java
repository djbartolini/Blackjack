import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class GUI extends JFrame {

    // window resolution
    int aW = 1280;
    int aH = 800;

    // grid dimensions
    int gridX = 50;
    int gridY = 50;
    int gridW = 900;
    int gridH = 400;

    // hit & stay button positions
    int btnGridX = gridX + gridW + 60;
    int btnGridY = gridY;
    int btnGridW = 240;
    int btnGridH = 400;

    // card dimensions
    int cardSpacing = 8;
    int cardArc = 8;
    int cardTW = gridW / 6;
    int cardTH = gridH / 2;
    int cardAW = cardTW - 2 * cardSpacing;
    int cardAH = cardTH - 2 * cardSpacing;

    // colors
    Color backgroundColor = new Color(58, 145, 65);
    Color buttonColor = new Color(30, 30, 30);
    Color textColor = new Color(230, 230, 230);

    // fonts
    Font buttonFont = new Font("Verdana", Font.PLAIN, 18);
    Font cardFont = new Font("Times New Roman", Font.BOLD, 32);

    // buttons
    JButton btnHit = new JButton("Hit");
    JButton btnStay = new JButton("Stay");
    JButton btnYes = new JButton("Yes");
    JButton btnNo = new JButton("No");

    // arraylist of cards
    ArrayList<Card> allCards = new ArrayList<Card>();
    ArrayList<Card> playerCards = new ArrayList<Card>();
    ArrayList<Card> dealerCards = new ArrayList<Card>();

    int randomNum = new Random().nextInt(52); // random int between 0 and 52

    public GUI() {
        this.setSize(aW + 6, aH + 29);
        this.setTitle("Blackjack");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // kill the thread, terminate process, on close

        Board board = new Board();
        board.setPreferredSize(new Dimension(aW, aH));
        board.setLayout(null);
        board.setLocation(0, 0);
        this.add(board); // set panel inside frame (Frame -> Panel)
        this.pack();
        this.setVisible(true);

        ActHit aHit = new ActHit();
        btnHit.addActionListener(aHit);
        btnHit.setBounds(1080, 240, 96, 64);
        btnHit.setBackground(buttonColor);
        btnHit.setForeground(textColor);
        btnHit.setFocusPainted(false);
        btnHit.setOpaque(true);
        btnHit.setBorderPainted(false);
        btnHit.setContentAreaFilled(true);
        btnHit.setFont(buttonFont);
        board.add(btnHit);

        ActStay aStay = new ActStay();
        btnStay.addActionListener(aStay);
        btnStay.setBounds(1080, 360, 96, 64);
        btnStay.setBackground(buttonColor);
        btnStay.setForeground(textColor);
        btnStay.setFocusPainted(false);
        btnStay.setOpaque(true);
        btnStay.setBorderPainted(false);
        btnStay.setContentAreaFilled(true);
        btnStay.setFont(buttonFont);
        board.add(btnStay);

        ActYes aYes = new ActYes();
        btnYes.addActionListener(aYes);
        btnYes.setBounds(1080 - 52, 660, 96, 48);
        btnYes.setBackground(buttonColor);
        btnYes.setForeground(textColor);
        btnYes.setFocusPainted(false);
        btnYes.setOpaque(true);
        btnYes.setBorderPainted(false);
        btnYes.setContentAreaFilled(true);
        btnYes.setFont(buttonFont);
        board.add(btnYes);

        ActNo aNo = new ActNo();
        btnNo.addActionListener(aNo);
        btnNo.setBounds(1080 + 52, 660, 96, 48);
        btnNo.setBackground(buttonColor);
        btnNo.setForeground(textColor);
        btnNo.setFocusPainted(false);
        btnNo.setOpaque(true);
        btnNo.setBorderPainted(false);
        btnNo.setContentAreaFilled(true);
        btnNo.setFont(buttonFont);
        board.add(btnNo);

        // Construct the deck
        String suitS1;
        int id_setter = 0;
        for (int s = 0; s < 4; s++) {
            if (s == 0) {
                suitS1 = "Spades";
            } else if (s == 1) {
                suitS1 = "Hearts";
            } else if (s == 2) {
                suitS1 = "Clubs";
            } else {
                suitS1 = "Diamonds";
            }

            for (int i = 2; i < 15; i++) {
                allCards.add(new Card(i, suitS1, id_setter));
                id_setter++;
            }
        }

        randomNum = new Random().nextInt(52);
        playerCards.add(allCards.get(randomNum));
        allCards.get(randomNum).isDiscarded = true;

        randomNum = new Random().nextInt(52);
        while (true) {
            if (!allCards.get(randomNum).isDiscarded) {
                dealerCards.add(allCards.get(randomNum));
                allCards.get(randomNum).isDiscarded = true;
                break;
            } else {
                randomNum = new Random().nextInt(52);
            }
        }

        randomNum = new Random().nextInt(52);
        while (true) {
            if (!allCards.get(randomNum).isDiscarded) {
                playerCards.add(allCards.get(randomNum));
                allCards.get(randomNum).isDiscarded = true;
                break;
            } else {
                randomNum = new Random().nextInt(52);
            }
        }

        randomNum = new Random().nextInt(52);
        while (true) {
            if (!allCards.get(randomNum).isDiscarded) {
                dealerCards.add(allCards.get(randomNum));
                allCards.get(randomNum).isDiscarded = true;
                break;
            } else {
                randomNum = new Random().nextInt(52);
            }
        }

        for (Card c : playerCards) {
            System.out.println("Player had the card " + c.name + " of " + c.suit);
        }
        for (Card c : dealerCards) {
            System.out.println("Dealer had the card " + c.name + " of " + c.suit);
        }
    }

    public class Board extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(backgroundColor);
            g.fillRect(0, 0, aW, aH);

            // paint grid
            g.setColor(Color.BLACK);
            // top section - dealer
            g.drawRect(gridX, gridY, gridW, gridH);
            // bottom section - player
            g.drawRect(gridX, gridY + gridH + 60, gridW, gridH);
            // right section - hit & stay buttons
            g.drawRect(btnGridX, btnGridY, btnGridW, btnGridH);

            int index = 0;
            for (Card c : playerCards) {
                g.setColor(Color.WHITE);
                g.fillRoundRect(gridX + (index * cardTW + cardSpacing), gridY + cardSpacing, cardAW, cardAH, cardArc, cardArc);
                g.setColor(Color.BLACK);

                if (c.suit.equalsIgnoreCase("Hearts") || c.suit.equalsIgnoreCase("Diamonds")) {
                    g.setColor(Color.RED);
                }

                g.setFont(cardFont);
                g.drawString(c.symbol, gridX + (index * cardTW + cardSpacing * 2), gridY + cardAH);

                if (c.suit.equalsIgnoreCase("Spades")) {

                }

                index++;
            }
        }
    }

    public class ActHit implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("You clicked the Hit button");
        }
    }

    public class ActStay implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("You clicked the Stay button");
        }
    }

    public class ActYes implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("You clicked the Yes button");
        }
    }

    public class ActNo implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("You clicked the No button");
        }
    }
}
