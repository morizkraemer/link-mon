package com.morizkraemer;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

public class AppConfig {
    public static class Colors {
        public static final Color ACCENT_RED = new Color(120, 30, 40);
        public static final Color ACCENT_RED_MUTED = new Color(120, 80, 85);
        public static final Color BACKGROUND_LIGHT = new Color(140, 140, 140);
        public static final Color BACKGROUND_MEDIUM = new Color(60, 60, 60);
        public static final Color BACKGROUND_DARK = new Color(30, 30, 30);

        public static final Color ALERT_RED = new Color(200, 40, 40);
        public static final Color OK_GREEN = new Color(50, 168, 82);
        public static final Color LOADING_BLUE = new Color(64, 142, 224);

        public static final Color TEXT_DARK = new Color(62, 62, 62);
        public static final Color PURE_WHITE = new Color(255, 255, 255);
        public static final Color PURE_BLACK = new Color(0, 0, 0);
    }

    public static class Fonts {
        public static final Font H1 = new Font("Arial", Font.BOLD, 32);
        public static final Font H2 = new Font("Arial", Font.BOLD, 20);
        public static final Font H3 = new Font("Arial", Font.BOLD, 15);
    }

    public static class Cursors {
        public static final Cursor magnifyingCursor = createCustomCursor("/magnifying-cursor.png", "Magnifying");
    }

    private static Cursor createCustomCursor(String imagePath, String name) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(AppConfig.class.getResource(imagePath));
        return toolkit.createCustomCursor(image, new Point(0, 0), name);
    }
}
