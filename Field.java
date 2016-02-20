/**
 * Třída pro správu pole hrací desky
 * Funkce:  1) Získávání informace zdali je pole prázdné
 *          2) Práce s barvou na daném poli
 * @author Lukáš Dibďák
 * @see othello.StoneFreeze
 */

package othello;

import othello.Utility.*;

public class Field extends StoneFreeze implements Cloneable {
    private Color color;
    private boolean empty;

    /**
     * Konstruktor pole, implicitně nastavené na prázdné
     */
    Field() {
        empty = true;
    }

    /**
     * Získání barvy kamene na poli
     * @return Barva kamene typu {@code Color}
     * @throws FieldIsEmptyException Pole je prázdné a nemá tedy na sobě žádný kámen
     * @see Color
     */
    Color getColor() throws FieldIsEmptyException {
        if (empty) {
            throw new FieldIsEmptyException();
        }
        return color;
    }

    /**
     * Nastavení kamene barvy na pole
     * @param color Barva kamene
     */
    void setColor(Color color) {
        empty = false;
        this.color = color;
    }

    /**
     * Získání informace zda je pole prázdné
     * @return Prázdné/neprázdné pole
     */
    boolean isEmpty() {
        return empty;
    }

    /**
     * Klonování pole pro získání hluboké kopie
     * @return Klon pole typu {@code Object}
     * @throws CloneNotSupportedException Nekompatibilní typy na klonování
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
