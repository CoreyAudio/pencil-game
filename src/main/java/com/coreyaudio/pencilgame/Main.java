package com.coreyaudio.pencilgame;

import javax.swing.SwingUtilities;
import com.coreyaudio.pencilgame.ui.GameWindow;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameWindow().show());
    }
}
