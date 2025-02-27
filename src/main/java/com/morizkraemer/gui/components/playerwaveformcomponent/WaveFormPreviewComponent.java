package com.morizkraemer.gui.components.playerwaveformcomponent;

import org.deepsymmetry.beatlink.data.WaveformPreviewComponent;

public class WaveFormPreviewComponent extends WaveformPreviewComponent {

    public WaveFormPreviewComponent(int player) {
        super(player);

        setDoubleBuffered(true);
    }

}
