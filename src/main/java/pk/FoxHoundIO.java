package pk;

import java.io.IOException;
import java.nio.file.Path;
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
        String[] validKeys = { "B1", "D1", "F1", "H1", "A2", "C2", "E2", "G2", "B3", "D3", "F3", "H3", "A4", "C4", "E4",
                "G4", "B5", "D5", "F5", "H5", "A6", "C6", "E6", "G6", "B7", "D7", "F7", "H7", "A8", "C8", "E8", "G8", };

        // get all possible positions
        if (fh != 'F' && fh != 'H') {
            return false;
        }
        for (String thePlayer : player) {
            if (FoxHoundUtils.searchKey(validKeys, thePlayer) < 0) {
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
                return false;
            }

        }
        return true;

    }

    // unfinished---------------------------------------------------------------------------
    public static char loadGame(String[] player, Path path) {

        String[] validKeys = { "B1", "D1", "F1", "H1", "A2", "C2", "E2", "G2", "B3", "D3", "F3", "H3", "A4", "C4", "E4",
                "G4", "B5", "D5", "F5", "H5", "A6", "C6", "E6", "G6", "B7", "D7", "F7", "H7", "A8", "C8", "E8", "G8", };

        if (Files.exists(path.toAbsolutePath())) {
            try {
                byte[] bytes = Files.readAllBytes(path);
                String text = new String(bytes);
                String[] newPlayer = text.split(" ");
                if ((text.charAt(0) != 'F') && (text.charAt(0) != 'H')) {
                    return '#';
                }
                if (player.length != 5){
                    throw new IllegalArgumentException();
                }
                if ((newPlayer.length != 6) ) {

                    return '#';
                }
                // check the wrong dim
                for (int i = 1; i < newPlayer.length; i++) {
                    if ((FoxHoundUtils.searchKey(validKeys, newPlayer[i]) < 0)) {
                        return '#';
                    }
                }
                for(String theString: player){
                    if (FoxHoundUtils.searchKey(validKeys, theString)<0){
                        throw new IllegalArgumentException();
                    }
                }
                // check each key is on the right possible position
                for (int i = 1; i < newPlayer.length; i++) {
                    player[i - 1] = newPlayer[i];
                }
                return text.charAt(0);

            } catch (IOException e) {
                // e.printStackTrace();
                return '#';
            }

        } else {
            // throw new IllegalArgumentException(path.toAbsolutePath().toString());
            return '#';
        }

    }
    // unfinished---------------------------------------------------------------------------

}
