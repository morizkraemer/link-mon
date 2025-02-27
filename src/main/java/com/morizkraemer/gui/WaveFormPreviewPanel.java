package com.morizkraemer.gui;

import java.awt.Container;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.deepsymmetry.beatlink.DeviceAnnouncement;

import com.morizkraemer.AppConfig;
import com.morizkraemer.gui.components.PlayerPreviewComponent;
import com.morizkraemer.state.PlayerState;

public class WaveFormPreviewPanel extends JPanel {
    ConsoleWindow consoleWindow = ConsoleWindow.getInstance();
    PlayerState playerState = PlayerState.getInstance();
    int foundPlayersVersion = -1;

    private Map<Integer, PlayerPreviewComponent> playerPreviewComponents = new HashMap<>();

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

        PlayerPreviewComponent playerPreviewComponent = new PlayerPreviewComponent(playerN);
        playerPreviewComponents.put(playerN, playerPreviewComponent);

        add(playerPreviewComponent);
    }


    public void resizeWaveformComponent(int newSize) {
        Container workspacePanel = getParent();
        playerPreviewComponents.forEach((playerN, comp) -> {
            comp.resizeComponent(newSize);
        });
        revalidate();
        workspacePanel.revalidate();
        workspacePanel.repaint();
    }

    public void setVisibility(Boolean visibility) {
        setVisible(visibility);
    }

}
