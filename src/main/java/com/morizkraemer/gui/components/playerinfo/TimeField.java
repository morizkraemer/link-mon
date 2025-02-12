package com.morizkraemer.gui.components.playerinfo;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.Border;

import org.deepsymmetry.beatlink.data.TrackMetadata;
import org.deepsymmetry.beatlink.data.TrackPositionUpdate;

import com.morizkraemer.AppConfig;
import com.morizkraemer.gui.components.fragments.CustomComponents.CustomLabel;
import com.morizkraemer.gui.components.fragments.CustomComponents.CustomPanel;
import com.morizkraemer.utils.Helpers;

class TimeField extends CustomPanel {
    private final Border b = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE);

    private JPanel cardPanel;
    private CardLayout cardLayout;

    private ElapsedPanel elapsedPanel;
    private RemainingPanel remainingPanel;

    private String playT;
    private String totalT;
    private String remainingT;

    public TimeField(String playT, String totalT) {
        setLayout(new BorderLayout());

        elapsedPanel = new ElapsedPanel(playT, totalT);
        remainingPanel = new RemainingPanel("");

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(elapsedPanel, "Elapsed");
        cardPanel.add(remainingPanel, "Remaining");

        add(cardPanel, BorderLayout.CENTER);
        setBorder(b);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleDisplay();
            }
        });
    }

    public void updateTime(TrackPositionUpdate trackPositionUpdate, TrackMetadata trackMetadata) {
        int elapsedMs = (int) trackPositionUpdate.milliseconds;
        int totalSeconds = trackMetadata.getDuration();
        int remainingMs = (totalSeconds * 1000) - elapsedMs;

        playT = Helpers.formatMsToTime(elapsedMs);
        totalT = Helpers.formatSecondsToTime(totalSeconds);
        remainingT = Helpers.formatMsToTime(remainingMs);

        elapsedPanel.updateTime(playT, totalT);
        remainingPanel.updateTime(remainingT);
    }

    private void toggleDisplay() {
        cardLayout.next(cardPanel);
    }

    private static class ElapsedPanel extends CustomPanel {
        private CustomLabel elapsedTime;
        private CustomLabel totalTime;
        private CustomLabel separator;

        public ElapsedPanel(String playT, String totalT) {
            setLayout(new GridBagLayout());
            setOpaque(true);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 2, 0, 2);
            gbc.anchor = GridBagConstraints.CENTER;

            elapsedTime = new CustomLabel(playT, SwingConstants.CENTER);
            elapsedTime.setFont(AppConfig.Fonts.H2);

            separator = new CustomLabel(":", SwingConstants.CENTER);
            separator.setFont(AppConfig.Fonts.H2);

            totalTime = new CustomLabel(totalT, SwingConstants.CENTER);
            totalTime.setFont(AppConfig.Fonts.H2);

            gbc.gridx = 0;
            gbc.weightx = 0;
            add(elapsedTime, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0;
            add(separator, gbc);

            gbc.gridx = 2;
            gbc.weightx = 0;
            add(totalTime, gbc);
        }

        public void updateTime(String playT, String totalT) {
            elapsedTime.setText(playT);
            totalTime.setText(totalT);
        }
    }

    private static class RemainingPanel extends CustomPanel {
        private CustomLabel remainingTime;

        public RemainingPanel(String remainingT) {
            setLayout(new BorderLayout());
            setOpaque(true);

            remainingTime = new CustomLabel(remainingT, SwingConstants.CENTER);
            remainingTime.setFont(AppConfig.Fonts.H2);

            add(remainingTime, BorderLayout.CENTER);
        }

        public void updateTime(String remainingT) {
            remainingTime.setText(remainingT);
        }
    }
}
