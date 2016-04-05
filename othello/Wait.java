package othello;

/**
 * Created by Lukas on 05.04.16.
 */
public class Wait implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {}
    }
}
