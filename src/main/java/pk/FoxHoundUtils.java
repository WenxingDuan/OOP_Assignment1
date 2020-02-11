package pk;

import java.util.Arrays;

public class FoxHoundUtils {

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

    public static String[] initialisePositions(int dimension) {

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // Used to check every part is valid
        if (dimension < 0) {
            throw new IllegalArgumentException("");
        }
        // check negative input

        int length = (dimension / 2) + 1;
        // length of players

        String[] positions = new String[length];
        int listPosition = 0;
        for (int i = 1; i < dimension; i = i + 2) {
            positions[listPosition] = alphabet.charAt(i) + "1";
            listPosition++;
        }
        // put the hound player into the array
        positions[listPosition] = alphabet.charAt(length - 1) + "" + dimension;
        // put the fox player into the array
        return positions;
    }

    public static boolean isValidMove(int dim, String[] players, char fh, String before, String after) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // Used to check every part is valid
        String[] allKeys = new String[dim * dim];
        int allKeysPos = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 1; j < dim + 1; j++) {
                allKeys[allKeysPos] = alphabet.charAt(i) + String.valueOf(j);
                allKeysPos++;
            }
        }
        // generate all possible valid move
        if (fh == 'H') {
            if (((searchKey(players, before)) > -1) && !before.equals(players[players.length - 1])) {
                return checkMoves(before, after) && (searchKey(allKeys, after) > -1)
                        && !(searchKeyNum(players, after) == 1)
                        && (Integer.parseInt(String.valueOf(before.charAt(1))) < Integer
                                .parseInt(String.valueOf(after.charAt(1))));
                /*
                 * the move must be 1. before and after are connected 2. the key is on the
                 * keyboard 3. hound cannot move onto another hound 4. hound cannot go backwards
                 */

            } else {
                return false;
            }
        }

        else if (fh == 'F') {
            if (before.equals(players[players.length - 1])) {
                return checkMoves(before, after) && (searchKey(allKeys, after) > -1)
                        && (searchKey(Arrays.copyOf(players, players.length - 1), after) < 0)
                        && !(searchKeyNum(players, after) == 1);
                /*
                 * the move must be 1. before and after are connected 2. the key is on the
                 * keyboard 3. fox cannot move onto another hound
                 */

            } else {
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

        // The helper function to check if the two input string are connected in the
        // right way
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
        // generate all possible moves
        String[] rightMoves = new String[4];
        int index = 0;
        for (String key : allKeys) {
            if (checkMoves(players[players.length - 1], key)) {
                rightMoves[index] = key;
                index++;
            }
        }
        // get all possible moves for the certain player.
        // is the keyboard surrounding it

        for (int i = 0; i < rightMoves.length; i++) {
            if (rightMoves[i] == null) {
                rightMoves[i] = "00";
            }
        }
        // put the 00 instead of null
        // for error handling

        boolean flag = false;
        for (String key : rightMoves) {
            flag = flag || isValidMove(dim, players, 'F', players[players.length - 1], key);

        }

        // if fox is completely surrounded by hounds
        // fox cannot go anywhere
        return !flag;
    }

    public static boolean isFoxWin(String foxPosition) {
        return foxPosition.charAt(1) == '1';
        // if the fox goes to the ending line
    }

    public static int searchKeyNum(String[] keys, String key) {
        int flag = 0;
        for (String theKey : keys) {
            if (theKey.equals(key)) {
                flag++;
            }
        }
        return flag;
        // helper function to find how many same keys exist, return number of same keys
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
        // helper function to find the position of same keys exist, return first same
        // keys's position

    }
}
