package com.morizkraemer.gui;

import java.awt.Container;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.deepsymmetry.beatlink.DeviceAnnouncement;

import com.morizkraemer.AppConfig;
import com.morizkraemer.AppConfig.Colors;
import com.morizkraemer.gui.components.PlayerWaveformComponent;
import com.morizkraemer.state.PlayerState;

public class WaveFormPanel extends JPanel {
    ConsoleWindow consoleWindow = ConsoleWindow.getInstance();
    PlayerState playerState = PlayerState.getInstance();

    int foundPlayersVersion = -1;

    private Map<Integer, PlayerWaveformComponent> playerComponents = new HashMap<>();

    public WaveFormPanel(MainWindow mainWindow, String panelName) {
        setBorder(BorderFactory.createLineBorder(Colors.PURE_BLACK, 5));
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
            consoleWindow.appendToConsole("tihs", "updated");
            removeAll();
            foundPlayers.forEach((playerN, deviceAnnouncement) -> {
                addPlayer(deviceAnnouncement);
            });
            revalidate();
            repaint();
            if (mainWindow.getActivePanel() != panelName && !foundPlayers.isEmpty()) {
                mainWindow.switchToPanel(panelName);
            }
        }

    }

    private void addPlayer(DeviceAnnouncement player) {
        int playerN = player.getDeviceNumber();

        PlayerWaveformComponent playerComponent = new PlayerWaveformComponent(playerN);

        playerComponents.put(playerN, playerComponent);

        add(playerComponent);
    }

    public void resizeWaveformComponent(int newSize) {
        playerComponents.forEach((playerN, comp) -> {
            comp.resizeComponent(newSize);
        });
        Container workspacePanel = getParent();
        workspacePanel.revalidate();
        workspacePanel.repaint();
    }

    public void setVisibilty(Boolean visibility) {
        setVisible(visibility);
    }

}
