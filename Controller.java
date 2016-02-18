package othello;

import othello.Utility.*;
import sun.reflect.generics.tree.Tree;

import java.util.*;

public class Controller {
    private Game game;
    private SaveLoadManager saveLoadManager;

    private PlayerType playerType = null;
    private TypeOfGame typeOfGame = null;
    private int boardSize = 0;

    ArrayList<TreeMap<Coords, ArrayList<Coords>>> allAvailableMoves = null;

    Controller() {
        game = null;
        saveLoadManager = new SaveLoadManager();
    }

    String[] createNewGame(int boardSize) {
        this.playerType = PlayerType.HUMAN;
        this.boardSize = boardSize;

        Player players[] = Player.getPlayersForConstructor(this.playerType);
        game = new Game(boardSize, players);

        String[] temp = {
                Utility.getPlayerTurnString(game.getActivePlayerTurn())
        };
        return temp;
    }

    String[] createNewGame(int boardSize, TypeOfGame typeOfGame) {
        this.playerType = PlayerType.COMPUTER;
        this.boardSize = boardSize;
        this.typeOfGame = typeOfGame;

        Player players[] = Player.getPlayersForConstructor(this.playerType);
        game = new Game(boardSize, players);

        String[] temp = {
                Utility.getPlayerTurnString(game.getActivePlayerTurn())
        };
        return temp;
    }

    String[] makeMove(Coords coords) throws FieldIsNotEmptyException, MoveNotAvailableException {

        try {
            game.controlMoveIfValid(coords, allAvailableMoves);
        } catch (MoveNotAvailableException e) {
            throw e;
        }

        game.countStones();

        game.makeCheckpoint();
        game.turnHasBeenMade();

        String[] temp = {
                Utility.getPlayerTurnString(game.getActivePlayerTurn())
        };
        return temp;
    }

    void freezeStones() {
        ArrayList<Field>[] temp = game.countStones();

        ArrayList<Field> notFrozenStones = new ArrayList<>();

        for (Field field: temp[game.getActivePlayerTurn()]) {
            if (!field.isFrozen()) {
                notFrozenStones.add(field);
            }
        }

        Random random = new Random();

        int randomInitNumber = 0, randomPersistNumber = 0, randomNumberOfStones = 0;
        while ((randomInitNumber = random.nextInt()) <= Utility.MAXFREEZETIME);
        while ((randomPersistNumber = random.nextInt()) <= Utility.MAXFREEZETIME);
        while ((randomNumberOfStones = random.nextInt()) <= notFrozenStones.size());

        for (int i = 0; i < randomNumberOfStones; i++) {
            temp[game.getActivePlayerTurn()].get(i).freeze(randomInitNumber, randomPersistNumber);
        }
    }

    String gameEndedResult() {
        return Utility.getGameEndedString(game.getPlayers());
    }

    String saveGame(String nameOfGame) throws GameSavingFailureException {
        try {
            saveLoadManager.save(nameOfGame, game.getPlayers(), game.getLogger(), Utility.PLAYERONE, typeOfGame);
        } catch (Exception e) {
            throw new GameSavingFailureException();
        }

        return Utility.getSuccessfulSaveGameString();
    }

    String[] loadGame(String nameOfGame) throws GameLoadingFailureException, GameLoadingNameNotFoundException{
        ArrayList<String> gameInfo = null;
        ArrayDeque<Board> gameBoards = null;

        int activePlayer = 0;
        int[] scoreOfPlayers = new int[2];

        try {
            gameInfo = saveLoadManager.load(nameOfGame);

            if (gameInfo.remove(0).charAt(0) == playerType.COMPUTER.getKey()) {
                this.playerType = PlayerType.COMPUTER;
            }
            else {
                this.playerType = PlayerType.HUMAN;
            }
            System.out.println("Typ hrace: " + playerType);

            Utility.setPlayerString(playerType == PlayerType.COMPUTER);

            this.boardSize = Integer.parseUnsignedInt(gameInfo.remove(0).trim());
            System.out.println("Velikost desky: " + boardSize);

            if (gameInfo.remove(0).trim().equals(TypeOfGame.EASY.getDifficulty())) {
                this.typeOfGame = TypeOfGame.EASY;
            } else {
                this.typeOfGame = TypeOfGame.HARD;
            }
            System.out.println("Obtiznost: " + typeOfGame);

            scoreOfPlayers[Utility.PLAYERONE] = Integer.parseInt(gameInfo.remove(0).trim());
            System.out.println("Prvni skore: " + scoreOfPlayers[Utility.PLAYERONE]);
            scoreOfPlayers[Utility.PLAYERTWO] = Integer.parseInt(gameInfo.remove(0).trim());
            System.out.println("Druhe skore: " + scoreOfPlayers[Utility.PLAYERTWO]);
            activePlayer = Integer.parseInt(gameInfo.remove(0).trim());
            System.out.println("Aktivni hrac: " + activePlayer);
            
            gameBoards = game.parseBoards(gameInfo, boardSize);
            System.out.println("Pocet boardu: " + gameBoards.size());

        } catch (GameLoadingNameNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw  new GameLoadingFailureException();
        }

        Player[] players = Player.getPlayersForConstructor(playerType, scoreOfPlayers);
        game = new Game(boardSize, players, gameBoards, activePlayer);

        String[] temp = {
            Utility.getSuccessfulLoadGameString(),
            Utility.getPlayerTurnString(game.getActivePlayerTurn())
        };
        return temp;
    }

    void undoMove() throws NoMoreMovesToUndoException {
        Board temp = null;
        try {
            temp = game.makeUndo();
            game.setBoard(temp);
        } catch (EmptyStackException e) {
            throw new NoMoreMovesToUndoException();
        } catch (NullPointerException e) {
            Player players[] = Player.getPlayersForConstructor(playerType);
            game = new Game(boardSize, players);
            throw new NoMoreMovesToUndoException();
        }
    }

    int[] getScore() {
        int[] score = new int[2];
        Player[] playerScore =  game.getPlayers();

        score[Utility.PLAYERONE] = playerScore[Utility.PLAYERONE].getScore();
        score[Utility.PLAYERTWO] = playerScore[Utility.PLAYERTWO].getScore();

        return score;
    }

    Board getBoard() {
        return game.getBoard();
    }

    void controlIfGameEnded() throws GameEndedException, ComputerHasPlayed {
        allAvailableMoves = new ArrayList<>();

        for (int x = 0; x < Board.SIZE; x++) {
            for (int y = 0; y < Board.SIZE; y++) {
                        try {
//                          System.out.println("Projizdim prazdna pole, ted budu hledat okolni pozice pro : " + x + " a " + y);
                            ArrayList<Coords> temp = game.checkPositionForMoves(new Coords(x, y));
                            if (temp.isEmpty()) {
                                continue;
                            }
                            //System.out.println("tempCoords pro prvek " + x + " a " + y + " nyni obsahuje : ");
                           // for (Coords tempo : temp) {
                            //    System.out.println("Obsahuje prvek: " + tempo.getX() + " a " + tempo.getY());
                           //}
                            //System.out.println("Konec obsahu");
                            NextTry:
                            for (Coords direction : temp) {
                                int i, j;
                                ArrayList<Coords> tempCoords = new ArrayList<>();
                               // System.out.println("Hledam celkove validni cesty pro: " + direction.getX() + " a " + direction.getY());
                                for (i = direction.getX(), j = direction.getY(); Utility.isInBoard(new Coords(i, j)); i += (direction.getX() - x), j += (direction.getY() - y)) {
                                   // System.out.println("Pokracuju v ceste na: " + i + " a " + j);
                                    try {
                                        if (getBoard().getField(i, j).getColor() != game.getActivePlayer().getColor()) {
                                       //     System.out.println("V diagonale je dalsi: " + getBoard().getField(i, j).getColor() + " pro " + i + " a " + j);
                                            tempCoords.add(new Coords(i, j));
                                      //      System.out.println("Pridavam prvek do stacku " + i + " a " + j + ". Obsah je ted: ");
                                        //    for (Coords tempo : tempCoords) {
                                       //         System.out.println("Obsahuje prvek: " + tempo.getX() + " a " + tempo.getY());
                                      //      }
                                      //      System.out.println("Konec obsahu");
                                        } else {
                                         //   System.out.println("Na konci diagonaly je i moje barva, takze to cele hodim do stromu a ulozim si pro pripadnou zmenu");
                                        //    System.out.println("Cele to ukladam pro" + x + " a " + y);
                                            TreeMap<Coords, ArrayList<Coords>> map = new TreeMap<>();
                                            map.put(new Coords(x, y), tempCoords);
                                        //    System.out.println("S obsahem:");
                                       //     for (Coords tempo : tempCoords) {
                                       //         System.out.println("Obsahuje prvek: " + tempo.getX() + " a " + tempo.getY());
                                       //     }
                                       //     System.out.println("Konec obsahu");
                                            allAvailableMoves.add(map);
                                         //   System.out.println("TOTO POLICKO JE VALIDNI:" + x + " a " + y);
                                            continue NextTry;
                                        }
                                    } catch (FieldIsEmptyException fieldException) {
                                        //System.out.println("Toto pole je prazdne, jdu dalsi cyklus pro dalsi okolni souradnici");
                                        continue NextTry;
                                    }
                                }
                            }
                        } catch (NoMovesAvailableException movesException) {}
            }
        }

        if (allAvailableMoves.isEmpty()) {
            throw new GameEndedException();
        }

        try {
            game.controlIfComputerTurn(typeOfGame);
        } catch (ComputerHasPlayed e) {
            throw e;
        }
    }
}
