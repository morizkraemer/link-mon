package com.morizkraemer.gui;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    private static MenuBar instance;

    ConsoleWindow consoleWindow = ConsoleWindow.getInstance();
    DeviceManager deviceManager = DeviceManager.getInstance();
    private JMenu fileMenu, settingsMenu, viewMenu;
    private JMenuItem closeMenuItem, deviceManagerMenuItem, consoleMenuItem, preferencesMenuItem;

    public MenuBar() {
        fileMenu = new JMenu("File");
        settingsMenu = new JMenu("Settings");
        viewMenu = new JMenu("View");

        closeMenuItem = new JMenuItem("Close");
        closeMenuItem.addActionListener(e -> System.exit(0));

        deviceManagerMenuItem = new JMenuItem("Device Manager");
        deviceManagerMenuItem.addActionListener(e -> deviceManager.openDeviceManager());
        preferencesMenuItem = new JMenuItem("Preferences");
        consoleMenuItem = new JMenuItem("Console");

        fileMenu.add(closeMenuItem);
        viewMenu.add(deviceManagerMenuItem);
        settingsMenu.add(preferencesMenuItem);
        viewMenu.add(consoleMenuItem);
        consoleMenuItem.addActionListener(e -> {
            consoleWindow.openConsole();
        });

        add(fileMenu);
        add(settingsMenu);
        add(viewMenu);
    }

    public static MenuBar getInstance() {
        if (instance == null) {
                instance = new MenuBar();
        }
        return instance;
    }
}
