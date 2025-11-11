package com.coreyaudio.pencilgame.ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Objects;

public class GameWindow extends JFrame
{
    
    private ImageIcon frameIcon;
    private ImageIcon labelIcon;
    private JLabel titleLabel;
    private JButton firstButton;
    private JPanel buttonLayout;
    
    public GameWindow(){
        SwingUtilities.invokeLater(this::buildUI);
    }
    
    private void buildUI(){
        loadIcons();
        configureWindow();
        configureLabel();
        setButtons();
        add(titleLabel, BorderLayout.CENTER);
        add(buttonLayout, BorderLayout.SOUTH);
        setVisible(true);
    }
    
    private void loadIcons(){
        frameIcon = new ImageIcon(Objects.requireNonNull(GameWindow.class.getResource("/images/pencil_game_icon.png")));
        labelIcon = new ImageIcon(Objects.requireNonNull(GameWindow.class.getResource("/images/pencil_game_icon_16.png")));
    }
    
    private void configureWindow(){
        // JFrame extended into GameWindow. Inherits all JFrame.
        setTitle("Pencil Game");
        setSize(400, 400);
        setIconImage(frameIcon.getImage());
        getContentPane().setBackground(Color.BLACK);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
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
        JButton exitButton = new JButton();
        GridBagConstraints gbc = new GridBagConstraints();
        buttonLayout = new JPanel(new GridBagLayout());
        firstButton.addActionListener(_ -> newGame());
        firstButton.setText("START");
        firstButton.setFocusable(false);
        exitButton.addActionListener(_ -> System.exit(0));
        exitButton.setText("EXIT");
        exitButton.setFocusable(false);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5,5,5,5);
        buttonLayout.add(firstButton, gbc);
        buttonLayout.add(exitButton, gbc);
    }
    
    private void newGame(){
        titleLabel.setIcon(null);
        titleLabel.setText("<html><center>How many pencils would<br/>you like to use?</center></html>");
        firstButton.setText("RESTART");
    }
}