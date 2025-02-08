package com.morizkraemer.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;

import org.deepsymmetry.beatlink.DeviceUpdate;
import org.deepsymmetry.beatlink.data.TrackMetadata;

import com.morizkraemer.gui.ConsoleWindow;
import com.morizkraemer.gui.components.PlayerInfoComponent.CustomLabel;
import com.morizkraemer.gui.components.PlayerInfoComponent.CustomPanel;
import com.morizkraemer.state.PlayerState;

public class PlayerInfoComponent extends JPanel {
    ConsoleWindow consoleWindow = ConsoleWindow.getInstance();

    private DeviceNumberField deviceNumberField;
    private MasterSyncField masterSyncField;
    private TimeField timeField;
    private KeyField keyField;
    private BpmField bpmField;

    public static String formatSecondsToTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;

        if (hours > 0) {
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }

    public static String formatIntToFloat(int number) {
        float result = number / 1000f;
        return String.format("%,.3f", result);
    }

    public static class CustomLabel extends JLabel {
        public CustomLabel(String text, int horizontalALignment) {
            super(text, horizontalALignment);
            setForeground(Color.WHITE);
        }
    }

    public static class CustomPanel extends JPanel {
        Border border = BorderFactory.createLineBorder(Color.WHITE);

        public CustomPanel() {
            setBackground(Color.BLACK);
            setBorder(border);
        }

        public CustomPanel(LayoutManager layout) {
            super(layout);
            setBorder(border);
            setBackground(Color.BLACK);

        }
    }

    public PlayerInfoComponent(int playerN) {
        PlayerState playerState = PlayerState.getInstance();

        Timer swingTimer = new Timer(2000, e -> {
            DeviceUpdate deviceUpdate = playerState.getDeviceUpdate(playerN);
            TrackMetadata trackMetadata = playerState.getTrackUpdate(playerN);
            if (trackMetadata != null && deviceUpdate != null) {
                consoleWindow.appendToConsole("component", trackMetadata);
                deviceNumberField.updateDeviceNumber(deviceUpdate.getDeviceNumber());
                masterSyncField.updateMasterSyncFiel(deviceUpdate.isTempoMaster(), deviceUpdate.isSynced());
                timeField.updateTime("0:00", formatSecondsToTime(trackMetadata.getDuration()));
                keyField.updateKey(trackMetadata.getKey().label);
                bpmField.updateBpmPanel(
                        deviceUpdate.getPitch() + "%",
                        formatIntToFloat(trackMetadata.getTempo()),
                        String.format("%.1f", deviceUpdate.getEffectiveTempo()));
            }
            revalidate();
            repaint();
        });

        setLayout(new GridLayout(3, 1));
        setBackground(Color.BLACK);

        deviceNumberField = new DeviceNumberField(playerN);
        masterSyncField = new MasterSyncField(null, null);
        timeField = new TimeField("0:00", null);
        keyField = new KeyField(null);
        bpmField = new BpmField(null, null, null);

        swingTimer.start();

        // First row: Device Number and Master Sync
        CustomPanel firstRow = new CustomPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.weightx = 1; // Device Number takes 2/3
        firstRow.add(deviceNumberField, gbc);

        gbc.gridx = 1;
        gbc.weightx = 3; // Master Sync takes 1/3
        firstRow.add(masterSyncField, gbc);

        // Second row: Time
        CustomPanel secondRow = new CustomPanel(new BorderLayout());
        secondRow.add(timeField, BorderLayout.CENTER);

        // Third row: Key and BPM
        CustomPanel thirdRow = new CustomPanel(new GridBagLayout());

        gbc.gridx = 0;
        gbc.weightx = 1; // Key takes 1/4
        thirdRow.add(keyField, gbc);

        gbc.gridx = 1;
        gbc.weightx = 3; // BPM takes 3/4
        thirdRow.add(bpmField, gbc);

        // Add rows to the main panel
        add(firstRow);
        add(secondRow);
        add(thirdRow);
    }
}

// ----------------------------------
// Device Number Field
// ----------------------------------
class DeviceNumberField extends CustomPanel {
    private CustomLabel deviceLabel;

    public DeviceNumberField(int playerN) {
        setLayout(new BorderLayout());
        deviceLabel = new CustomLabel(String.valueOf(playerN), SwingConstants.CENTER);
        add(deviceLabel, BorderLayout.CENTER);
    }

    public void updateDeviceNumber(int deviceNumber) {
        deviceLabel.setText(String.valueOf(deviceNumber));
    }
}

// ----------------------------------
// Master Sync Field
// ----------------------------------
class MasterSyncField extends CustomPanel {
    CustomLabel masterLabel;
    CustomLabel syncLabel;

    public MasterSyncField(Boolean master, Boolean sync) {
        setLayout(new GridLayout(2, 1)); // Stack Master and Sync
        masterLabel = new CustomLabel("Master", SwingConstants.CENTER);
        syncLabel = new CustomLabel("Sync", SwingConstants.CENTER);
        add(masterLabel);
        add(syncLabel);
    }

    public void updateMasterSyncFiel(Boolean master, Boolean sync) {
        if (master == true) {
            masterLabel.setForeground(Color.ORANGE);
        } else if (master == false) {
            masterLabel.setForeground(Color.GRAY);
        }

        if (sync == true) {
            syncLabel.setForeground(Color.GREEN);
        } else if (sync == false) {
            syncLabel.setForeground(Color.GRAY);
        }
    }
}

// ----------------------------------
// Time Field
// ----------------------------------
class TimeField extends CustomPanel {
    CustomLabel elapsedTime;
    CustomLabel totalTime;

    public TimeField(String playT, String totalT) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        elapsedTime = new CustomLabel(playT, SwingConstants.CENTER);
        add(elapsedTime);
        totalTime = new CustomLabel(totalT, SwingConstants.CENTER);
        add(totalTime);
    }

    public void updateTime(String playT, String totalT) {
        elapsedTime.setText(playT);
        totalTime.setText(totalT);
    }

}

// ----------------------------------
// Key Field
// ----------------------------------
class KeyField extends CustomPanel {
    CustomLabel keyLabel;

    public KeyField(String key) {
        setLayout(new BorderLayout());
        keyLabel = new CustomLabel(key, SwingConstants.CENTER);
        add(keyLabel, BorderLayout.CENTER);
    }

    public void updateKey(String key) {
        keyLabel.setText(key);
    }
}

// ----------------------------------
// BPM Field
// ----------------------------------
class BpmField extends CustomPanel {

    private CustomLabel bpmLabel;
    private CustomLabel pitchLabel;
    private CustomLabel origBpmLabel;

    public BpmField(String percent, String origBpm, String bpm) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        // Left side (Stack % and origBPM vertically)
        CustomPanel bpmPanel = new CustomPanel(new GridLayout(2, 1));
        pitchLabel = new CustomLabel(percent, SwingConstants.CENTER);
        origBpmLabel = new CustomLabel(origBpm, SwingConstants.CENTER);
        bpmPanel.add(pitchLabel);
        bpmPanel.add(origBpmLabel);

        // Right side (BPM label)
        bpmLabel = new CustomLabel(bpm, SwingConstants.CENTER);

        // Add left and right components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        add(bpmLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 3;
        add(bpmLabel, gbc);

    }

    public void updateBpmPanel(String bpm, String pitch, String oBpm) {
        bpmLabel.setText(bpm);
        pitchLabel.setText(pitch);
        origBpmLabel.setText(oBpm);
    }

}
