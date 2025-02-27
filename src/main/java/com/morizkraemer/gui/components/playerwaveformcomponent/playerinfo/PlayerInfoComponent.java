package com.morizkraemer.gui.components.playerwaveformcomponent.playerinfo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

import org.deepsymmetry.beatlink.DeviceUpdate;
import org.deepsymmetry.beatlink.data.TrackMetadata;
import org.deepsymmetry.beatlink.data.TrackPositionUpdate;

import com.morizkraemer.gui.components.fragments.CustomComponents.CustomPanel;
import com.morizkraemer.state.PlayerState;

public class PlayerInfoComponent extends JPanel {

    private PlayerState playerState = PlayerState.getInstance();

    private DeviceNumberField deviceNumberField;
    private MasterSyncField masterSyncField;
    private TimeField timeField;
    private KeyField keyField;
    private BpmField bpmField;

    public PlayerInfoComponent(int playerN) {
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
        timeField = new TimeField(null, null);
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
