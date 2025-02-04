package com.morizkraemer;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.morizkraemer.gui.MainWindow;
import com.morizkraemer.services.DeviceFinderService;

public class App {
    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        JFrame frame = new JFrame("Simple Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 200); // Set the size to 200x200

        DeviceFinderService deviceFinder = DeviceFinderService.getInstance();
        deviceFinder.runDeviceFinder();

        try {
            SwingUtilities.invokeAndWait(() -> {

                MainWindow mainWindow = MainWindow.getInstance();
                mainWindow.openMainWindow();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
