package othello;

import java.util.Timer;
import java.util.TimerTask;

public class StoneFreeze {
    class Defrost extends TimerTask {
        int s = 0;

        Defrost(int s) {
            this.s = s;
        }

        @Override
        public void run() {
            try {
                Thread.sleep((long) s * 1000);
            } catch (InterruptedException e) {}
            frozen = false;
        }
    }

    private boolean frozen;

    StoneFreeze() {
        frozen = false;
    }

    void freeze(int initSec, int persistSec) {
        Timer freezeTimer = new Timer();
        Defrost defrost = new Defrost(persistSec);

        frozen = true;

        freezeTimer.schedule(defrost, initSec * 1000);
        freezeTimer.cancel();
    }

    public boolean isFrozen() {
        return frozen;
    }
}
