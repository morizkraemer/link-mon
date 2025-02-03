package com.morizkraemer.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.morizkraemer.AppConfig;

public class StatusBar extends JPanel {

    private static StatusBar instance;

    public enum Status {
        SERVICE_OFFLINE(AppConfig.Colors.BACKGROUND_LIGHT),
        SEARCHING(AppConfig.Colors.LOADING_BLUE),
        NO_DEVICES_FOUND(AppConfig.Colors.ALERT_RED),
        DEVICES_FOUND(AppConfig.Colors.OK_GREEN);

        private final Color color;

        Status(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
    }

    Status status;

    public void setStatus(Status status) {
        SwingUtilities.invokeLater(() -> setBackground(status.getColor()));
    }

    public StatusBar() {
        setStatus(Status.SERVICE_OFFLINE);
        setPreferredSize(new Dimension(23, 23));
    }

    public static StatusBar getInstance() {
        if (instance == null) {
                instance = new StatusBar();
        }
        return instance;
    }
}
