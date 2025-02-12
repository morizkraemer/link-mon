package com.morizkraemer.gui;

import java.awt.*;
import javax.swing.*;

import com.morizkraemer.AppConfig;
import com.morizkraemer.gui.components.fragments.LoadingAnimation;
import com.morizkraemer.services.DeviceFinderService;;

public class SplashScreen extends JPanel {
    StatusBar statusBar = StatusBar.getInstance();
    DeviceFinderService deviceFinderService = DeviceFinderService.getInstance();

    public SplashScreen() {
        setLayout(new GridBagLayout());

        JLabel welcomeText = new JLabel("Welcome to Link-Mon", SwingConstants.CENTER);
        welcomeText.setFont(AppConfig.Fonts.H1);
        welcomeText.setForeground(AppConfig.Colors.ACCENT_RED);

        JLabel discoverDevicesLabel = new JLabel("Discovering Devices", SwingConstants.CENTER);
        discoverDevicesLabel.setFont(AppConfig.Fonts.H2);
        discoverDevicesLabel.setForeground(AppConfig.Colors.ACCENT_RED);
        //discoverDevicesLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        LoadingAnimation loadingAnimation = new LoadingAnimation(AppConfig.Colors.ACCENT_RED);

        // discoverDevicesLabel.addMouseListener(new java.awt.event.MouseAdapter() {
        // @Override
        // public void mouseClicked(java.awt.event.MouseEvent e) {
        // System.out.println("Discover Devices clicked!");
        // deviceFinderService.startServices();
        //
        // }
        //
        // @Override
        // public void mouseEntered(java.awt.event.MouseEvent e) {
        // discoverDevicesLabel.setForeground(AppConfig.Colors.ACCENT_RED_MUTED);
        // }
        //
        // @Override
        // public void mouseExited(java.awt.event.MouseEvent e) {
        // discoverDevicesLabel.setForeground(AppConfig.Colors.ACCENT_RED);
        // }
        // });

        // Layout constraints for centering both labels
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Single column
        gbc.gridy = 0; // Row 0 for the welcome text
        gbc.insets = new Insets(10, 0, 10, 0); // Add vertical spacing between labels
        gbc.anchor = GridBagConstraints.CENTER;

        add(welcomeText, gbc);

        // Row 1 for the discover devices label
        gbc.gridy = 1; // Move to the next row
        add(discoverDevicesLabel, gbc);

        gbc.gridy = 2;
        add(loadingAnimation, gbc);

        setBackground(AppConfig.Colors.BACKGROUND_DARK);
    }

}
