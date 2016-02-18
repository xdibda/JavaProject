package othello;

import java.util.*;
import othello.Utility.*;
import java.io.*;

class SaveLoadManager {
    File pathToEnviroment = new File(System.getProperty("user.dir"));
    File nameOfFolder = new File(pathToEnviroment + "\\" + Utility.getSaveFolderLocationString());
    File nameOfSave = null;

    void save(String nameOfGame, Player[] players, ArrayDeque<Board> logger, int activePlayer, TypeOfGame typeOfGame) throws GameSavingFailureException {
        char playerTypeChar = 0;
        if (players[Utility.PLAYERTWO].getPlayerType() == PlayerType.HUMAN)
            playerTypeChar = 'H';
        else playerTypeChar = 'C';

        String[] undoMoves = new String[logger.size()];

        int i; Iterator<Board> index;
        StringBuffer temp = new StringBuffer();
        for (index = logger.iterator(), i = 0; index.hasNext(); i++) {
            temp.setLength(0);
            for (Field field: index.next().getField()) {
                try {
                    switch (field.getColor()) {
                        case BLACK:
                            temp.append('B');
                            break;
                        case WHITE:
                            temp.append('W');
                    }
                } catch (FieldIsEmptyException e) {
                    temp.append('N');
                }
            }
            undoMoves[i] = temp.toString();
        }

        nameOfSave = new File(nameOfFolder + "\\" + nameOfGame + Utility.getFileExtensionString());
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

                fout.write(typeOfGame.getDifficulty());
                fout.write(System.lineSeparator());

                fout.write(Integer.toString(players[Utility.PLAYERONE].getScore()));
                fout.write(System.lineSeparator());

                fout.write(Integer.toString(players[Utility.PLAYERTWO].getScore()));
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
            int data = 0; StringBuffer temp = new StringBuffer();
            try {
                while ((data = fin.read()) != -1) {
                    if (Character.isWhitespace(data) && temp.length() != 0) {
                        gameInfo.add(temp.toString());
                        temp.setLength(0);
                    }
                    else temp.append((char) data);
                }
            } catch (IOException e) {
                throw e;
            }
        } catch (FileNotFoundException e) {
            throw new GameLoadingNameNotFoundException();
        } catch (IOException e) {
            throw new GameLoadingFailureException();
        }
        return gameInfo;
    }
}
