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
                        System.out.print("B");
                    else
                        System.out.print("W");
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
        String nextPlayer = null;

        while (true) {
            try {
                Player players[];
                ArrayList<String> tokenArgumentsArray = new ArrayList<>();
                TypeOfInstruction typeOfInstruction = fileManager.getDecision(tokenArgumentsArray);
                switch (typeOfInstruction) {
                    case NEW:
                        if (fileManager.getGameType() == null) {
                            nextPlayer = controller.createNewGame(fileManager.getBoardSize());
                        } else {
                            nextPlayer = controller.createNewGame(fileManager.getBoardSize(), fileManager.getGameType());
                        }
                        showBoard(controller.getBoard());
                        System.out.println(nextPlayer);
                        break;
                    case MOVE:
                        try {
                            controller.controlIfGameEnded();
                        } catch (GameEndedException e) {
                            System.out.print(e);
                            System.out.println(controller.gameEndedResult());
                            break;
                        }

                        Coords coords = new Coords(tokenArgumentsArray.get(0).charAt(0), Integer.parseInt(tokenArgumentsArray.get(1)));

                        try {
                            nextPlayer = controller.makeMove(coords);
                        } catch (FieldIsNotEmptyException | MoveNotAvailableException e) {
                            System.out.println(e);
                        }

                        showBoard(controller.getBoard());
                        System.out.println(nextPlayer);
                        break;
                    case SAVE:
                        try {
                            controller.saveGame(tokenArgumentsArray.get(0));
                            System.out.print("Hra byla uspesne ulozena");
                        } catch (GameSavingFailureException e) {
                            System.out.println(e);
                        }
                        break;
                    case LOAD:
                        try {
                            controller.loadGame(tokenArgumentsArray.get(0));
                            System.out.print("Hra byla uspesne nactena");
                        } catch (GameLoadingNameNotFoundException | GameLoadingFailureException e) {
                            System.out.println(e);
                        }
                        showBoard(controller.getBoard());
                        break;
                    case UNDO:
                        try {
                            controller.undoMove();
                        } catch (NoMoreMovesToUndoException e) {
                            System.out.println(e);
                        }
                        showBoard(controller.getBoard());
                        break;
                }
            } catch (ReadingFromConsoleFailureException | InvalidTokenInputException | BadTokenArgumentException e) {
                System.out.println(e);
            }
        }
    }
}
