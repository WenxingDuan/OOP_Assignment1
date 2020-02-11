package pk;

import java.util.Scanner;
import java.nio.file.Path;

public class FoxHoundGame {

    private static final Scanner STDIN_SCAN = new Scanner(System.in);

    private static char swapPlayers(char currentTurn) {
        if (currentTurn == FoxHoundUtils.FOX_FIELD) {
            return FoxHoundUtils.HOUND_FIELD;
        } else {
            return FoxHoundUtils.FOX_FIELD;
        }
    }

    private static void gameLoop(int dim, String[] players) {

        char turn = FoxHoundUtils.FOX_FIELD;
        boolean exit = false;

        while (!exit) {
            System.out.println("\n#################################");
            FoxHoundUI.displayBoard(players, dim);
            // Print the board

            int choice = FoxHoundUI.mainMenuQuery(turn, STDIN_SCAN);

            switch (choice) {
            case FoxHoundUI.MENU_MOVE:
                while (true) {
                    String[] step = FoxHoundUI.positionQuery(dim, STDIN_SCAN);
                    // Get the input step
                    String before = step[0];
                    String after = step[1];

                    if (FoxHoundUtils.isValidMove(dim, players, turn, before, after)) {
                        players[FoxHoundUtils.searchKey(players, before)] = after;
                        // Change original player to new one
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
                // Check if any players win
                break;
            case FoxHoundUI.MENU_EXIT:
                exit = true;
                break;

            case FoxHoundUI.MENU_SAVE:
                Path savePath = FoxHoundUI.fileQuery(STDIN_SCAN);
                if (FoxHoundIO.saveGame(players, swapPlayers(turn), savePath)) {
                    System.out.println("Save successful");
                } else {
                    System.out.println("Save failed");
                }
                break;

            case FoxHoundUI.MENU_LOAD:
                Path loadPath = FoxHoundUI.fileQuery(STDIN_SCAN);
                if (FoxHoundIO.loadGame(players, loadPath) != '#') {
                    turn = FoxHoundIO.loadGame(players, loadPath);
                    //Change players and turn from loading file
                    System.out.println("Load successful");
                } else {
                    System.out.println("Load failed");
                }
                break;
            default:
                System.err.println("ERROR: invalid menu choice: " + choice);
                //Choice is not valid
            }
        }
    }

    public static void main(String[] args) {
        int dimension = FoxHoundUtils.DEFAULT_DIM;
        //Default dimension is 8
        String[] players = FoxHoundUtils.initialisePositions(dimension);
        //Initialise player
        gameLoop(dimension, players);
        STDIN_SCAN.close();
    }
}
