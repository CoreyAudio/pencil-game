package com.coreyaudio.pencilgame.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GameWindow extends JFrame {
    
    private ImageIcon frameIcon;
    private ImageIcon labelIcon;
    private JLabel titleLabel;
    
    public GameWindow(){
        SwingUtilities.invokeLater(this::buildUI);
    }
    
    private void buildUI(){
        loadIcons();
        configureWindow();
        configureLabel();
        
        add(titleLabel, BorderLayout.CENTER);
        setVisible(true);
    }
    
    private void loadIcons(){
        frameIcon = new ImageIcon(Objects.requireNonNull(GameWindow.class.getResource("/images/pencil_game_icon.png")));
        labelIcon = new ImageIcon(Objects.requireNonNull(GameWindow.class.getResource("/images/pencil_game_icon_16.png")));
    }
    
    private void configureWindow(){
        setTitle("Pencil Game");
        setSize(400, 400);
        setIconImage(frameIcon.getImage());
        getContentPane().setBackground(Color.BLACK);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
    }
    
    private void configureLabel(){
        titleLabel = new JLabel("<html>WELCOME TO THE<br/>&emsp;&nbsp;PENCIL GAME!</html>", SwingConstants.CENTER);
        titleLabel.setIcon(labelIcon);
        titleLabel.setHorizontalTextPosition(JLabel.CENTER);
        titleLabel.setVerticalTextPosition(JLabel.BOTTOM);
        titleLabel.setForeground(Color.GREEN);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
    }
}