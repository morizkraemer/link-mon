package com.morizkraemer.gui;

import java.awt.*;
import javax.swing.*;

import org.deepsymmetry.beatlink.DeviceAnnouncement;

import com.morizkraemer.gui.components.PlayerInfoComponent;
import com.morizkraemer.gui.components.WaveFormComponent;
import com.morizkraemer.state.PlayerState;

public class WaveFormPanel extends JPanel {
    ConsoleWindow consoleWindow = ConsoleWindow.getInstance();
    PlayerState playerState = PlayerState.getInstance();

    public WaveFormPanel(MainWindow mainWindow, String panelName) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setName(panelName);

        Timer swingTimer = new Timer(3000, (e) -> {
            playerUpdate(mainWindow, panelName);
        });

        swingTimer.start();

    }

    private void playerUpdate(MainWindow mainWindow, String panelName) {
        consoleWindow.appendToConsole("this", "yeaaa");
        if (playerState.foundPlayersChanged) {
            removeAll();
            consoleWindow.appendToConsole("WaveFormComponent", "Updated");
            playerState.getFoundPlayers().forEach((playerN, deviceAnnouncement) -> {
                addPlayer(deviceAnnouncement);
            });
            revalidate();
            repaint();
            if (mainWindow.getActivePanel() != panelName) {
                consoleWindow.appendToConsole("this", "switched");
                mainWindow.switchToPanel(panelName);
            }
        }

    }

    public void addPlayer(DeviceAnnouncement player) {
        int playerN = player.getDeviceNumber();

        JPanel playerComponent = new JPanel(new BorderLayout());
        WaveFormComponent waveFormComponent = new WaveFormComponent(player.getDeviceNumber());
        PlayerInfoComponent playerInfo = new PlayerInfoComponent(playerN);
        playerInfo.setPreferredSize(new Dimension(200, 200));

        playerComponent.add(waveFormComponent, BorderLayout.CENTER);
        playerComponent.add(playerInfo, BorderLayout.WEST);
        playerComponent.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        waveFormComponent.setName("Player " + player.getDeviceNumber());

        add(playerComponent);
    }



}
