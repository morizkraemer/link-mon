package com.morizkraemer.gui.components.playerwaveformcomponent.playerinfo;

import java.awt.BorderLayout;

import javax.swing.SwingConstants;

import org.deepsymmetry.beatlink.data.TrackMetadata;

import com.morizkraemer.AppConfig;
import com.morizkraemer.gui.components.fragments.CustomComponents.CustomLabel;
import com.morizkraemer.gui.components.fragments.CustomComponents.CustomPanel;
import com.morizkraemer.utils.CamelotKeys;

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
