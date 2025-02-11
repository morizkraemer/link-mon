package com.morizkraemer.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;

import org.deepsymmetry.beatlink.DeviceUpdate;
import org.deepsymmetry.beatlink.data.TrackMetadata;

import com.morizkraemer.gui.ConsoleWindow;
import com.morizkraemer.gui.components.fragments.CustomComponents.CustomPanel;
import com.morizkraemer.gui.components.fragments.CustomComponents.CustomLabel;
import com.morizkraemer.state.PlayerState;

public class PlayerInfoComponent extends JPanel {

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

    public static String formatMsToTime(long ms) {
        long s = ms / 1000;
        return formatSecondsToTime((int) s);
    }

    public static String formatIntToFloat(int number) {
        float result = number / 1000f;
        return String.format("%,.1f", result);
    }

    ConsoleWindow consoleWindow = ConsoleWindow.getInstance();

    private DeviceNumberField deviceNumberField;

    private MasterSyncField masterSyncField;

    private TimeField timeField;

    private KeyField keyField;

    private BpmField bpmField;
    public PlayerInfoComponent(int playerN) {
        PlayerState playerState = PlayerState.getInstance();
        Border border = BorderFactory.createLineBorder(Color.WHITE);

        Timer swingTimer = new Timer(2000, e -> {
            DeviceUpdate deviceUpdate = playerState.getDeviceUpdate(playerN);
            TrackMetadata trackMetadata = playerState.getTrackUpdate(playerN);
            if (trackMetadata != null && deviceUpdate != null) {
                deviceNumberField.updateDeviceNumber(deviceUpdate.getDeviceNumber());
                masterSyncField.updateMasterSyncFiel(deviceUpdate.isTempoMaster(), deviceUpdate.isSynced());
                timeField.updateTime("0:00", formatSecondsToTime(trackMetadata.getDuration()));
                keyField.updateKey(trackMetadata.getKey().label);
                double pitchPercent = Util.pitchToPercentage(deviceUpdate.getPitch());
                bpmField.updateBpmPanel(
                        String.format("%.2f", pitchPercent) + "%",
                        formatIntToFloat(trackMetadata.getTempo() * 10),
                        String.format("%.1f", deviceUpdate.getEffectiveTempo()));
            }
            revalidate();
            repaint();
        });

        setLayout(new GridLayout(3, 1));
        setBackground(Color.BLACK);
        setBorder(border);

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
    private final Border b = BorderFactory.createMatteBorder(0, 0, 1, 1, Color.WHITE);

    public DeviceNumberField(int playerN) {
        setLayout(new BorderLayout());
        deviceLabel = new CustomLabel(String.valueOf(playerN), SwingConstants.CENTER);
        deviceLabel.setFont(AppConfig.Fonts.TITLE_FONT);
        add(deviceLabel, BorderLayout.CENTER);
        setBorder(b);
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
    private final Border b = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE);

    public MasterSyncField(Boolean master, Boolean sync) {
        setLayout(new GridLayout(2, 1)); // Stack Master and Sync
        masterLabel = new CustomLabel("Master", SwingConstants.CENTER);
        syncLabel = new CustomLabel("Sync", SwingConstants.CENTER);
        add(masterLabel);
        add(syncLabel);
        setBorder(b);
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
    private final Border b = BorderFactory.createMatteBorder(0, 1, 0, 1, Color.WHITE);

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
        bpmPanel.setBorder(b);

        // Right side (BPM label)
        bpmLabel = new CustomLabel(bpm, SwingConstants.CENTER);
        bpmLabel.setFont(AppConfig.Fonts.SUBTITLE_FONT);

        // Add left and right components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2;
        add(bpmPanel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 3;
        add(bpmLabel, gbc);

    }

    public void updateBpmPanel(String pitch, String oBpm, String bpm) {
        bpmLabel.setText(bpm);
        pitchLabel.setText(pitch);
        origBpmLabel.setText(oBpm);
    }

}
