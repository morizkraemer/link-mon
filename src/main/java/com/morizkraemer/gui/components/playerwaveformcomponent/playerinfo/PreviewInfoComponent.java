package com.morizkraemer.gui.components.playerwaveformcomponent.playerinfo;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.deepsymmetry.beatlink.DeviceUpdate;
import org.deepsymmetry.beatlink.data.TrackMetadata;
import org.deepsymmetry.beatlink.data.TrackPositionUpdate;

import com.morizkraemer.state.PlayerState;

public class PreviewInfoComponent extends JPanel {
    private DeviceNumberField deviceNumberField;
    private TimeField timeField;
    private PlayerState playerState = PlayerState.getInstance();

    public PreviewInfoComponent(int playerN) {

        Timer swingTimer = new Timer(1000, e -> {
            DeviceUpdate deviceUpdate = playerState.getDeviceUpdate(playerN);
            TrackMetadata trackMetadata = playerState.getTrackUpdate(playerN);
            TrackPositionUpdate trackPositionUpdate = playerState.getTrackPositionUpdate(playerN);

            if (trackMetadata != null && deviceUpdate != null) {
                deviceNumberField.updateDeviceNumber(deviceUpdate);
                timeField.updateTime(trackPositionUpdate, trackMetadata);
            }
            revalidate();
            repaint();
        });

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(Color.WHITE));

        deviceNumberField = new DeviceNumberField(playerN);
        timeField = new TimeField(null, null);

        swingTimer.start();


        add(deviceNumberField, BorderLayout.NORTH);
        add(timeField, BorderLayout.SOUTH);
    }
}
