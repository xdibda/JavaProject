package othello;

import java.util.*;
import othello.Utility.*;
import java.io.*;

class SaveLoadManager {
    File pathToEnviroment = new File(System.getProperty("user.dir"));
    File nameOfFolder = new File(pathToEnviroment + "/" + Utility.getSaveFolderLocationString());
    File nameOfSave = null;

    void save(String nameOfGame, Player[] players, ArrayDeque<Board> logger, int activePlayer, TypeOfGame typeOfGame) throws GameSavingFailureException {
        char playerTypeChar; String typeOfGameString = "null";
        if (players[Utility.PLAYERTWO].getPlayerType() == PlayerType.HUMAN)
            playerTypeChar = PlayerType.HUMAN.getKey();
        else  {
            playerTypeChar = PlayerType.COMPUTER.getKey();
            typeOfGameString = typeOfGame.getDifficulty();
        }

        String[] undoMoves = new String[logger.size()];

        int i; Iterator<Board> index;
        StringBuilder temp = new StringBuilder();
        for (index = logger.iterator(), i = 0; index.hasNext(); i++) {
            temp.setLength(0);
            for (Field field: index.next().getField()) {
                try {
                    switch (field.getColor()) {
                        case BLACK:
                            temp.append(Color.BLACK.getKey());
                            break;
                        case WHITE:
                            temp.append(Color.WHITE.getKey());
                    }
                } catch (FieldIsEmptyException e) {
                    temp.append(Color.NONE.getKey());
                }
            }
            undoMoves[i] = temp.toString();
        }

        nameOfSave = new File(nameOfFolder + "/" + nameOfGame + Utility.getFileExtensionString());
        if (!nameOfFolder.exists()) {
            nameOfFolder.mkdir();
        }
        try {
            nameOfSave.createNewFile();
            try (FileWriter fout = new FileWriter(nameOfSave)) {
                fout.write(playerTypeChar);
                fout.write(System.lineSeparator());

                fout.write(Integer.toString(Board.SIZE));
                fout.write(System.lineSeparator());

                fout.write(typeOfGameString);
                fout.write(System.lineSeparator());

                fout.write(Integer.toString(activePlayer));
                fout.write(System.lineSeparator());

                for (String moveRecord: undoMoves) {
                    fout.write(moveRecord);
                    fout.write(System.lineSeparator());
                }

                fout.flush();
                fout.close();
            } catch (FileNotFoundException e) {
                throw new GameSavingFailureException();
            }
        } catch (IOException e) {
            throw new GameSavingFailureException();
        }
    }

    ArrayList<String> load(String nameOfGame) throws GameLoadingNameNotFoundException, GameLoadingFailureException {
        ArrayList<String> gameInfo = new ArrayList<>();

        try (FileReader fin = new FileReader(nameOfFolder + "\\" + nameOfGame + Utility.getFileExtensionString());) {
            try {
                int data; StringBuilder temp = new StringBuilder();
                while ((data = fin.read()) != -1) {
                    if (Character.isWhitespace(data) && temp.length() != 0) {
                        gameInfo.add(temp.toString());
                        temp.setLength(0);
                    }
                    else temp.append((char) data);
                }
            } catch (IOException e) {
                throw new GameLoadingFailureException();
            }
        } catch (FileNotFoundException e) {
            throw new GameLoadingNameNotFoundException();
        } catch (IOException e) {
            throw new GameLoadingFailureException();
        }
        return gameInfo;
    }
}
