import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameComponent extends JComponent implements MouseListener {

    public BufferedImage background;
    public BufferedImage emblem;
    public BufferedImage chip;

    private Font largeFont = new Font("Century", Font.BOLD, 24);
    private Font detailsFont = new Font("Century", Font.PLAIN, 14);
    private Font balanceFont = new Font("Century", Font.PLAIN, 18);

    private ArrayList<Card> dealerHand;
    private ArrayList<Card> playerHand;

    private int dealerScore;
    private int playerScore;

    public boolean faceDown = true;
    public boolean betMade = false;
    private int chipBalance;
    public static int currentBet;

    public GameComponent(ArrayList<Card> dH, ArrayList<Card> pH) {
        dealerHand = dH;
        playerHand = pH;
        dealerScore = 0;
        playerScore = 0;
        chipBalance = 1000;
        addMouseListener(this);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        try {
            background = ImageIO.read(new File("resources/images/background.jpeg"));
            emblem = ImageIO.read(new File("resources/images/blackjack-emblem.png"));
            chip = ImageIO.read(new File("resources/images/chip.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g2.drawImage(background, 0, 0, null);
        g2.drawImage(emblem, 480, 250, null);
        g2.drawImage(chip, 120, 580, null);

        // Dealer & player board section labels
        g2.setColor(Color.WHITE);
        g2.setFont(largeFont);
        g2.drawString("Dealer", 560, 240);
        g2.drawString("Player", 560, 610);

        // Player instructions
        g2.setFont(detailsFont);
        g2.drawString("Place your bet by clicking on the chip to start the round.", 36, 740);

        // Player chip balance
        g2.setFont(balanceFont);
        g2.drawString("Current balance: " + chipBalance, 540, 740);

        // Hand values
        g2.drawString("Your hand: " + Game.getHandValue(playerHand), 900, 660);

        // Dealer draws
        try {
            for (int i = 0; i < dealerHand.size(); i++) {
                if (i == 0) {
                    if (faceDown) {
                        dealerHand.get(i).printCard(g2, true, true, i);
                    } else {
                        dealerHand.get(i).printCard(g2, true, false, i);
                        g2.drawString("Dealer's hand: " + Game.getHandValue(dealerHand), 900, 120);
                    }
                } else {
                    dealerHand.get(i).printCard(g2, true, false, i);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Player draws
        try {
            for (int i = 0; i < playerHand.size(); i++) {
                playerHand.get(i).printCard(g2, false, false, i);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void refresh(int cB, int pS, int dS, boolean fD) {
        chipBalance = cB;
        playerScore = pS;
        dealerScore = dS;
        faceDown = fD;
        this.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int eX = e.getX();
        int eY = e.getY();

        if (eX >= 120 && eX <= 220 && eY >= 580 && eY <= 680) {
            betMade = true;
            String response = JOptionPane.showInputDialog(null, "Please enter your betting amount!", "BETTING", JOptionPane.PLAIN_MESSAGE);

            try {
                currentBet = Integer.parseInt(response);
            } catch (NumberFormatException ex) {
                currentBet = 1;
            }

            chipBalance -= currentBet;
            Main.newGame.startGame();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}