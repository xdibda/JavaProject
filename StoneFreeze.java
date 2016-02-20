/**
 * Třída přidávající možnost zmrazení pole
 * Funkce:  1) Možnost zmrazení pole na určitou dobu
 *          2) Získání informace o tomto zmrazení
 * @author Lukáš Dibďák
 * @see othello.Field
 */

package othello;

import java.util.Timer;
import java.util.TimerTask;

public class StoneFreeze {
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
            try {
                Thread.sleep((long) s * 1000);
            } catch (InterruptedException e) {}
            frozen = false;
        }
    }

    private boolean frozen;

    /**
     * Konstuktor třídy, implicitně určuje nezmrazené políčko
     */
    StoneFreeze() {
        frozen = false;
    }

    /**
     * Událost zmrazení políčka na určitou dobu
     * @param initSec Za jak dlouho se událost provede
     * @param persistSec Jak dlouho bude zmrazení trvat
     */
    void freeze(int initSec, int persistSec) {
        Timer freezeTimer = new Timer();
        Defrost defrost = new Defrost(persistSec);

        frozen = true;

        freezeTimer.schedule(defrost, initSec * 1000);
        freezeTimer.cancel();
    }

    /**
     * Získání informace, zda je políčko zmrazené
     * @return Zmrazené/nezmrazené políčko
     */
    public boolean isFrozen() {
        return frozen;
    }
}
