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
}