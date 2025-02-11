package com.morizkraemer.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

import com.morizkraemer.gui.components.DevicePanel;
import com.morizkraemer.state.PlayerState;
import com.morizkraemer.state.PlayerState.AppStatus;

public class StatusBar extends JPanel {
    ConsoleWindow consoleWindow = ConsoleWindow.getInstance();
    PlayerState playerState = PlayerState.getInstance();

    private static StatusBar instance;
    private RoundedPanel statusLabel;
    private DevicePanel devicePanel;
    private JLabel statusText;

    public StatusBar() {
        setPreferredSize(new Dimension(0, 75));
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 0;

        devicePanel = new DevicePanel();
        gbc.gridx = 2;
        gbc.weightx = 0.6;
        add(devicePanel, gbc);

        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        gbc.gridx = 1;
        gbc.weightx = 0.3;
        add(spacer, gbc);


        statusLabel = new RoundedPanel(10, getDarkerColor(AppStatus.SERVICE_OFFLINE.getColor()),
                getDarkerColor(AppStatus.SERVICE_OFFLINE.getColor()).darker(), 2, 75);
        statusText = new JLabel(AppStatus.SERVICE_OFFLINE.getMessage());
        statusText.setForeground(Color.WHITE);

        statusLabel.add(statusText);

        gbc.gridx = 0;
        gbc.weightx = 0.1;
        add(statusLabel, gbc);

        setStatus(AppStatus.SERVICE_OFFLINE);

        Timer swingTimer = new Timer(2000, (e) -> {
            setStatus(getStatusUpdate());
            devicePanel.updatePanel();
        });

        swingTimer.start();
    }

    private AppStatus getStatusUpdate() {
        return playerState.getAppStatus();
    }

    public void setStatus(AppStatus status) {
        statusText.setText(status.getMessage());
        setBackground(status.getColor());
        repaint();
    }

    private Color getDarkerColor(Color color) {
        return color.darker();
    }

    public class RoundedPanel extends JPanel {
        private int cornerRadius;
        private Color borderColor;
        private int borderThickness;

        public RoundedPanel(int cornerRadius, Color backgroundColor, Color borderColor, int borderThickness,
                int width) {
            this.cornerRadius = cornerRadius;
            this.borderColor = borderColor;
            this.borderThickness = borderThickness;

            setOpaque(false);
            setPreferredSize(new Dimension(width, 30));
            setMaximumSize(new Dimension(width, 30));
            setLayout(new GridBagLayout());

            setBackground(backgroundColor); // Ensure background updates dynamically
        }

        @Override
        public void setBackground(Color bg) {
            super.setBackground(bg);
            repaint(); // Redraw on background change
        }

        public void setBorderColor(Color borderColor) {
            this.borderColor = borderColor;
            repaint(); // Redraw when border color changes
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Background Fill
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

            // Border
            if (borderThickness > 0) {
                g2.setStroke(new BasicStroke(borderThickness));
                g2.setColor(borderColor);
                g2.drawRoundRect(borderThickness / 2, borderThickness / 2, getWidth() - borderThickness,
                        getHeight() - borderThickness, cornerRadius, cornerRadius);
            }

            g2.dispose();
        }
    }

    public static synchronized StatusBar getInstance() {
        if (instance == null) {
            instance = new StatusBar();
        }
        return instance;
    }
}
