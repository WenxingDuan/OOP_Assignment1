package pk;

import java.util.Scanner;
import java.util.Objects;
import java.util.Arrays;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FoxHoundUI {

    /** Number of main menu entries. */
    private static final int MENU_ENTRIES = 4;
    /** Main menu display string. */
    private static final String MAIN_MENU = "\n1. Move\n2. Save\n3. Load\n4. Exit\n\nEnter 1 - 4:";

    /** Menu entry to select a move action. */
    public static final int MENU_MOVE = 1;
    /** Menu entry to terminate the program. */

    public static final int MENU_SAVE = 2;
    public static final int MENU_LOAD = 3;
    public static final int MENU_EXIT = 4;

    public static void displayBoard(String[] players, int dim) {
        dim = dim + 2;
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // get all possible keyboards
        String[] keyboard = new String[dim];
        // generate the keyboard with given dimension
        keyboard[0] = "  " + alphabet.substring(0, dim - 2) + "  " + "\n";
        // first line of the keyboard
        keyboard[dim - 1] = "\n  " + alphabet.substring(0, dim - 2) + "  ";
        // last line of the keyboard
        for (int i = 1; i < dim - 1; i++) {
            keyboard[i] = new String();
            for (int j = 0; j < dim - 2; j++) {
                keyboard[i] = keyboard[i] + ".";
            }
        }
        // write "." on all keyboard

        for (int i = 0; i < players.length; i++) {

            String theString = players[i];
            int column = alphabet.indexOf(String.valueOf(theString.charAt(0)));
            int row = Integer.parseInt(String.valueOf(theString.charAt(1)));
            StringBuilder theNewString = new StringBuilder(keyboard[row]);
            if (i != players.length - 1) {
                keyboard[row] = theNewString.replace(column, column + 1, "H").toString();
            } else {
                keyboard[row] = theNewString.replace(column, column + 1, "F").toString();
            }
            // write F and H on the keyboard
        }

        for (int i = 0; i < keyboard.length; i++) {
            if (i == 0 || i == keyboard.length - 1) {
                System.out.println(keyboard[i]);
            } else {
                System.out.print(i + " ");
                System.out.print(keyboard[i]);
                System.out.println(" " + i);
            }
            // print the keyboard
        }
    }

    public static int mainMenuQuery(char figureToMove, Scanner stdin) {
        Objects.requireNonNull(stdin, "Given Scanner must not be null");
        if (figureToMove != FoxHoundUtils.FOX_FIELD && figureToMove != FoxHoundUtils.HOUND_FIELD) {
            throw new IllegalArgumentException("Given figure field invalid: " + figureToMove);
        }

        String nextFigure = figureToMove == FoxHoundUtils.FOX_FIELD ? "Fox" : "Hounds";

        int input = -1;
        while (input == -1) {
            System.out.println(nextFigure + " to move");
            System.out.println(MAIN_MENU);

            boolean validInput = false;
            if (stdin.hasNextInt()) {
                input = stdin.nextInt();
                validInput = input > 0 && input <= MENU_ENTRIES;
            }

            if (!validInput) {
                System.out.println("Please enter valid number.");
                input = -1; // reset input variable
            }

            stdin.nextLine(); // throw away the rest of the line
        }

        return input;
    }

    public static String[] positionQuery(int dim, Scanner stdin) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        String[] allKeys = new String[dim * dim];
        int allKeysPos = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 1; j < dim + 1; j++) {
                allKeys[allKeysPos] = alphabet.charAt(i) + String.valueOf(j);
                allKeysPos++;
            }
        }
        // get all possible keyboard positions

        while (true) {
            System.out.println("Provide origin and destination coordinates.");
            System.out.println("Enter two positions between " + allKeys[0] + "-" + allKeys[allKeys.length - 1] + ":\n");
            // write the range of moves
            String key = stdin.nextLine() + "thisIsForErrorHanding";
            // in case user input nothing
            String before = key.substring(0, 2);
            String after = key.substring(3, 5);
            if ((Arrays.binarySearch(allKeys, before) > -1) && (Arrays.binarySearch(allKeys, after) > -1)) {
                String[] returnValues = new String[2];
                returnValues[0] = before;
                returnValues[1] = after;
                return returnValues;
            } else {
                System.err.println("ERROR: Please enter valid coordinate pair separated by space.");
                // the input key is not on the keyboard
            }
        }

    }

    public static Path fileQuery(Scanner stdin) {
        System.out.println("Enter file path:");
        String path = stdin.nextLine();
        Path thePath = Paths.get(path);
        // change the string to path
        return thePath;

    }
}
