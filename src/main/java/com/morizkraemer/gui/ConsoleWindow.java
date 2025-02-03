package com.morizkraemer.gui;

import javax.swing.*;
import java.awt.*;

public class ConsoleWindow {

    private static ConsoleWindow instance;

    private JFrame frame;
    private JTextArea consoleArea;
    private JMenuBar menubar;
    private JMenu file;
    private JMenuItem close;

    public ConsoleWindow() {
        // Initialize the console window frame
        frame = new JFrame("Console");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);

        // Set up the text area for console output
        consoleArea = new JTextArea();
        consoleArea.setEditable(false);
        consoleArea.setLineWrap(true);
        consoleArea.setWrapStyleWord(true);

        // Add the text area inside a scroll pane
        JScrollPane scrollPane = new JScrollPane(consoleArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        setupMenuBar();

        appendToConsole("", "Console Started");
    }

    private void setupMenuBar() {
        menubar = new JMenuBar();
        file = new JMenu("File");
        close = new JMenuItem("Close");
        close.addActionListener(e -> closeConsole());

        file.add(close);
        menubar.add(file);
        frame.setJMenuBar(menubar);
    }

    public void appendToConsole(String invoker, Object object) {
            consoleArea.append("[" + invoker + "]" + " - " + object + "\n\n");
            consoleArea.setCaretPosition(consoleArea.getDocument().getLength());
    }

    public static ConsoleWindow getInstance() {
        if (instance == null) {
            instance = new ConsoleWindow();
        }
        return instance;
    }

    private void closeConsole() {
        frame.setVisible(false);
        frame.dispose();
    }

    public void openConsole() {
        frame.setVisible(true);
    }

    public void toggleConsole() {
        frame.setVisible(!frame.isVisible());
    }
}
