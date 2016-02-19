package othello;

import othello.Utility.*;
import java.util.*;

public class GameCommandLine {
    static ArrayList<Character> characters = new ArrayList<>();
    static ArrayList<String> numbers = new ArrayList<>();

    static void showBoard(Board board) {
        for (int i = Utility.transformCharToInt('a'); i <= Utility.transformCharToInt('l'); i++)
            characters.add(Utility.transformIntToChar(i));

        for (int i = 1; i <= 12; i++) {
            if (i < 10)
                numbers.add(Integer.toString(i) + " ");
            else
                numbers.add(Integer.toString(i));
        }


        for (int i = 0; i < Board.SIZE * 2 + 5; i++) {
            System.out.print("-");
        }
        System.out.print(System.lineSeparator());
        System.out.print("    ");
        for (int i = 0; i < Board.SIZE; i++) {
            System.out.print(characters.get(i));
            System.out.print(" ");
        }
        System.out.print(System.lineSeparator());
        System.out.print("   ");
        for (int i = 0; i < Board.SIZE * 2 + 1; i++) {
            System.out.print("-");
        }
        System.out.print('\n');
        for (int i = 0; i < Board.SIZE; i++) {
            System.out.print(numbers.get(i));
            System.out.print(" |");
            for (int j = 0; j < Board.SIZE; j++) {
                try {
                    if (board.getField(j, i).getColor() == Color.BLACK)
                        System.out.print(Color.BLACK.getKey());
                    else
                        System.out.print(Color.WHITE.getKey());
                    if (j != Board.SIZE - 1)
                        System.out.print(" ");
                } catch (FieldIsEmptyException e) {
                    System.out.print(0);
                    if (j != Board.SIZE - 1)
                        System.out.print(" ");
                }
            }
            System.out.print("|");
            System.out.print('\n');
        }
        System.out.print("   ");
        for (int i = 0; i < Board.SIZE * 2 + 1; i++) {
            System.out.print("-");
        }
        System.out.print('\n');
    }

    public static void main(String args[]) {
        Controller controller = new Controller();
        ReadLineManager fileManager = new ReadLineManager();
        String[] nextPlayer = null;

        boolean gameStarted = false;

        while (true) {
            try {
                ArrayList<String> tokenArgumentsArray = new ArrayList<>();

                if (gameStarted) {
                    try {
                        controller.controlIfGameEnded();
                    } catch (GameEndedException e) {
                        System.out.println(e);
                        System.out.println("Konecne skore: " + Utility.PLAYERS[Utility.PLAYERONE] + ": " + e.getScore()[Utility.PLAYERONE] + ", " + Utility.PLAYERS[Utility.PLAYERTWO] + ": " + e.getScore()[Utility.PLAYERTWO]);
                        gameStarted = false;
                    } catch (ComputerHasPlayed e) {
                        showBoard(controller.getBoard());
                        System.out.println("Skore: " + Utility.PLAYERS[Utility.PLAYERONE] + ": "+ controller.getScore()[Utility.PLAYERONE] + ", " + Utility.PLAYERS[Utility.PLAYERTWO] + ": " + controller.getScore()[Utility.PLAYERTWO]);
                        System.out.println(e);
                    }
                }

                TypeOfInstruction typeOfInstruction = fileManager.getDecision(tokenArgumentsArray);
                switch (typeOfInstruction) {
                    case NEW:
                        gameStarted = true;
                        if (fileManager.getGameType() == null) {
                            nextPlayer = controller.createNewGame(fileManager.getBoardSize());
                        } else {
                            nextPlayer = controller.createNewGame(fileManager.getBoardSize(), fileManager.getGameType());
                        }
                        showBoard(controller.getBoard());
                        System.out.println("Skore je: " + Utility.PLAYERS[Utility.PLAYERONE] + ": " + nextPlayer[1] + ", " + Utility.PLAYERS[Utility.PLAYERTWO] + ": " + nextPlayer[2]);
                        System.out.println(nextPlayer[0]);
                        break;
                    case MOVE:
                        Coords coords = new Coords(tokenArgumentsArray.get(0).charAt(0), Integer.parseInt(tokenArgumentsArray.get(1)));

                        try {
                            nextPlayer = controller.makeMove(coords);
                        } catch (FieldIsNotEmptyException | MoveNotAvailableException e) {
                            System.out.println(e);
                        }

                        showBoard(controller.getBoard());
                        System.out.println("Skore je: " + Utility.PLAYERS[Utility.PLAYERONE] + ": " + nextPlayer[1] + ", " + Utility.PLAYERS[Utility.PLAYERTWO] + ": " + nextPlayer[2]);
                        System.out.println(nextPlayer[0]);
                        break;
                    case SAVE:
                        try {
                            System.out.println(controller.saveGame(tokenArgumentsArray.get(0)));
                        } catch (GameSavingFailureException e) {
                            System.out.println(e);
                        }
                        break;
                    case LOAD:
                        try {
                            nextPlayer = controller.loadGame(tokenArgumentsArray.get(0));
                            gameStarted = true;
                            System.out.println(nextPlayer[0]);
                            showBoard(controller.getBoard());
                            System.out.println("Skore je: " + Utility.PLAYERS[Utility.PLAYERONE] + ": " + nextPlayer[2] + ", " + Utility.PLAYERS[Utility.PLAYERTWO] + ": " + nextPlayer[3]);
                            System.out.println(nextPlayer[1]);
                        } catch (GameLoadingNameNotFoundException | GameLoadingFailureException e) {
                            System.out.println(e);
                        }
                        break;
                    case UNDO:
                        try {
                            nextPlayer = controller.undoMove();
                            showBoard(controller.getBoard());
                            System.out.println("Skore je: " + Utility.PLAYERS[Utility.PLAYERONE] + ": " + nextPlayer[1] + ", " + Utility.PLAYERS[Utility.PLAYERTWO] + ": " + nextPlayer[2]);
                            System.out.println(nextPlayer[0]);
                        } catch (NoMoreMovesToUndoException e) {
                            System.out.println(e);
                        }
                        break;
                }

            } catch (ReadingFromConsoleFailureException | InvalidTokenInputException | BadTokenArgumentException e) {
                System.out.println(e);
            }
        }
    }
}
