package com.coreyaudio.pencilgame.ui;

import com.coreyaudio.pencilgame.GameLogic;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

public class GameWindow
{
    private JFrame mainFrame;
    private ImageIcon frameIcon;
    private ImageIcon labelIcon;
    private JLabel titleLabel;
    private JButton firstButton;
    private JButton secondButton;
    private JButton thirdButton;
    private JPanel buttonLayout;
    private GridBagConstraints gbc;
    
    public GameWindow(){
        SwingUtilities.invokeLater(this::buildUI);
    }
    
    public void buildUI(){
        loadIcons();
        configureWindow();
        configureLabel();
        setButtons();
        mainFrame.add(titleLabel, BorderLayout.CENTER);
        mainFrame.add(buttonLayout, BorderLayout.SOUTH);
        mainFrame.setVisible(true);
    }
    
    private void loadIcons(){
        frameIcon = new ImageIcon(Objects.requireNonNull(GameWindow.class.getResource("/images/pencil_game_icon.png")));
        labelIcon = new ImageIcon(Objects.requireNonNull(GameWindow.class.getResource("/images/pencil_game_icon_16.png")));
    }
    
    private void configureWindow(){
        mainFrame = new JFrame();
        mainFrame.setTitle("Pencil Game");
        mainFrame.setSize(400, 400);
        mainFrame.setIconImage(frameIcon.getImage());
        mainFrame.getContentPane().setBackground(Color.BLACK);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setLocationRelativeTo(null);
    }
    
    private void configureLabel(){
        titleLabel = new JLabel("<html><center>WELCOME TO THE<br/>PENCIL GAME!</center></html>", SwingConstants.CENTER);
        Border border = BorderFactory.createLineBorder(Color.WHITE, 2);
        titleLabel.setIcon(labelIcon);
        titleLabel.setForeground(Color.GREEN);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setBorder(border);
    }
    
    private void setGrid()
    {
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);
    }
    
    private void setButtons(){
        firstButton = new JButton();
        secondButton = new JButton();
        thirdButton = new JButton();
        buttonLayout = new JPanel(new GridBagLayout());
        firstButton.addActionListener(_ -> newGame());
        firstButton.setText("START");
        firstButton.setFocusable(false);
        secondButton.addActionListener(_ -> System.exit(0));
        secondButton.setText("EXIT");
        secondButton.setFocusable(false);
        setGrid();
        buttonLayout.add(firstButton, gbc);
        buttonLayout.add(secondButton, gbc);
        buttonLayout.add(thirdButton,gbc);
        thirdButton.setVisible(false);
    }
    
    private void resetButtons(){
        for (ActionListener a : firstButton.getActionListeners()){
            firstButton.removeActionListener(a);
        }
        for (ActionListener b : secondButton.getActionListeners())
        {
            secondButton.removeActionListener(b);
        }
        for (ActionListener c : thirdButton.getActionListeners())
        {
            thirdButton.removeActionListener(c);
        }
    }
    
    private void newGame(){
        resetButtons();
        titleLabel.setIcon(null);
        titleLabel.setText("<html><center>HOW MANY PENCILS WOULD <br/> YOU LIKE TO USE?</center></html>");
        firstButton.setText("5");
        firstButton.addActionListener(_ -> {
            GameLogic.storePencils(5);
            playerChoice();
        });
        secondButton.setText("10");
        secondButton.addActionListener(_ -> {
            GameLogic.storePencils(10);
            playerChoice();
        });
        thirdButton.setVisible(true);
        thirdButton.setText("15");
        thirdButton.addActionListener(_ -> {
            GameLogic.storePencils(15);
            playerChoice();
        });
    }
    
    private void playerChoice(){
        resetButtons();
        thirdButton.setVisible(false);
        titleLabel.setText("<html><center>WHO GOES FIRST?</center></html>");
        firstButton.setText("PLAYER");
        firstButton.addActionListener(_ -> playerTurn());
        secondButton.setText("COMPUTER");
        secondButton.addActionListener(_ -> computerTurn());
    }
    
    private void playerTurn(){
        resetButtons();
        GameLogic.player = true;
        thirdButton.setVisible(true);
        titleLabel.setText("<html><center>"
                + GameLogic.getPencils()
                +"<br/><br/>PLEASE CHOOSE THE AMOUNT OF <br/> PENCILS TO REMOVE!</center></html>");
        firstButton.setText("1");
        firstButton.addActionListener(_ -> {
            GameLogic.removePencils(1);
            turnScreen();
        });
        secondButton.setText("2");
        secondButton.addActionListener(_ -> {
            GameLogic.removePencils(2);
            turnScreen();
        });
        thirdButton.setText("3");
        thirdButton.addActionListener(_ -> {
            GameLogic.removePencils(3);
            turnScreen();
        });
        if (!firstButton.isVisible()){
            firstButton.setVisible(true);
            secondButton.setVisible(true);
        }
    }
    
    private void computerTurn(){
        resetButtons();
        GameLogic.player = false;
        titleLabel.setText("<html><center>"
                + GameLogic.getPencils()
                + "<br/><br/>THE COMPUTER TOOK " + GameLogic.getCount() + " PENCILS!</center></html>");
        firstButton.setVisible(false);
        secondButton.setVisible(false);
        frameTimer();
    }
    
    private void turnScreen(){
        resetButtons();
        firstButton.setVisible(false);
        secondButton.setVisible(false);
        thirdButton.setVisible(false);
        titleLabel.setText("<html><center>"
                + GameLogic.getPencils()
                + "<br/><br/>THERE ARE " + GameLogic.getCount() + " PENCILS LEFT.");
        frameTimer();
    }
    
    private void frameTimer(){
        Timer slowTime;
        if (GameLogic.player){
            slowTime = new Timer(3000, _ -> computerTurn());
        }else{
            slowTime = new Timer(3000, _ -> playerTurn());
        }
        slowTime.setRepeats(false);
        slowTime.start();
    }
}