package pk;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

public class FoxHoundIO {

    public static Boolean saveGame(String[] player, char fh, Path path) {
        if (player.length != 5) {
            throw new IllegalArgumentException();

        }
        String[] validKeys = { "B1", "D1", "F1", "H1", "A2", "C2", "E2", "G2", "B3", "D3", "F3", "H3", "A4", "C4", "E4",
                "G4", "B5", "D5", "F5", "H5", "A6", "C6", "E6", "G6", "B7", "D7", "F7", "H7", "A8", "C8", "E8", "G8", };
        // all valid keys in dimension 8 keyboard

        if (fh != 'F' && fh != 'H') {
            return false;
            // if the turn key is invalid
        }
        for (String thePlayer : player) {
            if (FoxHoundUtils.searchKey(validKeys, thePlayer) < 0) {
                return false;
                // if the player key is not on the right position
            }
        }
        String txtData = fh + "";
        for (String theString : player) {
            txtData = txtData + " " + theString;
        }
        byte[] bytes = txtData.getBytes();
        // change String into bytes
        if (Files.exists(path)) {
            System.out.println("File exists");
            return false;
            // check if file exists
        } else {
            try {
                Files.createFile(path);
                Files.write(path, bytes);
                // write the file
            } catch (IOException e) {
                return false;
            }

        }
        return true;

    }

    public static char loadGame(String[] player, Path path) {

        String[] validKeys = { "B1", "D1", "F1", "H1", "A2", "C2", "E2", "G2", "B3", "D3", "F3", "H3", "A4", "C4", "E4",
                "G4", "B5", "D5", "F5", "H5", "A6", "C6", "E6", "G6", "B7", "D7", "F7", "H7", "A8", "C8", "E8", "G8", };
        // all valid keys in dimension 8 keyboard

        if (Files.exists(path.toAbsolutePath())) {
            try {
                byte[] bytes = Files.readAllBytes(path);
                // read the path file into byte
                String text = new String(bytes);
                // change into String
                String[] newPlayer = text.split(" ");
                // change into String[]
                if ((text.charAt(0) != 'F') && (text.charAt(0) != 'H')) {
                    // if the turn key is invalid
                    return '#';
                }
                if (player.length != 5) {
                    // if the player dimension is wrong
                    throw new IllegalArgumentException();
                }
                if ((newPlayer.length != 6)) {
                    // if the file dimension is wrong
                    return '#';
                }
                for (int i = 1; i < newPlayer.length; i++) {
                    if ((FoxHoundUtils.searchKey(validKeys, newPlayer[i]) < 0)) {
                        // if the file player position is wrong
                        return '#';
                    }
                }
                for (String theString : player) {
                    if (FoxHoundUtils.searchKey(validKeys, theString) < 0) {
                        // if the player position is wrong
                        throw new IllegalArgumentException();
                    }
                }
                for (int i = 1; i < newPlayer.length; i++) {
                    player[i - 1] = newPlayer[i];
                    //everything is fine, change the player
                }
                return text.charAt(0);

            } catch (IOException e) {
                return '#';
            }
        } else {
            return '#';
        }

    }
}
