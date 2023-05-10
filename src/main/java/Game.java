import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Game {

    ArrayList<Card> dealerHand;
    ArrayList<Card> playerHand;

    public boolean faceDown;
    public boolean dealerWon;
    public volatile boolean roundOver;

    JFrame frame;
    Deck deck;

    GameComponent atmosphereComponent;
    GameComponent cardComponent;

    JButton hitBtn;
    JButton standBtn;
    JButton doubleBtn;
    JButton exitBtn;

    private Font btnFont = new Font("Century", Font.PLAIN, 20);

    public Game(JFrame f) {
        deck = new Deck();
        deck.shuffleDeck();
        dealerHand = new ArrayList<Card>();
        playerHand = new ArrayList<Card>();
        atmosphereComponent = new GameComponent(dealerHand, playerHand);

        frame = f;
        faceDown = true;
        dealerWon = true;
        roundOver = false;
    }

    public void formGame() {
        System.out.println("New Game Formed");
        frame.setTitle("Blackjack");
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        hitBtn = new JButton("Hit");
        hitBtn.setBounds(300, 620, 90, 60);
        hitBtn.setFont(btnFont);
        standBtn = new JButton("Stand");
        standBtn.setBounds(555, 620, 90, 60);
        standBtn.setFont(btnFont);
        exitBtn = new JButton("Exit");
        exitBtn.setBounds(900, 620, 90, 60);

        frame.add(hitBtn);
        frame.add(standBtn);
        frame.add(exitBtn);

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "You finished with " + Main.chipBalance + " chips.");
                System.exit(0);
            }
        });

        atmosphereComponent = new GameComponent(dealerHand, playerHand);
        atmosphereComponent.setBounds(0, 0, 1200, 800);
        frame.add(atmosphereComponent);
        frame.setVisible(true);
    }

    public void startGame() {

        for (int i = 0; i < 2; i++) {
            dealerHand.add(deck.getCard(i));
        }
        for (int i = 0; i < 2; i++) {
            playerHand.add(deck.getCard(i));
        }
        for (int i = 0; i < 4; i++) {
            deck.removeCard(0);
        }

        cardComponent = new GameComponent(dealerHand, playerHand);
        cardComponent.setBounds(0, 0, 1200, 800);
        frame.add(cardComponent);
        frame.setVisible(true);

        checkHand(dealerHand);
        checkHand(playerHand);

        hitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCard(playerHand);
                checkHand(playerHand);

                if (getHandValue(playerHand) < 17 && getHandValue(dealerHand) < 17) {
                    addCard(dealerHand);
                    checkHand(dealerHand);
                }
            }
        });

        doubleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCard(playerHand);
                GameComponent.currentBet = GameComponent.currentBet * 2;
            }
        });

        standBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while (getHandValue(dealerHand) < 17) {
                    addCard(dealerHand);
                    checkHand(dealerHand);
                }

                if ((getHandValue(dealerHand) < 21) && (getHandValue(playerHand) < 21)) {
                    if (getHandValue(playerHand) > getHandValue(dealerHand)) {
                        faceDown = false;
                        dealerWon = false;
                        JOptionPane.showMessageDialog(frame, "You win!");
                        rest();
                        roundOver = true;
                    } else {
                        faceDown = false;
                        JOptionPane.showMessageDialog(frame, "Dealer wins.");
                        rest();
                        roundOver = true;
                    }
                }
            }
        });
    }

    public void checkHand (ArrayList<Card> hand) {
        if (hand.equals(playerHand)) {
            if (getHandValue(hand) == 21) {
                faceDown = false;
                dealerWon = false;
                JOptionPane.showMessageDialog(frame, "You win");
                rest();
                roundOver = true;
            } else if (getHandValue(hand) > 21) {
                faceDown = false;
                JOptionPane.showMessageDialog(frame, "You busted! Dealer wins.");
                rest();
                roundOver = true;
            }
        } else {
            if (getHandValue(hand) == 21) {
                faceDown = false;
                JOptionPane.showMessageDialog(frame, "Dealer got blackjack. Dealer wins.");
                rest();
                roundOver = true;
            } else if (getHandValue(hand) > 21) {
                faceDown = false;
                dealerWon = false;
                JOptionPane.showMessageDialog(frame, "Dealer busted. You win!");
                rest();
                roundOver = true;
            }
        }
    }

    public void addCard(ArrayList<Card> hand) {
        hand.add(deck.getCard(0));
        deck.removeCard(0);
        faceDown = true;
    }

    public boolean hasAce(ArrayList<Card> hand) {
        for (Card card : hand) {
            if (card.getValue() == 11) {
                return true;
            }
        }
        return false;
    }

    public int acesInHand(ArrayList<Card> hand) {
        int acesCount = 0;
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getValue() == 11) {
                acesCount++;
            }
        }
        return acesCount;
    }

    public int getHandWithHighAce(ArrayList<Card> hand) {
        int handValue = 0;
        for (Card card : hand) {
            handValue += card.getValue();
        }
        return handValue;
    }

    public int getHandValue(ArrayList<Card> hand) {
        if (hasAce(hand)) {
            if (getHandWithHighAce(hand) <= 21) {
                return getHandWithHighAce(hand);
            } else {
                for (int i = 0; i < acesInHand(hand); i++) {
                    int handValue = getHandWithHighAce(hand) - (i + 1) * 10;
                    if (handValue <= 21) {
                        return handValue;
                    }
                }
            }
        } else {
            int handValue = 0;
            for (int i = 0; i < hand.size(); i++) {
                handValue += hand.get(i).getValue();
            }
            return handValue;
        }
        return 22;
    }

    public static void rest() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}