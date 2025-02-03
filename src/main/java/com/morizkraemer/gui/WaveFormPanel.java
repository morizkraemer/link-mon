package com.morizkraemer.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import org.deepsymmetry.beatlink.DeviceAnnouncement;
import org.deepsymmetry.beatlink.DeviceUpdate;
import org.deepsymmetry.beatlink.data.WaveformDetailComponent;

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

    private class PlayerInfoComponent extends JPanel {
        public PlayerInfoComponent(int playerN) {
            Border border = BorderFactory.createLineBorder(Color.BLACK, 4);
            setBorder(border);
            setPreferredSize(new Dimension(200, 0));

            JLabel bpmLabel = new JLabel();
            JLabel playerNumber = new JLabel();
            playerNumber.setText("" + playerN);

            Timer swingTimer = new Timer(2000, e -> {
                DeviceUpdate update = deviceFinder.getDeviceUpdate(playerN);
                double bpm = update.getEffectiveTempo();
                bpmLabel.setText(String.format("%.1f", bpm));

            });

            swingTimer.start();

            add(playerNumber);
            add(bpmLabel);
        };
    }

    private class WaveFormComponent extends WaveformDetailComponent {
        public WaveFormComponent(int player) {
            super(player);
            setDoubleBuffered(true);
        }
    }

}
