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
    private JPanel buttonLayout;
    
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
    
    private void setButtons(){
        firstButton = new JButton();
        secondButton = new JButton();
        GridBagConstraints gbc = new GridBagConstraints();
        buttonLayout = new JPanel(new GridBagLayout());
        firstButton.addActionListener(_ -> newGame());
        firstButton.setText("START");
        firstButton.setFocusable(false);
        secondButton.addActionListener(_ -> System.exit(0));
        secondButton.setText("EXIT");
        secondButton.setFocusable(false);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5,5,5,5);
        buttonLayout.add(firstButton, gbc);
        buttonLayout.add(secondButton, gbc);
    }
    
    private void resetButtons(){
        for (ActionListener a : firstButton.getActionListeners()){
            firstButton.removeActionListener(a);
        }
        for (ActionListener b : secondButton.getActionListeners())
        {
            secondButton.removeActionListener(b);
        }
    }
    
    private void newGame(){
        resetButtons();
        titleLabel.setIcon(null);
        titleLabel.setText("<html><center>HOW MANY PENCILS WOULD <br/> YOU LIKE TO USE?</center></html>");
        firstButton.setText("5");
        firstButton.addActionListener(_ -> GameLogic.printPencils(5));
        firstButton.addActionListener(_ -> playerChoice());
        secondButton.setText("10");
        secondButton.addActionListener(_ -> GameLogic.printPencils(10));
        secondButton.addActionListener(_ -> playerChoice());
    }
    
    private void playerChoice(){
        resetButtons();
        titleLabel.setText("<html><center>WHO GOES FIRST?</center></html>");
        firstButton.setText("USER");
        secondButton.setText("COMPUTER");
    }
}