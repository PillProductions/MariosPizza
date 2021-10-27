import java.awt.event.KeyListener;

public class KeyEvent implements KeyListener {
    public static boolean exitLoop = false;

    public void keyTyped(java.awt.event.KeyEvent k) {
        exitLoop = true;
    }

    public void keyPressed(java.awt.event.KeyEvent k) {
        exitLoop = true;
    }

    public void keyReleased(java.awt.event.KeyEvent k) {
        exitLoop = true;
    }
}
