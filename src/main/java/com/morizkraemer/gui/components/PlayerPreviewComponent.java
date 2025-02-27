package com.morizkraemer.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.morizkraemer.gui.components.playerwaveformcomponent.WaveFormPreviewComponent;
import com.morizkraemer.gui.components.playerwaveformcomponent.playerinfo.PreviewInfoComponent;

public class PlayerPreviewComponent extends JPanel {
    private WaveFormPreviewComponent waveFormPreviewComponent;
    private PreviewInfoComponent previewInfoComponent;
    private int size = 150;

    public PlayerPreviewComponent(int playerN) {

        setLayout(new BorderLayout());

        previewInfoComponent = new PreviewInfoComponent(playerN);
        previewInfoComponent.setPreferredSize(new Dimension(size, size));

        waveFormPreviewComponent = new WaveFormPreviewComponent(playerN);
        waveFormPreviewComponent.setMaximumSize(new Dimension(Integer.MAX_VALUE, size));
        waveFormPreviewComponent.setName("Player " + playerN);

        add(previewInfoComponent, BorderLayout.WEST);
        add(waveFormPreviewComponent, BorderLayout.CENTER);

        setMaximumSize(new Dimension(Integer.MAX_VALUE, size));
    }

    public void resizeComponent(int height) {
        previewInfoComponent.setPreferredSize(new Dimension(height, height));
        waveFormPreviewComponent.setPreferredSize(new Dimension(Integer.MAX_VALUE, height));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
        revalidate();
        repaint();
    }
}
