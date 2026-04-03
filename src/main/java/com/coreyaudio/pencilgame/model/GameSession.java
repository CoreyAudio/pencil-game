package com.coreyaudio.pencilgame.model;

import com.coreyaudio.pencilgame.logic.ComputerStrategy;

public class GameSession {
    private final ComputerStrategy computerStrategy;
    private int pencilCount;
    private boolean playerTurn;

    public GameSession(ComputerStrategy computerStrategy) {
        this.computerStrategy = computerStrategy;
    }

    public void start(int pencils, boolean playerStarts) {
        this.pencilCount = pencils;
        this.playerTurn = playerStarts;
    }

    public int getPencilCount() {
        return pencilCount;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public boolean canTake(int amount) {
        return amount >= 1 && amount <= 3 && amount <= pencilCount;
    }

    public void takePlayerTurn(int amount) {
        if (!playerTurn) {
            throw new IllegalStateException("It is not the player's turn.");
        }
        if (!canTake(amount)) {
            throw new IllegalArgumentException("Invalid pencil amount: " + amount);
        }
        pencilCount -= amount;
        playerTurn = false;
    }

    public int takeComputerTurn() {
        if (playerTurn) {
            throw new IllegalStateException("It is not the computer's turn.");
        }
        int amount = computerStrategy.chooseMove(pencilCount);
        pencilCount -= amount;
        playerTurn = true;
        return amount;
    }

    public GameResult getResult() {
        if (pencilCount > 0) {
            return GameResult.IN_PROGRESS;
        }
        return playerTurn ? GameResult.WIN : GameResult.LOSE;
    }

    public String getPencilDisplay() {
        return "| ".repeat(Math.max(0, pencilCount)).trim();
    }
}
