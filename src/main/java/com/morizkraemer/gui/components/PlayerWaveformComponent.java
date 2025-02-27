package com.morizkraemer.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.morizkraemer.gui.components.playerwaveformcomponent.WaveFormComponent;
import com.morizkraemer.gui.components.playerwaveformcomponent.playerinfo.PlayerInfoComponent;

public class PlayerWaveformComponent extends JPanel {
    private PlayerInfoComponent playerInfoComponent;
    private WaveFormComponent waveFormComponent;
    private int size = 150;

    public PlayerWaveformComponent(int playerN) {
        setLayout(new BorderLayout());

        playerInfoComponent = new PlayerInfoComponent(playerN);
        playerInfoComponent.setPreferredSize(new Dimension(size, size));

        waveFormComponent = new WaveFormComponent(playerN);
        waveFormComponent.setMaximumSize(new Dimension(Integer.MAX_VALUE, size));
        waveFormComponent.setName("Player " + playerN);

        add(playerInfoComponent, BorderLayout.WEST);
        add(waveFormComponent, BorderLayout.CENTER);

        setMaximumSize(new Dimension(Integer.MAX_VALUE, size));
    }

    public void resizeComponent(int height) {
        playerInfoComponent.setPreferredSize(new Dimension(height, height));
        waveFormComponent.setPreferredSize(new Dimension(Integer.MAX_VALUE, height));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
        revalidate();
        repaint();
    }

}
