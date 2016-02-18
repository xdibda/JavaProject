package othello;

import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class Utility {
    static int PLAYERONE = 0;
    static int PLAYERTWO = 1;

    public enum Color {
        WHITE(true),
        BLACK(false);

        private boolean isWhite;

        Color(boolean white) {
            this.isWhite = white;
        }
    }

    public enum PlayerType {
        COMPUTER(true, "Pocitac"),
        HUMAN(false, "Hrac");

        private boolean isHuman;
        private String name;

        PlayerType(boolean human, String name) {
            this.isHuman = human;
            this.name = name;
        }

        String getName() {
            return name;
        }
    }

    public enum TypeOfGame {
        EASY(false, "easy"),
        HARD(true, "hard");

        private boolean type;
        private String difficulty;

        TypeOfGame(boolean type, String difficulty) {
            this.type = type;
            this.difficulty = difficulty;
        }

        String getDifficulty() {
            return difficulty;
        }
    }

    public enum TypeOfInstruction {
        MOVE(0, 2),
        SAVE(1),
        LOAD(2),
        NEW(3, 2),
        UNDO(4);

        private int instruction;
        private int numberOfArgumentRequired;

        TypeOfInstruction(int instruction) {
            this.instruction = instruction;
            this.numberOfArgumentRequired = 1;
        }

        TypeOfInstruction(int instruction, int numberOfArgumentsRequired) {
            this.instruction = instruction;
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

        int getX() { return x; }
        int getY() { return y; }
    }

    static String getPlayerTurnString(int player, PlayerType playerType) {
        switch (playerType) {
            case COMPUTER:
                if (player == PLAYERONE) {
                    return "Nyni je na rade [hrac] s barvou [BLACK]:";
                }
                else {
                    return "Nyni je na rade [pocitac] s barvou [WHITE]:";
                }
            case HUMAN:
                if (player == PLAYERONE) {
                    return "Nyni je na rade [hrac 1] s barvou [BLACK]:";
                }
                else {
                    return "Nyni je na rade [hrac 2] s barvou [WHITE]:";
                }
        }
        return null;
    }

    static String getGameEndedString(Player[] players) {
        String playerName;
        String playerOne = "hrac 1", playerTwo = "hrac 2";
        int[] score = {
                players[PLAYERONE].getScore(),
                players[PLAYERTWO].getScore()
        };

        if (score[PLAYERONE] > score[PLAYERTWO]) {
            playerName = "Zvitezil " + playerOne;
        } else if (score[PLAYERONE] > score[PLAYERTWO]) {
            playerName = "Zvitezil " + playerTwo;
        } else {
            playerName = "Zapas skoncil remizou";
        }
        return "Hra byla ukoncena.\n" + playerName + ".\nKonecna skore: " + playerOne + ": " + score[PLAYERONE] + ", " + playerTwo + ": " + score[PLAYERTWO];
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
        static Field getEasyAlgorithm(Board board) {
            Field temp = new Field();
            return temp;
        }
        static Field getHardAlgorithm(Board board) {
            Field temp = new Field();
            return temp;
        }
    }

    static void help() {
        System.out.print("dake pickoviny");
    }
}
