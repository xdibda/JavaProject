package othello;

import othello.Utility.*;

public class Board implements Cloneable {
    static int SIZE = 8;
    private Field[] map;

    Board() {
        map = new Field[SIZE * SIZE];
        this.allocateFields();
        this.initBoardStones();
    }

    Board(int size) throws NotValidMatrixSize {
        if (size % 2 != 0 | size > 12 | size < 6)
            throw new NotValidMatrixSize();
        SIZE = size;
        map = new Field[SIZE * SIZE];
        this.allocateFields();
        this.initBoardStones();
    }

    Board(int size, String boardStones) {
        SIZE = size;
        map = new Field[SIZE * SIZE];
        char[] charArray = boardStones.trim().toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            map[i] = new Field();
            if (charArray[i] == 'B') {
                map[i].setColor(Color.BLACK);
            }
            else if (charArray[i] == 'W') {
                map[i].setColor(Color.WHITE);
            }
        }
    }

    Board (Board board) {
        this.map = new Field[board.getField().length];
        allocateFields();
        for (int i = 0; i < board.getField().length; i++) {
            try {
                this.map[i] = (Field) board.map[i].clone();
            } catch (CloneNotSupportedException e) {}
        }
    }

    void allocateFields() {
        for (int i = 0; i < SIZE * SIZE; i++) {
            map[i] = new Field();
        }
    }

    void initBoardStones() {
        for (int i = Board.SIZE / 2; i >= (Board.SIZE / 2) - 1; i--) {
            for (int j = Board.SIZE / 2; j >= (Board.SIZE / 2) - 1; j--) {
                if (i == j)
                    setField(i, j, Color.WHITE);
                else
                    setField(i, j, Color.BLACK);
            }
        }
    }

    public Board copy() {
        return new Board(this);
    }

    void setField(int x, int y, Color color) {
        Field temp = map[y * SIZE + x];
        temp.setColor(color);
    }

    void setField(Coords coords, Color color) throws FieldIsNotEmptyException {
        if (!map[coords.getY() * SIZE + coords.getX()].isEmpty()) {
            throw new FieldIsNotEmptyException();
        }
        Field temp = map[coords.getY() * SIZE + coords.getX()];
        temp.setColor(color);
    }

    void changeField(Coords coords) {
        Field temp = getField(coords.getX(), coords.getY());
        try {
            if (temp.getColor() == Color.BLACK) {
                temp.setColor(Color.WHITE);
            }
            else temp.setColor(Color.BLACK);
        } catch (FieldIsEmptyException e) {}
    }

    Field getField(int x, int y) {
        return map[y * SIZE + x];
    }

    Field[] getField() {
        return map;
    }
}
