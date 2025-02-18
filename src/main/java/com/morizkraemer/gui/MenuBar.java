package com.morizkraemer.gui;

import javax.swing.*;

//import com.morizkraemer.test.TestComponent;

public class MenuBar extends JMenuBar {

    ConsoleWindow consoleWindow = ConsoleWindow.getInstance();
    DeviceManager deviceManager = DeviceManager.getInstance();
    private JMenu fileMenu, settingsMenu, viewMenu;
    private JMenuItem closeMenuItem, deviceManagerMenuItem, preferencesMenuItem;
    private JCheckBoxMenuItem showWaveformPanel, consoleMenuItem;

    public MenuBar(WaveFormPanel waveFormPanel) {
        fileMenu = new JMenu("File");
        settingsMenu = new JMenu("Settings");
        viewMenu = new JMenu("View");

        closeMenuItem = new JMenuItem("Close");
        closeMenuItem.addActionListener(e -> System.exit(0));

        // JMenuItem testItem = new JMenuItem("Test");
        // testItem.addActionListener(e -> {
        // new TestComponent();
        // });
        // viewMenu.add(testItem);
        //

        showWaveformPanel = new JCheckBoxMenuItem("Show Waveforms", true);

        showWaveformPanel.addActionListener(e -> {
            Boolean isSelected = showWaveformPanel.isSelected();
            SwingUtilities.invokeLater(() -> {
                waveFormPanel.setVisibilty(isSelected);
            }

            );
        });

        deviceManagerMenuItem = new JMenuItem("Device Manager");
        deviceManagerMenuItem.addActionListener(e -> deviceManager.openDeviceManager());
        preferencesMenuItem = new JMenuItem("Preferences");
        consoleMenuItem = new JCheckBoxMenuItem("Show Console", false);

        fileMenu.add(closeMenuItem);
        viewMenu.add(deviceManagerMenuItem);
        viewMenu.add(consoleMenuItem);
        viewMenu.add(showWaveformPanel);
        settingsMenu.add(preferencesMenuItem);
        consoleMenuItem.addActionListener(e -> {
            Boolean isSelected = consoleMenuItem.isSelected();
            consoleWindow.setVisibility(isSelected);
        });

        add(fileMenu);
        add(settingsMenu);
        add(viewMenu);
    }

}
