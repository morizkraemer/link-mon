package com.morizkraemer.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.morizkraemer.AppConfig;
import com.morizkraemer.AppConfig.Colors;

public class StatusBar extends JPanel {
    ConsoleWindow consoleWindow = ConsoleWindow.getInstance();
    private static StatusBar instance;
    private RoundedPanel statusLabel;
    private DevicePanel devicePanel;
    private JLabel statusText;
    private AppStatus currentStatus;

    public enum AppStatus {
        SERVICE_OFFLINE(AppConfig.Colors.BACKGROUND_MEDIUM, "Service offline"),
        SEARCHING(AppConfig.Colors.LOADING_BLUE, "Searching for devices"),
        NO_DEVICES_FOUND(AppConfig.Colors.ALERT_RED, "No devices found"),
        DEVICES_FOUND(AppConfig.Colors.OK_GREEN, "Devices found");

        private final Color color;
        private final String message;

        AppStatus(Color color, String message) {
            this.color = color;
            this.message = message;
        }

        public Color getColor() {
            return color;
        }

        public String getMessage() {
            return message;
        }
    }

    public StatusBar() {
        setPreferredSize(new Dimension(0, 75));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Initialize RoundedPanels with default status color
        statusText = new JLabel(AppStatus.SERVICE_OFFLINE.getMessage());
        statusText.setForeground(Color.WHITE);
        statusText.setText(AppStatus.SERVICE_OFFLINE.getMessage());
        statusLabel = new RoundedPanel(10, getDarkerColor(AppStatus.SERVICE_OFFLINE.getColor()),
                getDarkerColor(AppStatus.SERVICE_OFFLINE.getColor()).darker(), 2, 200);
        statusLabel.add(statusText);

        devicePanel = new DevicePanel();

        add(statusLabel);
        add(Box.createHorizontalGlue());
        add(devicePanel);

        // Set initial status
        setStatus(AppStatus.SERVICE_OFFLINE);
    }

    public void setStatus(AppStatus status) {
        currentStatus = status;
        statusText.setText(status.getMessage());
        setBackground(status.getColor());

        // Update label backgrounds and borders

        repaint();
    }

    private Color getDarkerColor(Color color) {
        return color.darker();
    }

    private enum PlayerStatus {
        ONLINE(3),
        CONNECTING(2),
        OFFLINE(1);

        private final int status;

        PlayerStatus(int status) {
            this.status = status;
        }
    }

    public class DevicePanel extends JPanel {
        List<Integer> players = Arrays.asList(1, 2, 3, 4);
        private Map<Integer, PlayerIcon> playerIcons = new HashMap<>();

        DevicePanel() {
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            setBackground(AppConfig.Colors.BACKGROUND_MEDIUM);

            for (Integer player : players) {
                setOpaque(false);
                PlayerComponent playerComponent = new PlayerComponent(player);
                playerIcons.put(player, playerComponent.getIcon());
                add(playerComponent);
            }

        }

        public void updatePlayerStatus(int player, PlayerStatus status) {
            if (playerIcons.containsKey(player)) {
                playerIcons.get(player).setStatus(status);
            }
        }

        private class PlayerComponent extends JPanel {
            private PlayerIcon playerIcon;
            private PlayerStatus status;

            PlayerComponent(int player) {
                setName("Player" + player);
                setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
                setBorder(BorderFactory.createLineBorder(Color.WHITE));
                setBackground(AppConfig.Colors.BACKGROUND_MEDIUM);
                setPreferredSize(new Dimension(200, 60));

                JLabel playerNumber = new JLabel("" + player);
                playerNumber.setForeground(Color.WHITE);
                playerNumber.setFont(AppConfig.Fonts.TITLE_FONT);
                playerNumber.setBorder(new EmptyBorder(0, 10, 0, 0));
                playerIcon = new PlayerIcon(player);
                add(playerNumber);
                add(Box.createHorizontalGlue());
                add(playerIcon);
            }

            public PlayerIcon getIcon() {
                return playerIcon;
            }
        }

        private class PlayerIcon extends JLabel {
            ImageIcon offline = new ImageIcon(getClass().getResource("/img/p2424_cdj1.png"));
            ImageIcon connecting = new ImageIcon(getClass().getResource("/img/p2426_cdj3.png"));
            ImageIcon online = new ImageIcon(getClass().getResource("/img/p2425_cdj2.png"));

            PlayerIcon(int player) {
                setName("Icon" + player);
                setStatus(PlayerStatus.OFFLINE);
            }

            public void setStatus(PlayerStatus status) {
                switch (status) {
                    case OFFLINE:
                        setIcon(offline);
                        break;
                    case CONNECTING:
                        setIcon(connecting);
                        break;
                    case ONLINE:
                        setIcon(online);
                        break;
                    default:
                        break;
                }
            }
        }

    };

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
