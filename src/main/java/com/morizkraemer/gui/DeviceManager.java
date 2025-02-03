package com.morizkraemer.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class DeviceManager extends JFrame {
    private static DeviceManager instance;

    private final ConsoleWindow consoleWindow = ConsoleWindow.getInstance();
    //private final DeviceFinderService deviceFinderService = DeviceFinderService.getInstance();

    private JPanel panel;

    private DeviceManager() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setTitle("Device Manager");

        createLayout();
    }

    private void createLayout() {
        panel = new JPanel();

        JButton startFinder = new JButton("Start DeviceFinder");
        startFinder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //deviceFinderService.startFinder();
                consoleWindow.appendToConsole("DeviceManager", "Started DeviceFinderService");
            }
        });

        JButton stopFinder = new JButton("Stop DeviceFinder");
        stopFinder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //deviceFinderService.stopFinder();
                consoleWindow.appendToConsole("DeviceManager", "Stopped DeviceFinderService");
            }
        });

        panel.add(startFinder);
        panel.add(stopFinder);

        add(panel);
    }

    public void openDeviceManager() {
        setVisible(true);
    }

    public void closeDeviceManager() {
        setVisible(false);
    }

    public static DeviceManager getInstance() {
        if (instance == null) {
            instance = new DeviceManager();
        }
        return instance;
    }
}
