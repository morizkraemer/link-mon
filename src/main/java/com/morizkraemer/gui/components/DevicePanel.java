package com.morizkraemer.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.morizkraemer.AppConfig;
import com.morizkraemer.state.PlayerState.PlayerStatus;

public class DevicePanel extends JPanel {
    

    List<Integer> players = Arrays.asList(1, 2, 3, 4);
    private Map<Integer, PlayerIcon> playerIcons = new HashMap<>();

    public DevicePanel() {
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
