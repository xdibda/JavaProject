/**
 * Třída pro správu pole hrací desky
 * Funkce:  1) Získávání informace zdali je pole prázdné
 *          2) Práce s barvou na daném poli
 *          3) Zmrazení kamenů
 * @author Lukáš Dibďák
 */

package othello;

import othello.Utility.*;

import java.util.Timer;
import java.util.TimerTask;

public class Field implements Cloneable {
    /**
     * Vnitřní třída pro plánování události
     */
    class Defrost extends TimerTask {
        int s = 0;

        /**
         * Konstruktor vnitřní třídy pro události zmrazení
         * @param s Doba trvání zmrazení v sekundách
         * @see Controller
         */
        Defrost(int s) {
            this.s = s;
        }

        /**
         * Specifikace události, která se má provést při naplánování
         */
        @Override
        public void run() {
            frozen = true;
            changeColorFreeze(color);

            try {
                Thread.sleep((long) s * 1000);
            } catch (InterruptedException e) {}

            frozen = false;
            changeColorFreeze(color);
        }

    }

    private Color color;
    private boolean empty;
    private boolean frozen;

    /**
     * Konstruktor pole, implicitně nastavené na prázdné
     */
    Field() {
        empty = true;
        frozen = false;
    }

    /**
     * Mění barvu mezi zmrazením a odmrazením
     * @param color Barva kamenu
     */
    void changeColorFreeze(Color color) {
        switch (color) {
            case WHITE:
                this.color = Color.FWHITE;
                break;
            case BLACK:
                this.color = Color.FBLACK;
                break;
            case FWHITE:
                this.color = Color.WHITE;
                break;
            case FBLACK:
                this.color = Color.BLACK;
                break;
        }
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
        if (!frozen)
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

    /**
     * Událost zmrazení políčka na určitou dobu
     * @param initSec Za jak dlouho se událost provede
     * @param persistSec Jak dlouho bude zmrazení trvat
     */
    void freeze(int initSec, int persistSec) {
        Timer freezeTimer = new Timer();
        Defrost defrost = new Defrost(persistSec);

        System.out.println("mrznu2");

        freezeTimer.schedule(defrost, initSec * 1000);
        freezeTimer.cancel();
    }

    /**
     * Ziskává zda je políčko zamrznuté
     * @return zamrznuté/nezamrznuté
     */
    public boolean isFrozen() {
        return frozen;
    }
}
