package com.morizkraemer.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.morizkraemer.AppConfig.Colors;

public class WorkspacePanel extends JPanel {
    public WorkspacePanel() {
        setLayout(new BorderLayout());
        setBackground(Colors.BACKGROUND_DARK);
    }
}
