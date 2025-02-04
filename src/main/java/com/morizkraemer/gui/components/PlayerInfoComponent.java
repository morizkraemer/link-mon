package com.morizkraemer.gui.components;

import javax.swing.*;
import javax.swing.border.Border;

import org.deepsymmetry.beatlink.DeviceUpdate;

import com.morizkraemer.services.DeviceFinderService;

import java.awt.*;

public class PlayerInfoComponent extends JPanel {

    String deviceNumber;
    Boolean master;
    Boolean sync;
    String timeElapsed;
    String timeTotal;
    String key;
    String pitch;
    String oBpm;
    String bpm;

    public static class CustomPanel extends JPanel {
        public CustomPanel() {
            setBackground(Color.BLACK);
        }

        public CustomPanel(LayoutManager layout) {
            super(layout);
            setBackground(Color.BLACK);
        }
    }

    public PlayerInfoComponent(int playerN) {
        DeviceFinderService deviceFinder = DeviceFinderService.getInstance();

        Timer swingTimer = new Timer(2000, e -> {
            DeviceUpdate update = deviceFinder.getDeviceUpdate(playerN);
            deviceNumber = "" + update.getDeviceNumber();
            master = update.isTempoMaster();
            sync = update.isSynced();

        });

        swingTimer.start();
        setLayout(new GridLayout(3, 1)); // 3 rows
        setBackground(Color.BLACK);

        // First row: Device Number and Master Sync
        CustomPanel firstRow = new CustomPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.weightx = 1; // Device Number takes 2/3
        firstRow.add(new DeviceNumberField(playerN), gbc);

        gbc.gridx = 1;
        gbc.weightx = 3; // Master Sync takes 1/3
        firstRow.add(new MasterSyncField(), gbc);

        // Second row: Time
        CustomPanel secondRow = new CustomPanel(new BorderLayout());
        secondRow.add(new TimeField("0:00", "5:21"), BorderLayout.CENTER);

        // Third row: Key and BPM
        CustomPanel thirdRow = new CustomPanel(new GridBagLayout());

        gbc.gridx = 0;
        gbc.weightx = 1; // Key takes 1/4
        thirdRow.add(new KeyField("Key"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 3; // BPM takes 3/4
        thirdRow.add(new BpmField("%", "origBPM", "BPM"), gbc);

        // Add rows to the main panel
        add(firstRow);
        add(secondRow);
        add(thirdRow);
    }
}

// ----------------------------------
// Device Number Field
// ----------------------------------
class DeviceNumberField extends PlayerInfoComponent.CustomPanel {
    public DeviceNumberField(int playerN) {
        setLayout(new BorderLayout());
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        JLabel deviceLabel = new JLabel(String.valueOf(playerN), SwingConstants.CENTER);
        setBorder(border);
        add(deviceLabel, BorderLayout.CENTER);
    }
}

// ----------------------------------
// Master Sync Field
// ----------------------------------
class MasterSyncField extends PlayerInfoComponent.CustomPanel {
    public MasterSyncField() {
        setLayout(new GridLayout(2, 1)); // Stack Master and Sync
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        JLabel masterLabel = new JLabel("Master", SwingConstants.CENTER);
        JLabel syncLabel = new JLabel("Sync", SwingConstants.CENTER);
        setBorder(border);
        add(masterLabel);
        add(syncLabel);
    }
}

// ----------------------------------
// Time Field
// ----------------------------------
class TimeField extends PlayerInfoComponent.CustomPanel {
    public TimeField(String playTime, String totalTime) {
        setLayout(new BorderLayout());
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        JLabel timeLabel = new JLabel(playTime + "   " + totalTime, SwingConstants.CENTER);
        setBorder(border);
        add(timeLabel, BorderLayout.CENTER);
    }
}

// ----------------------------------
// Key Field
// ----------------------------------
class KeyField extends PlayerInfoComponent.CustomPanel {
    public KeyField(String key) {
        setLayout(new BorderLayout());
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        JLabel keyLabel = new JLabel(key, SwingConstants.CENTER);
        setBorder(border);
        add(keyLabel, BorderLayout.CENTER);
    }
}

// ----------------------------------
// BPM Field
// ----------------------------------
class BpmField extends PlayerInfoComponent.CustomPanel {
    public BpmField(String percent, String origBpm, String bpm) {
        setLayout(new GridBagLayout());
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        // Left side (Stack % and origBPM vertically)
        PlayerInfoComponent.CustomPanel bpmLeft = new PlayerInfoComponent.CustomPanel(new GridLayout(2, 1));
        JLabel percentLabel = new JLabel(percent, SwingConstants.CENTER);
        JLabel origBpmLabel = new JLabel(origBpm, SwingConstants.CENTER);
        bpmLeft.add(percentLabel);
        bpmLeft.add(origBpmLabel);

        // Right side (BPM label)
        JLabel bpmLabel = new JLabel(bpm, SwingConstants.CENTER);

        // Add left and right components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        add(bpmLeft, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 3;
        add(bpmLabel, gbc);

        setBorder(border);
    }
}
//
// };
// }
