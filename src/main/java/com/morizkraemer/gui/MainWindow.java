package com.morizkraemer.gui;

import javax.swing.*;

import com.morizkraemer.services.DeviceFinderService;

import java.awt.*;

public class MainWindow {

    private static MainWindow instance;

    DeviceFinderService deviceFinder = DeviceFinderService.getInstance();
    ConsoleWindow consoleWindow = ConsoleWindow.getInstance();
    MenuBar menuBar = MenuBar.getInstance();
    StatusBar statusBar = StatusBar.getInstance();

    private JFrame mainWindowFrame;
    private JPanel cardsPanel;
    private String activePanelName;

    private void setupWindowLayout() {
        JPanel layoutPanel;
        SwingUtilities.invokeLater(() -> consoleWindow.openConsole());

        layoutPanel = new JPanel(new BorderLayout());
        cardsPanel = new JPanel(new CardLayout());

        JPanel splashScreenPanel = new SplashScreen();
        cardsPanel.add(splashScreenPanel, "SplashScreen");

        WaveFormPanel waveFormPanel = new WaveFormPanel(this, "WaveFormPanel");
        cardsPanel.add(waveFormPanel, "WaveFormPanel");

        layoutPanel.add(cardsPanel, BorderLayout.CENTER);
        layoutPanel.add(statusBar, BorderLayout.PAGE_END);

        mainWindowFrame.setJMenuBar(menuBar);
        mainWindowFrame.add(layoutPanel);

    }

    public void switchToPanel(String panelName) {
        activePanelName = panelName;
        CardLayout layout = (CardLayout) cardsPanel.getLayout();
        consoleWindow.appendToConsole("Main", panelName);
        SwingUtilities.invokeLater(() -> layout.show(cardsPanel, panelName));
    }

    public String getActivePanel() {
        return activePanelName;
    }

    public MainWindow() {
        mainWindowFrame = new JFrame("Link-Mon");
        mainWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindowFrame.setSize(1200, 900);

        setupWindowLayout();
    }

    public void openMainWindow() {
        mainWindowFrame.setVisible(true);
    }

    public static MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }
}
