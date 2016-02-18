package othello;

import java.io.CharArrayReader;
import java.util.*;
import othello.Utility.*;
import sun.reflect.generics.tree.Tree;

public class Game {
    private int activePlayerTurn = 0;
    private Board board = null;
    private Player players[] = new Player[2];
    private ArrayDeque<Board> logger = null;

    Game(int boardSize, Player players[]) {
        try {
            this.board = new Board(boardSize);
        } catch (NotValidMatrixSize e) {
            System.out.print(e);
            board = new Board();
        }

        for (int i = 0; i < 2; i++) {
            this.players[i] = players[i];
        }

        logger = new ArrayDeque<>();
        logger.push(board.copy());
    }

    Game(int boardSize, Player players[], ArrayDeque<Board> logger, int activePlayerTurn) {
        try {
            this.board = new Board(boardSize);
        } catch (NotValidMatrixSize e) {
            System.out.print(e);
            board = new Board();
        }

        for (int i = 0; i < 2; i++) {
            this.players[i] = players[i];
        }

        this.logger = logger;
        this.activePlayerTurn = activePlayerTurn;
    }

    Board makeUndo() {
        logger.pop();
        return logger.peek();
    }

    void makeCheckpoint() {
        Board temp = board.copy();
        logger.push(temp);
    }

    int getActivePlayerTurn() {
        return activePlayerTurn;
    }

    Player getActivePlayer() {
        return players[activePlayerTurn];
    }

    void turnHasBeenMade() {
        if (activePlayerTurn != Utility.PLAYERONE) {
            activePlayerTurn = Utility.PLAYERONE;
        }
        else {
            activePlayerTurn = Utility.PLAYERTWO;
        }
    }

    ArrayList<Coords> checkPositionForMoves(Coords coords) throws NoMovesAvailableException {
        //System.out.println("Zjistuji pristupne tahy pro: " + coords.getX() + " a " + coords.getY());
        ArrayList<Coords> availableMoves = new ArrayList<>();

        for (int i = coords.getX() - 1; i <= coords.getX() + 1; i++) {
            for (int j = coords.getY() - 1; j <= coords.getY() + 1; j++) {
                if (coords.getX() == i && coords.getY() == j) {
                    continue;
                }

                Coords tempCoords = new Coords(i, j);
//                System.out.println("Zjistuji pro policko pro: " + tempCoords.getX() + " a " + tempCoords.getY());
                if (Utility.isInBoard(tempCoords)) {
//                    System.out.println("Toto policko je v boardu: " + tempCoords.getX() + " a " + tempCoords.getY());
                    try {
                        Color color = board.getField(tempCoords.getX(), tempCoords.getY()).getColor();
//                        System.out.println("Je tam barva: " + tempCoords.getX() + " a " + tempCoords.getY() + " a " + color);
                        if (color != getActivePlayer().getColor()) {
//                            System.out.println("Toto policko obsahuje barvu soupere a je potencionalne zmenitelne: " + tempCoords.getX() + " a " + tempCoords.getY());
//                            System.out.println("Pridavam toto policko do tempCoords: " + tempCoords.getX() + " a " + tempCoords.getY());
                            availableMoves.add(tempCoords);
//                            System.out.println("tempCoords nyni obsahuje : ");
//                            for (Coords tempo: availableMoves) {
//                                System.out.println("Obsahuje prvek: " + tempo.getX() + " a " + tempo.getY());
//                            }
//                            System.out.println("Konec obsahu");
                        }
                    } catch (FieldIsEmptyException e) {
//                        System.out.println("Je prazdne, pokracuji: " + tempCoords.getX() + " a " + tempCoords.getY());
                    }
                }
            }
        }

        if (availableMoves.isEmpty()) {
            throw new NoMovesAvailableException();
        }

        return availableMoves;
    }

    void controlMoveIfValid(Coords coords, ArrayList<TreeMap<Coords, ArrayList<Coords>>> allAvailableMoves) throws MoveNotAvailableException {
        boolean moveFound = false;
        ArrayList<Coords> tempArrayOfCoords = null;

        //System.out.println("Muj tah je: x = " + coords.getX() + " y = " + coords.getY());
        for (TreeMap<Coords, ArrayList<Coords>> map: allAvailableMoves) {
            for (Map.Entry<Coords, ArrayList<Coords>> mapSet: map.entrySet()) {
                //System.out.println("Mozne povolene souradnice jsou x = : " + mapSet.getKey().getX() + " y = " + mapSet.getKey().getY() + " a pro ne budu prebarvovat tyto kameny: ");
                //for (Coords temp: mapSet.getValue()) {
                //    System.out.println("Tento kamen x = : " + temp.getX()  + " y = " + temp.getY());
                //}
                if (coords.equals(mapSet.getKey())) {
                    //   System.out.println("Zadany tah je validni");
                    moveFound = true;
                    tempArrayOfCoords = mapSet.getValue();
                }
            }
        }

        if (!moveFound) {
            throw new MoveNotAvailableException();
        }

        try {
            getBoard().setField(coords, getActivePlayer().getColor());
        } catch (FieldIsNotEmptyException e) {};

        changeFields(tempArrayOfCoords);
    }

    void controlIfComputerTurn(TypeOfGame typeOfGame) throws ComputerHasPlayed {
        if (getActivePlayer().getPlayerType() == PlayerType.COMPUTER) {
            Coords temp = null;
            switch (typeOfGame) {
                case EASY:
                    temp = Algorithm.getEasyAlgorithm(getBoard());
                    break;
                case HARD:
                    temp = Algorithm.getHardAlgorithm(getBoard());
                    break;
            }

            countStones();
            makeCheckpoint();
            turnHasBeenMade();

            throw new ComputerHasPlayed(temp.getX(), temp.getY());
        }
    }

    ArrayList<Field>[] countStones() {
        ArrayList<Field> blackStones = new ArrayList<>();
        ArrayList<Field> whiteStones = new ArrayList<>();

        for (Field field: getBoard().getField()) {
            try {
                if (field.getColor() == Color.BLACK)
                    blackStones.add(field);
                else
                    whiteStones.add(field);
            } catch (FieldIsEmptyException e) {}
        }

        getPlayers()[Utility.PLAYERONE].setScore(blackStones.size());
        getPlayers()[Utility.PLAYERTWO].setScore(whiteStones.size());

        ArrayList<Field>[] temp = new ArrayList[]{blackStones, whiteStones};
        return temp;
    }

    void setBoard(Board board) {
        this.board = board;
    }

    Board getBoard() {
        return board;
    }

    ArrayDeque<Board> getLogger() {
        return logger;
    }

    Player[] getPlayers() {
        return players;
    }

    void changeFields(ArrayList<Coords> listOfCoords) {
        for (Coords temp: listOfCoords) {
            board.changeField(temp);
        }
    }
}
