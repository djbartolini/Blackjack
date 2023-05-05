import javax.swing.*;

public class Main {

    // Frames for main menu & game screen
    public static JFrame menuFrame = new JFrame();
    public static JFrame gameFrame = new JFrame();

    // Number of wins in current session
    private static int playerScore = 0;
    private static int dealerScore = 0;

    // Starting chip total
    public static int chipBalance = 1000;

    // Initialize a Game
    public static Game newGame = new Game(gameFrame);
    private static boolean isFirstGame = true;

    // Game state
    public static enum STATE {
        MENU,
        GAME
    }

    public static STATE currentState = STATE.MENU;

    public static void main(String[] args) throws InterruptedException {
        if (currentState == STATE.MENU) {
            openMenu();
        }
    }

    public static void openMenu() {
        // Set up Frame
        menuFrame.setTitle("Blackjack");
        menuFrame.setSize(1280, 800);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setResizable(false);

        // Add menu component to Frame
        MenuComponent menuComponent = new MenuComponent();
        menuFrame.add(menuComponent);
        menuFrame.setVisible(true);
    }

    // Thread that runs the background images and updates it continuously
    public static Thread gameRefreshThread = new Thread() {
        public void run() {
            while (true) {
                newGame.atmosphereComponent.refresh(chipBalance, playerScore, dealerScore - 1, newGame.faceDown);
            }
        }
    };

    // Thread that checks game status, updates it continuously, and updates Game and its component accordingly
    public static Thread gameCheckThread = new Thread() {
        public void run() {
            while (true) {
                if (isFirstGame || newGame.roundOver) {
                    if (newGame.dealerWon) {
                        dealerScore++;
                        chipBalance -= GameComponent.currentBet;
                    } else {
                        playerScore++;
                        chipBalance += GameComponent.currentBet * 2;
                    }

                    // Initialize new game
                    gameFrame.getContentPane().removeAll();
                    newGame = new Game(gameFrame);
                    newGame.formGame();
                    isFirstGame = false;
                }
            }
        }
    };
}