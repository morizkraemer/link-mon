package com.morizkraemer.gui;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    ConsoleWindow consoleWindow = ConsoleWindow.getInstance();
    DeviceManager deviceManager = DeviceManager.getInstance();
    private JMenu fileMenu, settingsMenu, viewMenu, waveFormPanelSize, waveFormPreviewPanelSize;
    private JMenuItem closeMenuItem, deviceManagerMenuItem, preferencesMenuItem;
    private JCheckBoxMenuItem showWaveformPanel, showWaveformPreviewPanel, consoleMenuItem;
    private JRadioButtonMenuItem waveFormPanelSize100, waveFormPanelSize75, waveFormPanelSize50;
    private JRadioButtonMenuItem waveFormPreviewPanelSize100, waveFormPreviewPanelSize75, waveFormPreviewPanelSize50;

    public MenuBar(WaveFormPanel waveFormPanel, WaveFormPreviewPanel waveFormPreviewPanel) {
        fileMenu = new JMenu("File");
        settingsMenu = new JMenu("Settings");
        viewMenu = new JMenu("View");

        closeMenuItem = new JMenuItem("Close");
        closeMenuItem.addActionListener(e -> System.exit(0));

        // Show Waveform Panel Checkbox
        showWaveformPanel = new JCheckBoxMenuItem("Show Waveforms", true);
        showWaveformPanel.addActionListener(e -> {
            Boolean isSelected = showWaveformPanel.isSelected();
            SwingUtilities.invokeLater(() -> waveFormPanel.setVisibilty(isSelected));
        });

        // Show Waveform Preview Panel Checkbox
        showWaveformPreviewPanel = new JCheckBoxMenuItem("Show Waveform Previews", true);
        showWaveformPreviewPanel.addActionListener(e -> {
            Boolean isSelected = showWaveformPreviewPanel.isSelected();
            SwingUtilities.invokeLater(() -> waveFormPreviewPanel.setVisibility(isSelected));
        });

        // Waveform Panel Size Submenu
        waveFormPanelSize = new JMenu("Waveform Size");
        waveFormPanelSize50 = new JRadioButtonMenuItem("50%");
        waveFormPanelSize75 = new JRadioButtonMenuItem("75%", true);
        waveFormPanelSize100 = new JRadioButtonMenuItem("100%");

        waveFormPanelSize50.addActionListener(e -> waveFormPanel.resizeWaveformComponent(100));
        waveFormPanelSize75.addActionListener(e -> waveFormPanel.resizeWaveformComponent(150));
        waveFormPanelSize100.addActionListener(e -> waveFormPanel.resizeWaveformComponent(200));

        ButtonGroup waveFormPanelSizeButtonGroup = new ButtonGroup();
        waveFormPanelSizeButtonGroup.add(waveFormPanelSize50);
        waveFormPanelSizeButtonGroup.add(waveFormPanelSize75);
        waveFormPanelSizeButtonGroup.add(waveFormPanelSize100);

        waveFormPanelSize.add(waveFormPanelSize50);
        waveFormPanelSize.add(waveFormPanelSize75);
        waveFormPanelSize.add(waveFormPanelSize100);

        // Waveform Preview Panel Size Submenu
        waveFormPreviewPanelSize = new JMenu("Preview Size");
        waveFormPreviewPanelSize50 = new JRadioButtonMenuItem("50%");
        waveFormPreviewPanelSize75 = new JRadioButtonMenuItem("75%", true);
        waveFormPreviewPanelSize100 = new JRadioButtonMenuItem("100%");

        waveFormPreviewPanelSize50.addActionListener(e -> waveFormPreviewPanel.resizeWaveformComponent(100));
        waveFormPreviewPanelSize75.addActionListener(e -> waveFormPreviewPanel.resizeWaveformComponent(150));
        waveFormPreviewPanelSize100.addActionListener(e -> waveFormPreviewPanel.resizeWaveformComponent(200));

        ButtonGroup waveFormPreviewPanelSizeButtonGroup = new ButtonGroup();
        waveFormPreviewPanelSizeButtonGroup.add(waveFormPreviewPanelSize50);
        waveFormPreviewPanelSizeButtonGroup.add(waveFormPreviewPanelSize75);
        waveFormPreviewPanelSizeButtonGroup.add(waveFormPreviewPanelSize100);

        waveFormPreviewPanelSize.add(waveFormPreviewPanelSize50);
        waveFormPreviewPanelSize.add(waveFormPreviewPanelSize75);
        waveFormPreviewPanelSize.add(waveFormPreviewPanelSize100);

        // Device Manager Menu Item
        deviceManagerMenuItem = new JMenuItem("Device Manager");
        deviceManagerMenuItem.addActionListener(e -> deviceManager.openDeviceManager());

        // Preferences Menu Item
        preferencesMenuItem = new JMenuItem("Preferences");

        // Console Checkbox Menu Item
        consoleMenuItem = new JCheckBoxMenuItem("Show Console", false);
        consoleMenuItem.addActionListener(e -> {
            Boolean isSelected = consoleMenuItem.isSelected();
            SwingUtilities.invokeLater(() -> consoleWindow.setVisibility(isSelected));
        });

        // Adding Menu Items to Menus
        fileMenu.add(closeMenuItem);
        viewMenu.add(deviceManagerMenuItem);
        viewMenu.add(consoleMenuItem);
        viewMenu.add(showWaveformPanel);
        viewMenu.add(waveFormPanelSize);
        viewMenu.add(showWaveformPreviewPanel);
        viewMenu.add(waveFormPreviewPanelSize);
        settingsMenu.add(preferencesMenuItem);

        // Adding Menus to MenuBar
        add(fileMenu);
        add(settingsMenu);
        add(viewMenu);
    }
}
