package com.morizkraemer.gui;

import javax.swing.*;

//import com.morizkraemer.test.TestComponent;

public class MenuBar extends JMenuBar {

    ConsoleWindow consoleWindow = ConsoleWindow.getInstance();
    DeviceManager deviceManager = DeviceManager.getInstance();
    private JMenu fileMenu, settingsMenu, viewMenu, waveFormPanelSize;
    private JMenuItem closeMenuItem, deviceManagerMenuItem, preferencesMenuItem;
    private JCheckBoxMenuItem showWaveformPanel, showWaveformPreviewPanel, consoleMenuItem;
    private JRadioButtonMenuItem waveFormPanelSize100, waveFormPanelSize75, waveFormPanelSize50;

    public MenuBar(WaveFormPanel waveFormPanel, WaveFormPreviewPanel waveFormPreviewPanel) {
        fileMenu = new JMenu("File");
        settingsMenu = new JMenu("Settings");
        viewMenu = new JMenu("View");

        closeMenuItem = new JMenuItem("Close");
        closeMenuItem.addActionListener(e -> System.exit(0));

        showWaveformPanel = new JCheckBoxMenuItem("Show Waveforms", true);
        showWaveformPanel.addActionListener(e -> {
            Boolean isSelected = showWaveformPanel.isSelected();
            SwingUtilities.invokeLater(() -> {
                waveFormPanel.setVisibilty(isSelected);
            });
        });

        showWaveformPreviewPanel = new JCheckBoxMenuItem("Show Waveform Previews", true);
        showWaveformPanel.addActionListener(e -> {
            Boolean isSelected = showWaveformPreviewPanel.isSelected();
            waveFormPreviewPanel.setVisibility(isSelected);
        });

        waveFormPanelSize = new JMenu("Size");

        waveFormPanelSize50 = new JRadioButtonMenuItem("50%");
        waveFormPanelSize75 = new JRadioButtonMenuItem("75%");
        waveFormPanelSize100 = new JRadioButtonMenuItem("100%", true);

        waveFormPanelSize50.addActionListener((e) -> {
            waveFormPanel.resizeWaveformComponent(100);
        });
        waveFormPanelSize75.addActionListener((e) -> {
            waveFormPanel.resizeWaveformComponent(150);
        });

        waveFormPanelSize100.addActionListener((e) -> {
            waveFormPanel.resizeWaveformComponent(200);
        });

        ButtonGroup waveFormPanelSizeButtonGroup = new ButtonGroup();
        waveFormPanelSizeButtonGroup.add(waveFormPanelSize50);
        waveFormPanelSizeButtonGroup.add(waveFormPanelSize75);
        waveFormPanelSizeButtonGroup.add(waveFormPanelSize100);

        waveFormPanelSize.add(waveFormPanelSize50);
        waveFormPanelSize.add(waveFormPanelSize75);
        waveFormPanelSize.add(waveFormPanelSize100);

        deviceManagerMenuItem = new JMenuItem("Device Manager");
        deviceManagerMenuItem.addActionListener(e -> deviceManager.openDeviceManager());
        preferencesMenuItem = new JMenuItem("Preferences");

        consoleMenuItem = new JCheckBoxMenuItem("Show Console", false);
        consoleMenuItem.addActionListener(e -> {
            Boolean isSelected = consoleMenuItem.isSelected();
            consoleWindow.setVisibility(isSelected);
        });

        fileMenu.add(closeMenuItem);

        viewMenu.add(deviceManagerMenuItem);
        viewMenu.add(consoleMenuItem);
        viewMenu.add(showWaveformPanel);
        viewMenu.add(waveFormPanelSize);
        viewMenu.add(showWaveformPreviewPanel);

        settingsMenu.add(preferencesMenuItem);

        add(fileMenu);
        add(settingsMenu);
        add(viewMenu);
    }

}
