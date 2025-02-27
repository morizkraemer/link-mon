package com.morizkraemer.gui.components.playerwaveformcomponent.playerinfo;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import org.deepsymmetry.beatlink.DeviceUpdate;

import com.morizkraemer.AppConfig;
import com.morizkraemer.gui.components.fragments.CustomComponents.CustomLabel;
import com.morizkraemer.gui.components.fragments.CustomComponents.CustomPanel;

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
