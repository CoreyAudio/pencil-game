package com.coreyaudio.pencilgame.logic;

import java.util.concurrent.ThreadLocalRandom;

public class ComputerStrategy {
    public int chooseMove(int pencilCount) {
        if (pencilCount <= 0) {
            throw new IllegalArgumentException("Pencil count must be positive.");
        }

        if ((pencilCount - 1) % 4 == 0) {
            return 1;
        }
        if ((pencilCount - 2) % 4 == 0) {
            return 2;
        }
        if ((pencilCount - 3) % 4 == 0) {
            return 3;
        }

        return ThreadLocalRandom.current().nextInt(1, Math.min(3, pencilCount) + 1);
    }
}
