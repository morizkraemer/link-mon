package com.morizkraemer.gui;

import java.awt.*;
import java.util.Map;

import javax.swing.*;

import org.deepsymmetry.beatlink.DeviceAnnouncement;
import org.deepsymmetry.beatlink.data.WaveformPreviewComponent;

import com.morizkraemer.AppConfig;
import com.morizkraemer.gui.components.playerinfo.PlayerInfoComponent;
import com.morizkraemer.gui.components.WaveFormComponent;
import com.morizkraemer.state.PlayerState;

public class WaveFormPreviewPanel extends JPanel {
    ConsoleWindow consoleWindow = ConsoleWindow.getInstance();
    PlayerState playerState = PlayerState.getInstance();
    int foundPlayersVersion = -1;

    public WaveFormPreviewPanel(MainWindow mainWindow, String panelName) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(AppConfig.Colors.BACKGROUND_MEDIUM);
        setName(panelName);

        Timer swingTimer = new Timer(3000, (e) -> {
            playerUpdate(mainWindow, panelName);
        });

        swingTimer.start();

    }

    private void playerUpdate(MainWindow mainWindow, String panelName) {
        int fpv = playerState.getFoundPlayersVersion();
        if (fpv > foundPlayersVersion) {
            Map<Integer, DeviceAnnouncement> foundPlayers = playerState.getFoundPlayers();
            foundPlayersVersion = fpv;
            consoleWindow.appendToConsole("WaveFormComponent", "Updated");
            removeAll();
            foundPlayers.forEach((playerN, deviceAnnouncement) -> {
                addPlayer(deviceAnnouncement);
            });
            revalidate();
            repaint();
            //if (mainWindow.getActivePanel() != panelName && !foundPlayers.isEmpty()) {
            //    mainWindow.switchToPanel(panelName);
            //}
        }

    }

    public void addPlayer(DeviceAnnouncement player) {
        int playerN = player.getDeviceNumber();

        JPanel playerComponent = new JPanel(new BorderLayout());
        WaveformPreviewComponent waveFormPreviewComponent = new WaveformPreviewComponent(playerN);
        //PlayerInfoComponent playerInfo = new PlayerInfoComponent(playerN);
        //playerInfo.setPreferredSize(new Dimension(200, 200));

        playerComponent.add(waveFormPreviewComponent, BorderLayout.CENTER);
        //playerComponent.add(playerInfo, BorderLayout.WEST);
        playerComponent.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        waveFormPreviewComponent.setName("Player " + playerN);

        add(playerComponent);
    }

}
