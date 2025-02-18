package com.morizkraemer.gui.components;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.deepsymmetry.beatlink.data.WaveformDetailComponent;

import com.morizkraemer.AppConfig;

public class WaveFormComponent extends WaveformDetailComponent {
    private int waveFormScale = 1;
    private int lastY;
    private Boolean mousePressed = false;

    public WaveFormComponent(int player) {
        super(player);

        setDoubleBuffered(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastY = e.getYOnScreen();
                setCursor(AppConfig.Cursors.magnifyingCursor);
                mousePressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
                mousePressed = false;
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(AppConfig.Cursors.magnifyingCursor);

            }

            public void mouseExited(MouseEvent e) {
                if (!mousePressed) setCursor(Cursor.getDefaultCursor());
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int deltaY = e.getYOnScreen() - lastY;
                if (Math.abs(deltaY) > 5) {
                    if (deltaY > 0) {
                        if (waveFormScale < 256) waveFormScale++;
                    } else {
                        if (waveFormScale > 1) waveFormScale--;
                    }
                    lastY = e.getYOnScreen();
                    setScale(waveFormScale);
                    repaint();
                }
            }
        });
    }

}
