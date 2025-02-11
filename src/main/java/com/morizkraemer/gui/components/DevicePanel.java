package com.morizkraemer.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.deepsymmetry.beatlink.DeviceAnnouncement;

import com.morizkraemer.AppConfig;
import com.morizkraemer.state.PlayerState;
import com.morizkraemer.state.PlayerState.PlayerStatus;

public class DevicePanel extends JPanel {
    PlayerState playerState = PlayerState.getInstance();
    Map<Integer, PlayerComponent> players = new ConcurrentHashMap<>();
    int foundPlayersVersion = -1;
    private Map<Integer, PlayerIcon> playerIcons = new HashMap<>();

    public DevicePanel() {
        setBackground(AppConfig.Colors.BACKGROUND_MEDIUM);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        for (int i = 1; i < 5; i++) {
            setOpaque(false);
            PlayerComponent playerComponent = new PlayerComponent(i);
            playerIcons.put(i, playerComponent.getIcon());
            players.put(i, playerComponent);
            add(playerComponent);
        }
    }

    public void updatePanel() {
        int fpv = playerState.getFoundPlayersVersion();
        if (fpv > foundPlayersVersion) {
            foundPlayersVersion = fpv;
            Map<Integer, DeviceAnnouncement> foundPlayers = playerState.getFoundPlayers();

            players.forEach((playerNumber, component) -> {
                if (foundPlayers.containsKey(playerNumber)) {
                    updatePlayerStatus(playerNumber, PlayerStatus.ONLINE, foundPlayers.get(playerNumber));
                } else {
                    updatePlayerStatus(playerNumber, PlayerStatus.OFFLINE, null);
                }
            });
            repaint();
        }
    }

    public void updatePlayerStatus(int player, PlayerStatus status, DeviceAnnouncement announcement) {
        if (playerIcons.containsKey(player)) {
            playerIcons.get(player).setStatus(status);
        }

        if (players.containsKey(player)) {
            String name = (announcement != null) ? announcement.getDeviceName() : "Disconnected";
            players.get(player).setPlayerName(name);
        }
    }

    private class PlayerComponent extends JPanel {
        private PlayerIcon playerIcon;
        private JLabel playerName;

        PlayerComponent(int player) {
            setName("Player" + player);
            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createLineBorder(Color.WHITE));
            setBackground(AppConfig.Colors.BACKGROUND_MEDIUM);
            setPreferredSize(new Dimension(100, 60));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;

            JLabel playerNumber = new JLabel("" + player, SwingConstants.CENTER);
            playerNumber.setForeground(Color.WHITE);
            playerNumber.setFont(AppConfig.Fonts.H1);
            playerNumber.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));

            gbc.gridx = 0;
            gbc.weightx = 1;
            add(playerNumber, gbc);

            playerName = new JLabel("Disconnected", SwingConstants.CENTER);
            playerName.setForeground(AppConfig.Colors.BACKGROUND_LIGHT);

            gbc.gridx = 1;
            gbc.weightx = 3;
            add(playerName, gbc);

            playerIcon = new PlayerIcon(player);

            gbc.gridx = 2;
            gbc.weightx = 1;
            add(playerIcon, gbc);
        }

        public void setPlayerName(String name) {
            playerName.setText(name);
            playerName.setForeground(AppConfig.Colors.PURE_WHITE);
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
            setHorizontalAlignment(SwingConstants.CENTER);
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
}
