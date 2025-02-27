package com.morizkraemer.gui.components.playerwaveformcomponent.playerinfo;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import org.deepsymmetry.beatlink.DeviceUpdate;
import org.deepsymmetry.beatlink.Util;
import org.deepsymmetry.beatlink.data.TrackMetadata;

import com.morizkraemer.AppConfig;
import com.morizkraemer.gui.components.fragments.CustomComponents.CustomLabel;
import com.morizkraemer.gui.components.fragments.CustomComponents.CustomPanel;
import com.morizkraemer.utils.Helpers;

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
