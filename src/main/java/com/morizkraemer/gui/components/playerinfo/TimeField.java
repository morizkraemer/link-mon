package com.morizkraemer.gui.components.playerinfo;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import org.deepsymmetry.beatlink.data.TrackMetadata;
import org.deepsymmetry.beatlink.data.TrackPositionUpdate;

import com.morizkraemer.AppConfig;
import com.morizkraemer.gui.components.fragments.CustomComponents.CustomLabel;
import com.morizkraemer.gui.components.fragments.CustomComponents.CustomPanel;
import com.morizkraemer.utils.Helpers;

class TimeField extends CustomPanel {
    private final Border b = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE);


    private CustomLabel elapsedTime;
    private CustomLabel totalTime;
    private CustomLabel separator;

    public TimeField(String playT, String totalT) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        elapsedTime = new CustomLabel(playT, SwingConstants.CENTER);
        elapsedTime.setFont(AppConfig.Fonts.H2);
        separator = new CustomLabel(":", SwingConstants.CENTER);
        totalTime = new CustomLabel(totalT, SwingConstants.CENTER);
        totalTime.setFont(AppConfig.Fonts.H2);

        // Prevent extra spacing between labels
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 2, 0, 2); // Minimal spacing
        gbc.anchor = GridBagConstraints.CENTER;

        // Elapsed time label
        gbc.gridx = 0;
        gbc.weightx = 0; // No extra horizontal space
        add(elapsedTime, gbc);

        // Separator (:)
        gbc.gridx = 1;
        gbc.weightx = 0;
        add(separator, gbc);

        // Total time label
        gbc.gridx = 2;
        gbc.weightx = 0;
        add(totalTime, gbc);

        setBorder(b);
    }

    public void updateTime(TrackPositionUpdate trackPositionUpdate, TrackMetadata trackMetadata) {
        String playT = Helpers.formatMsToTime(trackPositionUpdate.milliseconds);
        String totalT = Helpers.formatSecondsToTime(trackMetadata.getDuration());
        elapsedTime.setText(playT);
        totalTime.setText(totalT);
    }
}
