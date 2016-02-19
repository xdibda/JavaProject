package othello;

import java.util.*;
import othello.Utility.*;

public class Game {
    private int activePlayerTurn = 0;
    private Board board = null;
    private Player players[] = new Player[2];
    private ArrayDeque<Board> logger = null;

    Game(int boardSize, Player players[]) {
        this.board = new Board(boardSize);

        for (int i = 0; i < 2; i++) {
            this.players[i] = players[i];
        }

        logger = new ArrayDeque<>();
        logger.push(board.copy());

        countStones();

        Utility.setPlayerString(players[Utility.PLAYERTWO].getPlayerType() == PlayerType.COMPUTER);
    }

    Game(int boardSize, Player players[], ArrayDeque<Board> logger, int activePlayerTurn) {
        board = new Board(boardSize);

        for (int i = 0; i < 2; i++) {
            this.players[i] = players[i];
        }

        this.logger = new ArrayDeque<>();
        while (!logger.isEmpty()) {
            this.logger.push(logger.removeFirst());
        }
        setBoard(this.logger.peek());

        this.activePlayerTurn = activePlayerTurn;

        countStones();

        Utility.setPlayerString(players[Utility.PLAYERTWO].getPlayerType() == PlayerType.COMPUTER);
    }

    Board makeUndo() throws NoMoreMovesToUndoException {
        if (logger.size() < 2) {
            throw new NoMoreMovesToUndoException();
        }
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
        ArrayList<Coords> availableMoves = new ArrayList<>();

        for (int i = coords.getX() - 1; i <= coords.getX() + 1; i++) {
            for (int j = coords.getY() - 1; j <= coords.getY() + 1; j++) {
                if (coords.getX() == i && coords.getY() == j) {
                    continue;
                }

                Coords tempCoords = new Coords(i, j);
                if (Utility.isInBoard(tempCoords)) {
                    try {
                        Color color = board.getField(tempCoords.getX(), tempCoords.getY()).getColor();
                        if (color != getActivePlayer().getColor()) {
                            availableMoves.add(tempCoords);
                        }
                    } catch (FieldIsEmptyException e) {}
                }
            }
        }

        if (availableMoves.isEmpty()) {
            throw new NoMovesAvailableException();
        }

        return availableMoves;
    }

    void setFinalScore() {
        countStones();

        int playerOne = getPlayers()[Utility.PLAYERONE].getScore();
        int playerTwo = getPlayers()[Utility.PLAYERTWO].getScore();

        if ((playerOne + playerTwo) != (Board.SIZE * Board.SIZE)) {
            if (playerOne > playerTwo) {
                getPlayers()[Utility.PLAYERONE].setScore(Board.SIZE * Board.SIZE - playerTwo);
            }
            else if (playerOne < playerTwo) {
                getPlayers()[Utility.PLAYERTWO].setScore(Board.SIZE * Board.SIZE - playerOne);
            }
            else {
                getPlayers()[Utility.PLAYERONE].setScore((Board.SIZE * Board.SIZE) / 2);
                getPlayers()[Utility.PLAYERTWO].setScore((Board.SIZE * Board.SIZE) / 2);
            }
        }
    }

    void controlMoveIfValid(Coords coords, ArrayList<TreeMap<Coords, ArrayList<Coords>>> allAvailableMoves) throws MoveNotAvailableException {
        boolean moveFound = false;
        ArrayList<ArrayList<Coords>> tempArrayOfCoords = new ArrayList<>();

        try {
            getBoard().setField(coords, getActivePlayer().getColor());
        } catch (FieldIsNotEmptyException e) {
            throw new MoveNotAvailableException();
        };

        for (TreeMap<Coords, ArrayList<Coords>> map: allAvailableMoves) {
            for (Map.Entry<Coords, ArrayList<Coords>> mapSet: map.entrySet()) {
                if (coords.equals(mapSet.getKey())) {
                    moveFound = true;
                    tempArrayOfCoords.add(mapSet.getValue());
                }
            }
        }

        if (!moveFound) {
            throw new MoveNotAvailableException();
        }

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

        return new ArrayList[] {blackStones, whiteStones};
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

    void changeFields(ArrayList<ArrayList<Coords>> listOfCoords) {
        for (ArrayList<Coords> tempCoords: listOfCoords) {
            for (Coords temp: tempCoords) {
                board.changeField(temp);
            }
        }
    }
}
