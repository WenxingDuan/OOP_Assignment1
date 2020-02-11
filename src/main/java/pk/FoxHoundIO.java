package pk;

import java.io.*;
import java.io.UncheckedIOException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions for all file input / output related operations
 * such as saving and loading a game.
 */
public class FoxHoundIO {

    public static Boolean saveGame(String[] player, char fh, Path path) {
        if (player.length != 5) {
            throw new IllegalArgumentException();
        }
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String[] validKeys = new String[32];
        int index = 0;
        for (int i = 1; i < 8; i = i + 2) {
            for (int j = 0; j < 8; j++) {
                String theString = alphabet.charAt(i) + "" + String.valueOf(j);
                validKeys[index] = theString;
                index++;
            }
        }
        // get all possible positions
        if (fh != 'F' && fh != 'H') {
            return false;
        }
        for (String thePlayer : player) {
            if (FoxHoundUtils.searchKey(player, thePlayer) < 0) {
                return false;
            }
        }
        // check if any is not on the right position
        String txtData = fh + "";
        for (String theString : player) {
            txtData = txtData + " " + theString;
        }
        byte[] bytes = txtData.getBytes();
        if (Files.exists(path)) {
            System.out.println("File exists");
            return false;

        } else {

            try {
                Files.createFile(path);
                Files.write(path, bytes);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        }
        return true;

    }

    // unfinished---------------------------------------------------------------------------
    public static char loadGame(String[] player, Path path) {

        if (Files.exists(path.toAbsolutePath())) {
            try {
                byte[] bytes = Files.readAllBytes(path);
                String text = new String(bytes);
                System.out.println(text);
                player[0] = text.charAt(2) + "" + text.charAt(3);
                player[1] = text.charAt(5) + "" + text.charAt(6);
                player[2] = text.charAt(8) + "" + text.charAt(9);
                player[3] = text.charAt(11) + "" + text.charAt(12);
                player[4] = text.charAt(14) + "" + text.charAt(15);
                return text.charAt(0);

            } catch (IOException e) {
                e.printStackTrace();
                return '#';
            }

        } else {
            //throw new IllegalArgumentException(path.toAbsolutePath().toString());
            return '#';
        }

    }
    // unfinished---------------------------------------------------------------------------

}
