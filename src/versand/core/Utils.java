package versand.core;

import javax.swing.*;

public class Utils {

    public static void message(String msg) {
        new Thread(() -> JOptionPane.showMessageDialog(null, msg)).start();
    }

}
