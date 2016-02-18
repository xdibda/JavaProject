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
        ArrayList<Coords> tempArryOfCoords = null;

        for (TreeMap<Coords, ArrayList<Coords>> map: allAvailableMoves) {
            for (Map.Entry<Coords, ArrayList<Coords>> mapSet: map.entrySet()) {
                if (coords.equals(mapSet.getKey())) {
                    moveFound = true;
                    tempArryOfCoords = mapSet.getValue();
                }
            }
        }

        if (!moveFound) {
            throw new MoveNotAvailableException();
        }

        try {
            getBoard().setField(coords, getActivePlayer().getColor());
        } catch (FieldIsNotEmptyException e) {};

        for (Coords temp: tempArryOfCoords) {
            try {
                getBoard().setField(temp, getActivePlayer().getColor());
            } catch (FieldIsNotEmptyException e) {}
        }
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

    Player getPlayer(int number) {
        return players[number];
    }

    void changeFields(ArrayList<Coords> listOfCoords) {
        for (Coords temp: listOfCoords) {
            board.changeField(temp);
        }
    }
}
