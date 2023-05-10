import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Card {

    /* Card properties:
      ,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
      |        |  0      | 1       | 2      | 3       | 4  | 5 |  6 |  7 |  8 | 9  | 10            |  11 | 12   |
      | Suit:  |  Clubs  | Diamonds| Hearts | Spades  |    |   |    |    |    |    |               |     |      |
      | Rank:  |  Ace    | 2       | 3      | 4       | 5  | 6 |  7 |  8 |  9 | 10 | J             |  Q  | K    |
      | Value: |         | 1, Ace  | 2      | 3       | 4  | 5 |  6 |  7 |  8 | 9  | Ace, J, Q, K  |     |      |
      ```````````````````````````````````````````````````````````````````````````````````````````````````````````
     */

    private int suit;
    private int rank;
    private int value;

    // Card coordinates
    private int cardX;
    private int cardY;

    public Card() {
        suit = 0;
        rank = 0;
        value = 0;
    }

    public Card(int suit, int rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    public int getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    public int getValue() {
        return value;
    }

    public void printCard(Graphics2D g2, boolean dealerTurn, boolean faceDown, int cardNumber) throws IOException {
        BufferedImage deckImage = ImageIO.read(new File("resources/images/card-sprite-sheet.jpeg"));
        BufferedImage backOfACard = ImageIO.read(new File("resources/images/facedown.png"));

        int x = 2925;
        int y = 1260;

        // Create a 2D array from our sprite sheet of cards so we don't need 52 images
        BufferedImage[][] cardImages = new BufferedImage[4][13];

        for (int c = 0; c < 4; c++) {
            for (int r = 0; r < 13; r++) {
                cardImages[c][r] = deckImage.getSubimage(r*x/13, c*y/4, x/13, y/4);
            }
        }

        if (dealerTurn) {
            cardY = 80;
        } else {
            cardY = 600;
        }

        cardX = 540 + 80 * cardNumber;

        if (faceDown) {
            g2.drawImage(backOfACard, cardX, cardY, null);
        } else {
            g2.drawImage(cardImages[suit][rank], cardX, cardY, null);
        }
    }

}