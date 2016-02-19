package othello;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import othello.Utility.*;

public class ReadLineManager {
    private int boardSize;
    private TypeOfGame gameType;

    private BufferedReader commandLineReader;
    private StringTokenizer tokenizer;

    int getBoardSize() {
        return boardSize;
    }

    TypeOfGame getGameType() {
        return gameType;
    }

    void controlGameArguments(ArrayList<String> arguments) throws BadTokenArgumentException {
        String argument;
        argument = arguments.get(0);
        if (argument.equals("H")) {
            gameType = null;
            if (arguments.size() != 2) {
                throw new BadTokenArgumentException();
            } else {
                int boardSizeInteger = Integer.parseInt(arguments.get(1));
                if (boardSizeInteger < 6 || boardSizeInteger > 12 || !(boardSizeInteger % 2 == 0)) {
                    this.boardSize = 8;
                } else {
                    this.boardSize = boardSizeInteger;
                }
            }
        }
        else if(argument.equals("C")) {
            if (arguments.size() != 3) {
                throw new BadTokenArgumentException();
            } else {
                int boardSize = Integer.parseInt(arguments.get(1));
                if (boardSize < 6 || boardSize > 12 || !(boardSize % 2 == 0)) {
                    this.boardSize = 8;
                } else {
                    this.boardSize = boardSize;
                }
                argument = arguments.get(2);
                if (argument.equals("easy")) {
                    gameType = TypeOfGame.EASY;
                }
                else if(argument.equals("hard")) {
                    gameType = TypeOfGame.HARD;
                }
                else {
                    throw new BadTokenArgumentException();
                }
            }
        }
        else {
            throw new BadTokenArgumentException();
        }
    }

    void controlMakeMoveArguments(ArrayList<String> arguments) throws BadTokenArgumentException {
        String argument;
        argument = arguments.get(0);
        if (argument.length() > 1) {
            throw new BadTokenArgumentException();
        }
        else if (Character.toString(argument.charAt(0)).matches("[^a-" + Utility.transformIntToChar(Board.SIZE) + "]")) {
            throw new BadTokenArgumentException();
        }
        argument = arguments.get(1);
        int number = Integer.parseInt(argument);
        if (number < 0 || number > Board.SIZE) {
            throw  new BadTokenArgumentException();
        }
    }

    TypeOfInstruction getDecision(ArrayList<String> arguments) throws ReadingFromConsoleFailureException, InvalidTokenInputException, BadTokenArgumentException {
        commandLineReader = new BufferedReader(new InputStreamReader(System.in));
        String decision;
        try {
            decision = commandLineReader.readLine();
        } catch (IOException e) {
            throw new ReadingFromConsoleFailureException();
        }
        tokenizer = new StringTokenizer(decision);
        if (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            switch (token) {
                case "MOVE":
                case "move":
                    for (int i = 0; i < TypeOfInstruction.MOVE.getNumberOfArgumentRequired(); i++) {
                        if (tokenizer.hasMoreTokens()) {
                            String makeMoveArgument = tokenizer.nextToken();
                            arguments.add(makeMoveArgument);
                        } else {
                            throw new BadTokenArgumentException();
                        }
                    }
                    if (tokenizer.hasMoreTokens()) {
                        throw new BadTokenArgumentException();
                    }
                    try {
                        controlMakeMoveArguments(arguments);
                    } catch (BadTokenArgumentException | NumberFormatException e) {
                        throw new BadTokenArgumentException();
                    }
                    return TypeOfInstruction.MOVE;
                case "SAVE":
                case "save":
                    if (tokenizer.hasMoreTokens()) {
                        String fileName = tokenizer.nextToken();
                        arguments.add(fileName);
                        if (tokenizer.hasMoreTokens()) {
                            throw new BadTokenArgumentException();
                        }
                    } else {
                        throw new BadTokenArgumentException();
                    }
                    return TypeOfInstruction.SAVE;
                case "FREEZE":
                case "freeze":
                    if (tokenizer.hasMoreTokens()) {
                        throw new BadTokenArgumentException();
                    }
                    return TypeOfInstruction.FREEZE;
                case "LOAD":
                case "load":
                    if (tokenizer.hasMoreTokens()) {
                        String fileName = tokenizer.nextToken();
                        arguments.add(fileName);
                        if (tokenizer.hasMoreTokens()) {
                            throw new BadTokenArgumentException();
                        }
                    } else {
                        throw new BadTokenArgumentException();
                    }
                    return TypeOfInstruction.LOAD;
                case "NEW":
                case "new":
                    for (int i = 0; i < TypeOfInstruction.NEW.getNumberOfArgumentRequired(); i++) {
                        if (tokenizer.hasMoreTokens()) {
                            String newGameArgument = tokenizer.nextToken();
                            arguments.add(newGameArgument);
                        } else {
                            throw new BadTokenArgumentException();
                        }
                    }
                    if (tokenizer.hasMoreTokens()) {
                        String newGameArgument = tokenizer.nextToken();
                        arguments.add(newGameArgument);
                    }
                    try {
                        controlGameArguments(arguments);
                    } catch (BadTokenArgumentException | NumberFormatException e) {
                        throw new BadTokenArgumentException();
                    }
                    return TypeOfInstruction.NEW;
                case "UNDO":
                case "undo":
                    if (tokenizer.hasMoreTokens()) {
                        throw new BadTokenArgumentException();
                    }
                    return TypeOfInstruction.UNDO;
            }
        }
        throw new InvalidTokenInputException();
    }
}
