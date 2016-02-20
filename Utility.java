/**
 * Třída pro pomocné operace a podporu programu
 * Funkce:  1) Podpora enumů
 *          2) Podpora inline tříd algoritmů a souřadnic
 *          3) Pomocné metody pro prezentaci dat
 *          4) Jednotné výpisy informací
 *          5) Pomocné parsovací metody
 *          6) Generování čísel
 *          7) Nápověda
 * @author Lukáš Dibďák
 * @see othello.Controller
 * @see othello.Game
 * @see othello.SaveLoadManager
 */

package othello;

import java.util.*;

public class Utility {
    static int PLAYERONE = 0;
    static int PLAYERTWO = 1;
    static int MAXFREEZETIME = 60;

    static String[] PLAYERS = new String[2];

    /**
     * Inline třída pro přehlednější prezentaci souřadnic pole
     */
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

    /**
     * Inline třída pro zpracování algoritmů počítače a jeho tahů
     */
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

    /**
     * Enum - Barva kamenů
     */
    public enum Color {
        WHITE('W'),
        BLACK('B'),
        FWHITE('H'),
        FBLACK('L'),
        NONE('0');

        private char key;

        Color(char key) {
            this.key = key;
        }

        char getKey() {
            return key;
        }
    }

    /**
     * Enum - Typ hráče
     */
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

    /**
     * Enum - obtížnost hry
     */
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

    /**
     * Enum - typ instrukce (tokenu)
     */
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

    /**
     * Metoda pro transformaci vodorovné souřadnice desky z char na int
     * @param x Znakový identifikátor vodorovné osy hrací desky
     * @return Číselný identifikátor, který odpovídá znakovému identifikátoru vodorovné osy hrací desky
     */
    static int transformCharToInt(char x) {
        return (int) x - 97;
    }

    /**
     * Metoda pro transformaci vodorovné souřadnice desky z int na char
     * @param x Číselný identifikátor vodorovné osy hrací desky
     * @return Znakový identifikátor, který odpovídá číselnému identifikátoru vodorovné osy hrací desky
     */
    static char transformIntToChar(int x) { return (char) (x + 97); }

    /**
     * Získání znakové interpretace úspěšného uložení hry
     * @return Řetězec znaků
     */
    static String getSuccessfulSaveGameString() { return "Hra byla uspesne ulozena."; }

    /**
     * Získání znakové interpretace úspěšného načtení hry
     * @return Řetězec znaků
     */
    static String getSuccessfulLoadGameString() { return "Hra byla uspesne nactena."; }

    /**
     * Získání znakové interpretace formátu uložených/načtených her
     * @return Řetězec znaků
     */
    static String getFileExtensionString() { return ".txt"; }

    /**
     * Získání znakové interpretace jména složky pro uložení/načtení hry
     * @return Řetězec znaků
     */
    static String getSaveFolderLocationString() { return "save"; }

    /**
     * Získání znakové interpretace úspěšného zmrazení kamenů
     * @param numbers Pole typu {@code Integer}. Kolik kamenů, za jak dlouho, na jak dlouho
     * @return Řetězec znaků
     */
    static String getSuccessfulFreezeStoneString(int[] numbers) {
        return "Za dobu: " + numbers[0] + " sekund bude zmrazen pocet kamenu: " + numbers[2] + " na dobu: " + numbers[1] + " sekund";
    }

    /**
     * Získání znakové interpretace aktuálního tahu hry
     * @param player Identifikace hráče, který je aktuálně na tahu
     * @return Řetězec znaků
     */
    static String getPlayerTurnString(int player) {
        if (player == PLAYERONE) {
            return "Nyni je na rade " + PLAYERS[PLAYERONE] + " s barvou [BLACK]:";
        }
        else {
            return "Nyni je na rade " + PLAYERS[PLAYERTWO] + " s barvou [WHITE]:";
        }
    }

    /**
     * Získání znakové interpretace výsledků hry po jejím ukončení
     * @param players Pole hráčů typu {@code Player}
     * @return Řetězec znaků
     */
    static String getGameEndedString(Player[] players) {
        String playerName;
        int[] score = {
                players[PLAYERONE].getScore(),
                players[PLAYERTWO].getScore()
        };

        if (score[PLAYERONE] > score[PLAYERTWO]) {
            playerName = "Zvitezil " + PLAYERS[PLAYERONE];
        }
        else if (score[PLAYERONE] > score[PLAYERTWO]) {
            playerName = "Zvitezil " + PLAYERS[PLAYERTWO];
        }
        else {
            playerName = "Zapas skoncil remizou";
        }
        return "Hra byla ukoncena.\n" + playerName;
    }

    /**
     * Metoda sloužící k parsování řetězce typu hráče na typ hráče {@code PlayerType}
     * @param temp Znak reprezentující typ hráče
     * @return Typ hráče
     */
    static PlayerType loadParsePlayerType(char temp) {
        return Character.valueOf(temp).equals(PlayerType.COMPUTER.getKey()) ?
                PlayerType.COMPUTER : PlayerType.HUMAN;
    }

    /**
     * Metoda sloužící k parsování řetězce velikosti desky na číslo
     * @param temp Řetězec reprezentující velikost desky
     * @return Velikost desky
     */
    static int loadParseBoardSize(String temp) {
        return Integer.parseUnsignedInt(temp);
    }

    /**
     * Metoda sloužící k parsování řetězce obtížnosti počítače na tento typ
     * @param temp Řetězec reprezentující obtížnost hry
     * @return Obtížnost hry
     */
    static TypeOfGame loadParseTypeOfGame(String temp) {
        return (temp.equals(TypeOfGame.EASY.getDifficulty())) ? TypeOfGame.EASY : TypeOfGame.HARD;
    }

    /**
     * Metoda sloužící k parsování řetězce tahu hráče na číselný typ
     * @param temp Řetězec reprezentující aktuální tah
     * @return Aktuální tah
     */
    static int loadParseActivePlayer(String temp) {
        return Integer.parseUnsignedInt(temp);
    }

    /**
     * Metoda sloužící k parsování řetězců hracích desek na tyto hrací desky
     * @param boardsInString Zásobník řetězců reprezentující hrací desky
     * @param boardSize Velikost desky
     * @return Zásobník hracích desek
     */
    static ArrayDeque<Board> loadParseBoards(ArrayList<String> boardsInString, int boardSize) {
        ArrayDeque<Board> boards = new ArrayDeque<>();

        while (!boardsInString.isEmpty()) {
            String temp = boardsInString.remove(0);
            Board tempBoard = new Board(boardSize, temp);
            boards.addFirst(tempBoard);
        }

        return boards;
    }

    /**
     * Metoda pro generování čísel pro metodu {@code freezeStones}
     * @param randomNumbers Pole odkazů, kam se uloží výsledky
     * @param notFrozenStones Počet kamenů hráče, které ještě nejsou zamrznuty a lze je tedy nechat zamrznout
     */
    static void generateRandomNumbers(int[] randomNumbers, int notFrozenStones) {
        Random random = new Random();

        randomNumbers[0] = random.nextInt(Utility.MAXFREEZETIME);
        randomNumbers[1] = random.nextInt(Utility.MAXFREEZETIME);
        randomNumbers[2] = random.nextInt(notFrozenStones);
    }

    /**
     * Metoda pro vizualizaci hrací desky
     * @param board Hrací deska
     * @return Řetězec znaků odpovídající hodnotám hrací desky
     */
    static String visualizeBoard(Board board) {
        StringBuilder temp = new StringBuilder();
        for (Field field: board.getField()) {
            try {
                switch (field.getColor()) {
                    case WHITE:
                        temp.append(Color.WHITE.getKey());
                        break;
                    case BLACK:
                        temp.append(Color.BLACK.getKey());
                        break;
                    case FWHITE:
                        temp.append(Color.FWHITE.getKey());
                        break;
                    case FBLACK:
                        temp.append(Color.FBLACK.getKey());
                        break;
                }
            } catch (FieldIsEmptyException e) {
                temp.append(Color.NONE.getKey());
            }
        }
        return temp.toString();
    }

    /**
     * Metoda nastavující pomocnou identifikaci jmen hráčů
     * @param computer Je druhý hráč počítač nebo hráč
     */
    static void setPlayerString(boolean computer) {
        PLAYERS[PLAYERONE] = PlayerType.PONE.getName();
        if (computer)
            PLAYERS[PLAYERTWO] = PlayerType.COMP.getName();
        else
            PLAYERS[PLAYERTWO] = PlayerType.PTWO.getName();
    }

    /**
     * Metoda zjišťuje, zdali je pole o zadaných souřadnicích na hrací desce
     * @param coords Souřadnice pole
     * @return Pole leží na desce/pole neleží na desce
     */
    static boolean isInBoard(Coords coords) {
        if (coords.getY() < 0 || coords.getY() >= Board.SIZE) {
            return false;
        }
        else if (coords.getX() < 0 || coords.getX() >= Board.SIZE) {
            return false;
        }
        return true;
    }

    /**
     * Nápověda k programu
     */
    static void help() {
        System.out.print("dake pickoviny");
    }
}
