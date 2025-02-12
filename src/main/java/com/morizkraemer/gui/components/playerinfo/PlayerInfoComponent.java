package com.morizkraemer.gui.components.playerinfo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;

import org.deepsymmetry.beatlink.DeviceUpdate;
import org.deepsymmetry.beatlink.Util;
import org.deepsymmetry.beatlink.data.TrackMetadata;
import org.deepsymmetry.beatlink.data.TrackPositionUpdate;

import com.morizkraemer.AppConfig;
import com.morizkraemer.gui.ConsoleWindow;
import com.morizkraemer.gui.components.fragments.CustomComponents.CustomLabel;
import com.morizkraemer.gui.components.fragments.CustomComponents.CustomPanel;
import com.morizkraemer.state.PlayerState;
import com.morizkraemer.utils.CamelotKeys;
import com.morizkraemer.utils.Helpers;


public class PlayerInfoComponent extends JPanel {


    ConsoleWindow consoleWindow = ConsoleWindow.getInstance();

    private DeviceNumberField deviceNumberField;

    private MasterSyncField masterSyncField;

    private TimeField timeField;

    private KeyField keyField;

    private BpmField bpmField;

    public PlayerInfoComponent(int playerN) {
        PlayerState playerState = PlayerState.getInstance();
        Border border = BorderFactory.createLineBorder(Color.WHITE);

        Timer swingTimer = new Timer(1000, e -> {
            DeviceUpdate deviceUpdate = playerState.getDeviceUpdate(playerN);
            TrackMetadata trackMetadata = playerState.getTrackUpdate(playerN);
            TrackPositionUpdate trackPositionUpdate = playerState.getTrackPositionUpdate(playerN);
            if (trackMetadata != null && deviceUpdate != null) {
                deviceNumberField.updateDeviceNumber(deviceUpdate);
                masterSyncField.updateMasterSyncFiel(deviceUpdate);
                timeField.updateTime(trackPositionUpdate, trackMetadata);
                keyField.updateKey(trackMetadata);
                bpmField.updateBpmPanel(trackMetadata, deviceUpdate);
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
        deviceLabel.setFont(AppConfig.Fonts.H1);
        add(deviceLabel, BorderLayout.CENTER);
        setBorder(b);
    }

    public void updateDeviceNumber(DeviceUpdate deviceUpdate) {
        int playerN = deviceUpdate.getDeviceNumber();
        deviceLabel.setText(String.valueOf(playerN));
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
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Stack Master and Sync
        masterLabel = new CustomLabel("Master", SwingConstants.CENTER);
        masterLabel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        masterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        masterLabel.setFont(AppConfig.Fonts.H3);
        syncLabel = new CustomLabel("Sync", SwingConstants.CENTER);
        syncLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        syncLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        syncLabel.setFont(AppConfig.Fonts.H3);
        add(masterLabel);
        add(Box.createVerticalGlue());
        add(syncLabel);
        setBorder(b);
    }

    public void updateMasterSyncFiel(DeviceUpdate deviceUpdate) {
        Boolean master = deviceUpdate.isTempoMaster();
        Boolean sync = deviceUpdate.isSynced();
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

// ----------------------------------
// Key Field
// ----------------------------------
class KeyField extends CustomPanel {
    CustomLabel keyLabel;

    public KeyField(String key) {
        setLayout(new BorderLayout());
        keyLabel = new CustomLabel(key, SwingConstants.CENTER);
        keyLabel.setFont(AppConfig.Fonts.H2);
        if (key != null) {
            keyLabel.setForeground(CamelotKeys.fromString(key).getColor());
        }
        ;
        add(keyLabel, BorderLayout.CENTER);
    }

    public void updateKey(TrackMetadata trackMetadata) {
        String key = trackMetadata.getKey().label;
        keyLabel.setText(key);
        if (key != null) {
            keyLabel.setForeground(CamelotKeys.fromString(key).getColor());
        }
        ;
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
        bpmLabel.setFont(AppConfig.Fonts.H2);

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

    public void updateBpmPanel(TrackMetadata trackMetadata, DeviceUpdate deviceUpdate) {

        double pitchPercent = Util.pitchToPercentage(deviceUpdate.getPitch());
        String pitch = String.format("%.2f", pitchPercent) + "%";
        String oBpm = Helpers.formatIntToFloatString(trackMetadata.getTempo() * 10);
        String bpm = String.format("%.1f", deviceUpdate.getEffectiveTempo());
        bpmLabel.setText(bpm);
        pitchLabel.setText(pitch);
        origBpmLabel.setText(oBpm);
    }

}
