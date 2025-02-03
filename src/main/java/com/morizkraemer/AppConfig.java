package com.morizkraemer;

import java.awt.Color;
import java.awt.Font;

public class AppConfig {
    public static class Colors {
        public static final Color ACCENT_RED = new Color(120, 30, 40);
        public static final Color ACCENT_RED_MUTED = new Color(120, 80, 85);
        public static final Color BACKGROUND_LIGHT = new Color(140, 140, 140);
        public static final Color BACKGROUND_MEDIUM = new Color(30, 30, 30);

        public static final Color ALERT_RED = new Color(200, 40, 40);
        public static final Color OK_GREEN = new Color(50, 168, 82);
        public static final Color LOADING_BLUE = new Color(64, 142, 224);


        public static final Color TEXT_DARK = new Color(62, 62, 62);
        public static final Color PURE_WHITE = new Color(255, 255, 255);
    }

    public static class Fonts {
        public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 32);
        public static final Font SUBTITLE_FONT = new Font("Arial", Font.BOLD, 20);
    }
}
