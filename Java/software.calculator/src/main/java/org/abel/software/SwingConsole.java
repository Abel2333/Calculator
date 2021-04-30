package org.abel.software;

import javax.swing.*;

public class SwingConsole {
    public static void run(final JFrame f, final int width, final int height, final String a) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run () {
                f.setTitle(a);
                //f.setTitle(f.getClass().getSimpleName());
                f.setSize(width, height);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setVisible(true);
            }
        });
    }
}
