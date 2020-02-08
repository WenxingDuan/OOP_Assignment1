/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions to check the state of the game
 * board and validate board coordinates and figure positions.
 */
package pk;

import java.util.Arrays;

public class FoxHoundUtils {

    // ATTENTION: Changing the following given constants can
    // negatively affect the outcome of the auto grading!

    /** Default dimension of the game board in case none is specified. */
    public static final int DEFAULT_DIM = 8;
    /** Minimum possible dimension of the game board. */
    public static final int MIN_DIM = 4;
    /** Maximum possible dimension of the game board. */
    public static final int MAX_DIM = 26;

    /** Symbol to represent a hound figure. */
    public static final char HOUND_FIELD = 'H';
    /** Symbol to represent the fox figure. */
    public static final char FOX_FIELD = 'F';

    // HINT Write your own constants here to improve code readability ...

    public static String[] initialisePositions(int dimension) {

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (dimension < 0) {
            throw new IllegalArgumentException("");
        }
        int length = (dimension / 2) + 1;
        // System.out.println(length);

        String[] positions = new String[length];
        int listPosition = 0;
        for (int i = 1; i < dimension; i = i + 2) {
            positions[listPosition] = alphabet.charAt(i) + "1";
            listPosition++;
            // System.out.println(positions[listPosition]);
            // System.out.println(listPosition);
        }
        positions[listPosition] = alphabet.charAt(length - 1) + "" + dimension;

        return positions;
    }

    public static boolean isValidMove(int dim, String[] players, char fh, String before, String after) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        String[] allKeys = new String[dim * dim];
        int allKeysPos = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 1; j < dim + 1; j++) {
                allKeys[allKeysPos] = alphabet.charAt(i) + String.valueOf(j);
                allKeysPos++;
            }
        }

        if (fh == 'H') {
            if (((searchKey(players, before)) > -1) && !before.equals(players[players.length - 1])) {
                return checkMoves(before, after) && (searchKey(allKeys, after) > -1)
                        && !(searchKeyNum(players, after) == 1)
                        && (Integer.parseInt(String.valueOf(before.charAt(1))) < Integer
                                .parseInt(String.valueOf(after.charAt(1))));

            } else {
                return false;
            }
        }

        else if (fh == 'F') {
            if (before.equals(players[players.length - 1])) {
                return checkMoves(before, after) && (searchKey(allKeys, after) > -1)
                        && (searchKey(Arrays.copyOf(players, players.length - 1), after) < 0)
                        && !(searchKeyNum(players, after) == 1);

            } else {
                // System.out.println("listPosition");
                return false;
            }
        } else {
            return false;
        }
    }

    private static boolean checkMoves(String before, String after) {
        if ((before.charAt(0) + 1 == after.charAt(0)) || (before.charAt(0) - 1 == after.charAt(0))) {
            if ((Integer.parseInt(String.valueOf(before.charAt(1))) + 1 == Integer
                    .parseInt(String.valueOf(after.charAt(1))))
                    || (Integer.parseInt(String.valueOf(before.charAt(1))) - 1 == Integer
                            .parseInt(String.valueOf(after.charAt(1))))) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isHoundWin(String[] players, int dim) {
        if (dim < 0) {
            throw new IllegalArgumentException("");
        }
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        String[] allKeys = new String[dim * dim];
        int allKeysPos = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 1; j < dim + 1; j++) {
                allKeys[allKeysPos] = alphabet.charAt(i) + String.valueOf(j);
                allKeysPos++;
            }
        }
        String[] rightMoves = new String[4];
        int index = 0;
        for (String key : allKeys) {

            if (checkMoves(players[players.length - 1], key)) {
                rightMoves[index] = key;
                index++;
            }

        }
        boolean flag = false;
        for (String key : rightMoves) {
            flag = flag || isValidMove(dim, players, 'F', players[players.length - 1], key);

        }
        return !flag;
    }

    public static boolean isFoxWin(String foxPosition) {
        return foxPosition.charAt(1) == '1';
    }

    public static int searchKeyNum(String[] keys, String key) {
        int flag = 0;
        for (String theKey : keys) {
            if (theKey.equals(key)) {
                flag++;
            }
        }
        return flag;
    }

    public static int searchKey(String[] keys, String key) {
        int flag = 0;
        for (String theKey : keys) {
            if (theKey.equals(key)) {
                return flag;
            }
            flag++;
        }
        return -1;
    }
}
