package othello;

import othello.Utility.*;

public class Field extends StoneFreeze implements Cloneable {
    private Color color;
    private boolean empty;

    Field() {
        empty = true;
    }

    Color getColor() throws FieldIsEmptyException {
        if (empty) {
            throw new FieldIsEmptyException();
        }
        return color;
    }

    void setColor(Color color) {
        empty = false;
        this.color = color;
    }

    boolean isEmpty() {
        return empty;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
