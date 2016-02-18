package othello;

import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class Utility {
    static int PLAYERONE = 0;
    static int PLAYERTWO = 1;

    static int MAXFREEZETIME = 100;

    public enum Color {
        WHITE('W'),
        BLACK('B'),
        NONE('N');

        private char key;

        Color(char key) {
            this.key = key;
        }

        char getKey() {
            return key;
        }
    }

    public enum PlayerType {
        COMPUTER('C'),
        HUMAN('H'),

        PONE("hrac 1"),
        PTWO("hrac 2"),
        COMP("pocitac");

        private char key = 0;
        private String name = null;

        PlayerType(char c) {
            this.key = c;
        }

        PlayerType(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        char getKey() {
            return key;
        }
    }

    public enum TypeOfGame {
        EASY("easy"),
        HARD("hard");

        private String difficulty;

        TypeOfGame(String difficulty) {
            this.difficulty = difficulty;
        }

        String getDifficulty() {
            return difficulty;
        }
    }

    public enum TypeOfInstruction {
        MOVE(2),
        SAVE(1),
        LOAD(1),
        NEW(2),
        UNDO(),
        FREEZE();

        private int numberOfArgumentRequired;

        TypeOfInstruction() {
            this.numberOfArgumentRequired = 0;
        }

        TypeOfInstruction(int numberOfArgumentsRequired) {
            this.numberOfArgumentRequired = numberOfArgumentsRequired;
        }

        int getNumberOfArgumentRequired() {
            return numberOfArgumentRequired;
        }
    }

    static boolean isInBoard(Coords coords) {
        if (coords.getY() < 0 || coords.getY() >= Board.SIZE) {
            return false;
        }
        else if (coords.getX() < 0 || coords.getX() >= Board.SIZE) {
            return false;
        }
        return true;
    }

    static class Coords implements Comparable<Coords> {;
        private int x, y;

        Coords(char x, int y) {
            this.x = Utility.transformCharToInt(x);
            this.y = y - 1;
        }

        Coords(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Coords o) {
            if (this.getX() < o.getX())
                return -1;
            else if (this.getX() == o.getX())
                return 0;
            else
                return 1;
        }

        public boolean equals(Coords temp) {
            return (temp.getX() == this.getX() && temp.getY() == this.getY());
        }

        int getX() { return x; }
        int getY() { return y; }
    }

    static String getSuccessfulSaveGameString() { return "Hra byla uspesne ulozena."; }
    static String getSuccessfulLoadGameString() { return "Hra byla uspesne nactena."; }

    static String getFileExtensionString() { return ".txt"; }
    static String getSaveFolderLocationString() { return "save"; }

    static String getPlayerTurnString(int player, PlayerType playerType) {
        switch (playerType) {
            case COMPUTER:
                if (player == PLAYERONE) {
                    return "Nyni je na rade " + playerType.PONE.getName() + " s barvou [BLACK]:";
                }
                else {
                    return "Nyni je na rade " + playerType.COMP.getName() + " s barvou [WHITE]:";
                }
            case HUMAN:
                if (player == PLAYERONE) {
                    return "Nyni je na rade " + playerType.PONE.getName() + " s barvou [BLACK]:";
                }
                else {
                    return "Nyni je na rade " + playerType.PONE.getName() + " s barvou [WHITE]:";
                }
        }
        return null;
    }

    static String getGameEndedString(Player[] players) {
        String playerName;
        int[] score = {
                players[PLAYERONE].getScore(),
                players[PLAYERTWO].getScore()
        };

        switch (players[PLAYERTWO].getPlayerType()) {
            case COMPUTER:
                if (score[PLAYERONE] > score[PLAYERTWO]) {
                    playerName = "Zvitezil " + PlayerType.PONE.getName();
                } else if (score[PLAYERONE] > score[PLAYERTWO]) {
                    playerName = "Zvitezil " + PlayerType.COMP.getName();
                } else {
                    playerName = "Zapas skoncil remizou";
                }
                return "Hra byla ukoncena.\n" + playerName + ".\nKonecna skore: " + PlayerType.PONE.getName() + ": " + score[PLAYERONE] + ", " + PlayerType.PTWO.getName() + ": " + score[PLAYERTWO];
            case HUMAN:
                if (score[PLAYERONE] > score[PLAYERTWO]) {
                    playerName = "Zvitezil " + PlayerType.PONE.getName();
                } else if (score[PLAYERONE] > score[PLAYERTWO]) {
                    playerName = "Zvitezil " + PlayerType.PTWO.getName();
                } else {
                    playerName = "Zapas skoncil remizou";
                }
                return "Hra byla ukoncena.\n" + playerName + ".\nKonecna skore: " + PlayerType.PONE.getName() + ": " + score[PLAYERONE] + ", " + PlayerType.COMP.getName() + ": " + score[PLAYERTWO];
        }
        return null;
    }

    static int transformCharToInt(char x) {
        return (int) x - 97;
    }
    static char transformIntToChar(int x) { return (char) (x + 97); }

    static ArrayDeque<Board> parseBoards(ArrayList<String> boardsInString, int boardSize) {
        ArrayDeque<Board> boards = new ArrayDeque<>();

        while (!boardsInString.isEmpty()) {
            String temp = boardsInString.remove(0);
            Board tempBoard = new Board(boardSize, temp);
            boards.addLast(tempBoard);
        }

        return boards;
    }

    static class Algorithm {
        static Coords getEasyAlgorithm(Board board) {
            Coords temp = new Coords(1, 1);
            return temp;
        }
        static Coords getHardAlgorithm(Board board) {
            Coords temp = new Coords(1, 1);
            return temp;
        }
    }

    static void help() {
        System.out.print("dake pickoviny");
    }
}
