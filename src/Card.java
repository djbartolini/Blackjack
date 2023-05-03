public class Card {
    int value;
    String suit;
    boolean isDiscarded = false;
    String name;
    String symbol;
    int id;

    public Card(int n, String s, int i) {
        this.value = n;
        this.suit = s;
        this.id = i;

        if (value < 11) {
            symbol = Integer.toString(value);
            name = Integer.toString(value);
        } else if (value == 12) {
            symbol = "J";
            name = "Jack";
        } else if (value == 13) {
            symbol = "Q";
            name = "Queen";
        } else if (value == 14) {
            symbol = "K";
            name = "King";
        } else {
            symbol = "A";
            name = "Ace";
        }


    }

}
