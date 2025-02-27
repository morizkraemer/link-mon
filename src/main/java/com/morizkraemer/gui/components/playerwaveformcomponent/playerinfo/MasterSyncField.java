package com.morizkraemer.gui.components.playerwaveformcomponent.playerinfo;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import org.deepsymmetry.beatlink.DeviceUpdate;

import com.morizkraemer.AppConfig;
import com.morizkraemer.gui.components.fragments.CustomComponents.CustomLabel;
import com.morizkraemer.gui.components.fragments.CustomComponents.CustomPanel;

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
