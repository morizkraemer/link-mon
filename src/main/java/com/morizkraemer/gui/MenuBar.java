package com.morizkraemer.gui;

import javax.swing.*;

//import com.morizkraemer.test.TestComponent;

public class MenuBar extends JMenuBar {


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

        //JMenuItem testItem = new JMenuItem("Test");
        //testItem.addActionListener(e -> {
        //    new TestComponent();
        //});
        //viewMenu.add(testItem);

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

}
