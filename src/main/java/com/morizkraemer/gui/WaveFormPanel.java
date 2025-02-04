package com.morizkraemer.gui;

import java.awt.*;
import javax.swing.*;

import org.deepsymmetry.beatlink.DeviceAnnouncement;

import com.morizkraemer.gui.components.PlayerInfoComponent;
import com.morizkraemer.gui.components.WaveFormComponent;
import com.morizkraemer.services.DeviceFinderService;

public class WaveFormPanel extends JPanel {
    ConsoleWindow consoleWindow = ConsoleWindow.getInstance();
    DeviceFinderService deviceFinder = DeviceFinderService.getInstance();

    public WaveFormPanel(MainWindow mainWindow, String panelName) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setName(panelName);
        deviceFinder.setDeviceFinderUpdateListener((players) -> {
            removeAll();
            consoleWindow.appendToConsole("WaveFormComponent", "Updated");
            for (DeviceAnnouncement player : players) {
                addPlayer(player);
            }
            revalidate();
            repaint();
            mainWindow.switchToPanel(panelName);
        });
    }

    public void addPlayer(DeviceAnnouncement player) {
        int playerN = player.getDeviceNumber();

        JPanel playerComponent = new JPanel(new BorderLayout());
        WaveFormComponent waveFormComponent = new WaveFormComponent(player.getDeviceNumber());
        PlayerInfoComponent playerInfo = new PlayerInfoComponent(playerN);

        playerComponent.add(waveFormComponent, BorderLayout.CENTER);
        playerComponent.add(playerInfo, BorderLayout.WEST);
        playerComponent.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200)); // Forces full width

        waveFormComponent.setName("Player " + player.getDeviceNumber());

        add(playerComponent);
    }



}
