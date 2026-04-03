package com.coreyaudio.pencilgame.ui;

import com.coreyaudio.pencilgame.logic.ComputerStrategy;
import com.coreyaudio.pencilgame.model.GameResult;
import com.coreyaudio.pencilgame.model.GameSession;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.net.URL;

public class GameWindow {
    private static final int ACTION_DELAY_MS = 3000;
    private static final int MENU_COUNTDOWN_SECONDS = 5;

    private final JFrame frame;
    private final JLabel titleLabel;
    private final JPanel buttonPanel;
    private final JButton firstButton;
    private final JButton secondButton;
    private final JButton thirdButton;
    private final GameSession gameSession;

    private Timer transitionTimer;
    private Timer menuCountdownTimer;

    public GameWindow() {
        this.gameSession = new GameSession(new ComputerStrategy());
        this.frame = new JFrame("Pencil Game");
        this.titleLabel = new JLabel("", SwingConstants.CENTER);
        this.buttonPanel = new JPanel(new GridBagLayout());
        this.firstButton = new JButton();
        this.secondButton = new JButton();
        this.thirdButton = new JButton();
        buildUi();
    }

    public void show() {
        showStartMenu();
        frame.setVisible(true);
    }

    private void buildUi() {
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(new BorderLayout());

        URL frameIconUrl = GameWindow.class.getResource("/images/pencil_game_icon.png");
        if (frameIconUrl != null) {
            frame.setIconImage(new ImageIcon(frameIconUrl).getImage());
        }

        titleLabel.setForeground(Color.GREEN);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        Border border = BorderFactory.createLineBorder(Color.WHITE, 2);
        titleLabel.setBorder(border);

        URL labelIconUrl = GameWindow.class.getResource("/images/pencil_game_icon_16.png");
        if (labelIconUrl != null) {
            titleLabel.setIcon(new ImageIcon(labelIconUrl));
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);

        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(firstButton, gbc);
        buttonPanel.add(secondButton, gbc);
        buttonPanel.add(thirdButton, gbc);

        styleButton(firstButton);
        styleButton(secondButton);
        styleButton(thirdButton);

        frame.add(titleLabel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void styleButton(JButton button) {
        button.setFocusable(false);
    }

    private void showStartMenu() {
        stopTimers();
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setIcon(loadLabelIcon());
        titleLabel.setText("<html><center>WELCOME TO THE<br/>PENCIL GAME!</center></html>");

        configureButtons(
                new ButtonSpec("START", this::showPencilSelection),
                new ButtonSpec("EXIT", () -> System.exit(0)),
                null
        );
    }

    private void showPencilSelection() {
        titleLabel.setIcon(null);
        titleLabel.setText("<html><center>HOW MANY PENCILS WOULD <br/> YOU LIKE TO USE?</center></html>");

        configureButtons(
                new ButtonSpec("5", () -> startTurnSelection(5)),
                new ButtonSpec("10", () -> startTurnSelection(10)),
                new ButtonSpec("15", () -> startTurnSelection(15))
        );
    }

    private void startTurnSelection(int pencils) {
        titleLabel.setText("<html><center>WHO GOES FIRST?</center></html>");

        configureButtons(
                new ButtonSpec("PLAYER", () -> startGame(pencils, true)),
                new ButtonSpec("COMPUTER", () -> startGame(pencils, false)),
                null
        );
    }

    private void startGame(int pencils, boolean playerStarts) {
        gameSession.start(pencils, playerStarts);
        if (playerStarts) {
            showPlayerTurn();
        } else {
            runComputerTurn();
        }
    }

    private void showPlayerTurn() {
        String pencils = gameSession.getPencilDisplay();
        int count = gameSession.getPencilCount();
        titleLabel.setText("<html><center>"
                + count + "<br/>"
                + pencils
                + "<br/><br/>PLEASE CHOOSE THE AMOUNT OF <br/> PENCILS TO REMOVE!</center></html>");

        ButtonSpec one = new ButtonSpec("1", () -> runPlayerTurn(1));
        ButtonSpec two = count >= 2 ? new ButtonSpec("2", () -> runPlayerTurn(2)) : null;
        ButtonSpec three = count >= 3 ? new ButtonSpec("3", () -> runPlayerTurn(3)) : null;

        configureButtons(one, two, three);
    }

    private void runPlayerTurn(int amount) {
        gameSession.takePlayerTurn(amount);

        titleLabel.setText("<html><center>"
                + gameSession.getPencilCount() + "<br/>"
                + gameSession.getPencilDisplay()
                + "<br/><br/>THERE ARE " + gameSession.getPencilCount() + " PENCILS LEFT.</center></html>");

        hideButtons();
        continueAfterDelay();
    }

    private void runComputerTurn() {
        int amount = gameSession.takeComputerTurn();
        titleLabel.setText("<html><center>"
                + gameSession.getPencilCount() + "<br/>"
                + gameSession.getPencilDisplay()
                + "<br/><br/>THE COMPUTER TOOK " + amount + " PENCIL" + (amount > 1 ? "S" : "") + "!</center></html>");

        hideButtons();
        continueAfterDelay();
    }

    private void continueAfterDelay() {
        stopTransitionTimer();
        transitionTimer = new Timer(ACTION_DELAY_MS, event -> {
            GameResult result = gameSession.getResult();
            if (result == GameResult.WIN) {
                showGameOver(true);
            } else if (result == GameResult.LOSE) {
                showGameOver(false);
            } else if (gameSession.isPlayerTurn()) {
                showPlayerTurn();
            } else {
                runComputerTurn();
            }
        });
        transitionTimer.setRepeats(false);
        transitionTimer.start();
    }

    private void showGameOver(boolean playerWon) {
        hideButtons();
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        startMenuCountdown(playerWon);
    }

    private void startMenuCountdown(boolean playerWon) {
        stopMenuCountdownTimer();
        final int[] secondsLeft = {MENU_COUNTDOWN_SECONDS};
        updateGameOverText(playerWon, secondsLeft[0]);

        menuCountdownTimer = new Timer(1000, event -> {
            secondsLeft[0]--;
            if (secondsLeft[0] <= 0) {
                stopMenuCountdownTimer();
                showStartMenu();
            } else {
                updateGameOverText(playerWon, secondsLeft[0]);
            }
        });
        menuCountdownTimer.start();
    }

    private void updateGameOverText(boolean playerWon, int secondsLeft) {
        String headline = playerWon ? "CONGRATULATIONS!<br/>YOU WIN!" : "GAME OVER<br/>YOU LOSE!";
        titleLabel.setText("<html><center>" + headline
                + "<br/><br/>RETURNING TO MENU IN " + secondsLeft + "...</center></html>");
    }

    private void configureButtons(ButtonSpec first, ButtonSpec second, ButtonSpec third) {
        applyButton(firstButton, first);
        applyButton(secondButton, second);
        applyButton(thirdButton, third);
    }

    private void applyButton(JButton button, ButtonSpec spec) {
        for (var listener : button.getActionListeners()) {
            button.removeActionListener(listener);
        }

        if (spec == null) {
            button.setVisible(false);
            button.setText("");
            return;
        }

        button.setText(spec.text());
        button.addActionListener(event -> spec.action().run());
        button.setVisible(true);
    }

    private void hideButtons() {
        firstButton.setVisible(false);
        secondButton.setVisible(false);
        thirdButton.setVisible(false);
    }

    private ImageIcon loadLabelIcon() {
        URL labelIconUrl = GameWindow.class.getResource("/images/pencil_game_icon_16.png");
        return labelIconUrl == null ? null : new ImageIcon(labelIconUrl);
    }

    private void stopTimers() {
        stopTransitionTimer();
        stopMenuCountdownTimer();
    }

    private void stopTransitionTimer() {
        if (transitionTimer != null && transitionTimer.isRunning()) {
            transitionTimer.stop();
        }
    }

    private void stopMenuCountdownTimer() {
        if (menuCountdownTimer != null && menuCountdownTimer.isRunning()) {
            menuCountdownTimer.stop();
        }
    }

    private record ButtonSpec(String text, Runnable action) {
    }
}
