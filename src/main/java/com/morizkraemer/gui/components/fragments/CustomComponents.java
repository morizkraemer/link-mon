package com.morizkraemer.gui.components.fragments;

import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class CustomComponents {

    public static class CustomLabel extends JLabel {
        public CustomLabel(String text, int horizontalALignment) {
            super(text, horizontalALignment);
            setForeground(Color.WHITE);
        }
    }

    public static class CustomPanel extends JPanel {
        Border border = BorderFactory.createLineBorder(Color.WHITE);

        public CustomPanel() {
            setBackground(Color.BLACK);
            // setBorder(border);
        }

        public CustomPanel(LayoutManager layout) {
            super(layout);
            // setBorder(border);
            setBackground(Color.BLACK);

        }
    }
}
