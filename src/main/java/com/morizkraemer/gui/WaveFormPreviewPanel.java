package com.morizkraemer.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.deepsymmetry.beatlink.DeviceAnnouncement;
import org.deepsymmetry.beatlink.data.WaveformPreviewComponent;

import com.morizkraemer.AppConfig;
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
        }

    }

    public void addPlayer(DeviceAnnouncement player) {
        int playerN = player.getDeviceNumber();

        JPanel playerComponent = new JPanel(new BorderLayout());
        WaveformPreviewComponent waveFormPreviewComponent = new WaveformPreviewComponent(playerN);

        playerComponent.add(waveFormPreviewComponent, BorderLayout.CENTER);
        playerComponent.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        waveFormPreviewComponent.setName("Player " + playerN);

        add(playerComponent);
    }

    public void setVisibility(Boolean visibility) {
        setVisible(visibility);
    }

}
