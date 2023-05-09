import javax.swing.*;
import java.util.ArrayList;

public class Game {

    ArrayList<Card> dealerHand;
    ArrayList<Card> playerHand;

    public boolean faceDown;
    public boolean dealerWon;

    public volatile boolean roundOver;

    JFrame frame;


}