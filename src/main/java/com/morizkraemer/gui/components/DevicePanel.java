package com.morizkraemer.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(AppConfig.Colors.BACKGROUND_MEDIUM);

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
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            setBorder(BorderFactory.createLineBorder(Color.WHITE));
            setBackground(AppConfig.Colors.BACKGROUND_MEDIUM);
            setPreferredSize(new Dimension(200, 60));

            JLabel playerNumber = new JLabel("" + player);
            playerName = new JLabel("Disconnected");
            playerName.setForeground(AppConfig.Colors.BACKGROUND_LIGHT);
            playerNumber.setForeground(Color.WHITE);
            playerNumber.setFont(AppConfig.Fonts.TITLE_FONT);
            playerNumber.setBorder(new EmptyBorder(0, 10, 0, 0));

            playerIcon = new PlayerIcon(player);
            add(playerNumber);
            add(Box.createHorizontalGlue());
            add(playerName);
            add(Box.createHorizontalGlue());
            add(playerIcon);
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
