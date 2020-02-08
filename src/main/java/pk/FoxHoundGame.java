package pk;

//import java.util.Arrays;
import java.util.Scanner;

/**
 * The Main class of the fox hound program.
 * 
 * It contains the main game loop where main menu interactions are processed and
 * handler functions are called.
 */
public class FoxHoundGame {

    /**
     * This scanner can be used by the program to read from the standard input.
     * 
     * Every scanner should be closed after its use, however, if you do that for
     * StdIn, it will close the underlying input stream as well which makes it
     * difficult to read from StdIn again later during the program.
     * 
     * Therefore, it is advisable to create only one Scanner for StdIn over the
     * course of a program and only close it when the program exits. Additionally,
     * it reduces complexity.
     */
    private static final Scanner STDIN_SCAN = new Scanner(System.in);

    /**
     * Swap between fox and hounds to determine the next figure to move.
     * 
     * @param currentTurn last figure to be moved
     * @return next figure to be moved
     */
    private static char swapPlayers(char currentTurn) {
        if (currentTurn == FoxHoundUtils.FOX_FIELD) {
            return FoxHoundUtils.HOUND_FIELD;
        } else {
            return FoxHoundUtils.FOX_FIELD;
        }
    }

    /**
     * The main loop of the game. Interactions with the main menu are interpreted
     * and executed here.
     * 
     * @param dim     the dimension of the game board
     * @param players current position of all figures on the board in board
     *                coordinates
     */
    private static void gameLoop(int dim, String[] players) {

        // start each game with the Fox
        char turn = FoxHoundUtils.FOX_FIELD;
        boolean exit = false;
        //------------------------------
        String[] testPlayers = { "C6", "D1", "F1", "H1", "B7" };
        players = testPlayers;
        //------------------------------
        while (!exit) {
            System.out.println("\n#################################");
            FoxHoundUI.displayBoard(players, dim);

            int choice = FoxHoundUI.mainMenuQuery(turn, STDIN_SCAN);

            // handle menu choice
            switch (choice) {
            case FoxHoundUI.MENU_MOVE:
                while (true) {
                    String[] step = FoxHoundUI.positionQuery(dim, STDIN_SCAN);
                    String before = step[0];
                    String after = step[1];
                    if (FoxHoundUtils.isValidMove(dim, players, turn, before, after)) {
                        players[FoxHoundUtils.searchKey(players, before)] = after;
                        break;
                    } else {
                        System.out.println("Invalid move");
                    }
                }
                turn = swapPlayers(turn);
                if (FoxHoundUtils.isFoxWin(players[players.length - 1])) {
                    System.out.println("The Fox wins!");
                    exit = true;
                }
                if (FoxHoundUtils.isHoundWin(players, dim)) {
                    System.out.println("The Hound wins!");
                    exit = true;
                }
                break;
            case FoxHoundUI.MENU_EXIT:
                exit = true;
                break;
            default:
                System.err.println("ERROR: invalid menu choice: " + choice);
            }
        }
    }

    /**
     * Entry method for the Fox and Hound game.
     * 
     * The dimensions of the game board can be passed in as optional command line
     * argument.
     * 
     * If no argument is passed, a default dimension of
     * {@value FoxHoundUtils#DEFAULT_DIM} is used.
     * 
     * Dimensions must be between {@value FoxHoundUtils#MIN_DIM} and
     * {@value FoxHoundUtils#MAX_DIM}.
     * 
     * @param args contain the command line arguments where the first can be board
     *             dimensions.
     */
    public static void main(String[] args) {

        int dimension = FoxHoundUtils.DEFAULT_DIM;

        String[] players = FoxHoundUtils.initialisePositions(dimension);
        // String[] players = { "A3", "B2", "C1", "D2", "A1" };

        FoxHoundUI.displayBoard(players, 8);
        gameLoop(dimension, players);

        // System.out.println(FoxHoundUtils.isValidMove(8, players, 'F', "A1", "B2"));
        // System.out.println(FoxHoundUtils.isValidMove(8, players, 'H', "C1", "B2"));
        // System.out.println(FoxHoundUtils.isValidMove(8, players, 'F', "34", "36"));
        // System.out.println(FoxHoundUtils.isValidMove(8, players, 'H', "E8", "D7"));
        // System.out.println(FoxHoundUtils.isValidMove(8, players, 'H', "B2", "A1"));

        // Close the scanner reading the standard input stream
        STDIN_SCAN.close();
    }
}
