package othello;

import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

public class Utility {
    static int PLAYERONE = 0;
    static int PLAYERTWO = 1;

    static int MAXFREEZETIME = 60;

    static String[] PLAYERS = new String[2];

    static void setPlayerString(boolean computer) {
        PLAYERS[PLAYERONE] = PlayerType.PONE.getName();
        if (computer)
            PLAYERS[PLAYERTWO] = PlayerType.COMP.getName();
        else
            PLAYERS[PLAYERTWO] = PlayerType.PTWO.getName();
    }

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

        PONE("[hrac 1]"),
        PTWO("[hrac 2]"),
        COMP("[pocitac]");

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
    static String getSuccessfulFreezeStoneString(int[] numbers) {
        return "Za dobu: " + numbers[0] + " sekund bude zmrazen pocet kamenu: " + numbers[2] + " na dobu: " + numbers[1] + " sekund";
    }

    static String getFileExtensionString() { return ".txt"; }
    static String getSaveFolderLocationString() { return "save"; }

    static String getPlayerTurnString(int player) {
        if (player == PLAYERONE) {
            return "Nyni je na rade " + PLAYERS[PLAYERONE] + " s barvou [BLACK]:";
        }
        else {
            return "Nyni je na rade " + PLAYERS[PLAYERTWO] + " s barvou [WHITE]:";
        }
    }

    static String getGameEndedString(Player[] players) {
        String playerName;
        int[] score = {
                players[PLAYERONE].getScore(),
                players[PLAYERTWO].getScore()
        };

        if (score[PLAYERONE] > score[PLAYERTWO]) {
            playerName = "Zvitezil " + PLAYERS[PLAYERONE];
        } else if (score[PLAYERONE] > score[PLAYERTWO]) {
            playerName = "Zvitezil " + PLAYERS[PLAYERTWO];
        } else {
            playerName = "Zapas skoncil remizou";
        }
        return "Hra byla ukoncena.\n" + playerName;
    }

    static int transformCharToInt(char x) {
        return (int) x - 97;
    }
    static char transformIntToChar(int x) { return (char) (x + 97); }

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

    static PlayerType loadParsePlayerType(char temp) {
        return Character.valueOf(temp).equals(PlayerType.COMPUTER.getKey()) ?
                PlayerType.COMPUTER : PlayerType.HUMAN;
    }

    static int loadParseBoardSize(String temp) {
        return Integer.parseUnsignedInt(temp);
    }

    static TypeOfGame loadParseTypeOfGame(String temp) {
        return (temp.equals(TypeOfGame.EASY.getDifficulty())) ? TypeOfGame.EASY : TypeOfGame.HARD;
    }

    static int loadPraseActivePlayer(String temp) {
        return Integer.parseUnsignedInt(temp);
    }

    static ArrayDeque<Board> loadParseBoards(ArrayList<String> boardsInString, int boardSize) {
        ArrayDeque<Board> boards = new ArrayDeque<>();

        while (!boardsInString.isEmpty()) {
            String temp = boardsInString.remove(0);
            Board tempBoard = new Board(boardSize, temp);
            boards.addFirst(tempBoard);
        }

        return boards;
    }

    static void generateRandomNumbers(int[] randomNumbers, int notFrozenStones) {
        Random random = new Random();

        randomNumbers[0] = random.nextInt(Utility.MAXFREEZETIME);
        randomNumbers[1] = random.nextInt(Utility.MAXFREEZETIME);
        randomNumbers[2] = random.nextInt(notFrozenStones);
    }

    static void help() {
        System.out.print("dake pickoviny");
    }
}
