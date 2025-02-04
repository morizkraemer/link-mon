package com.morizkraemer.gui.components;

import org.deepsymmetry.beatlink.data.WaveformDetailComponent;

public class WaveFormComponent extends WaveformDetailComponent {
        public WaveFormComponent(int player) {
            super(player);
            setDoubleBuffered(true);
        }
    }
