package com.morizkraemer.utils;

import java.awt.Color;

public enum CamelotKeys {
    _1A("1A", new Color(0, 153, 153)),     // Abm (G# minor) - #009999
    _2A("2A", new Color(51, 204, 204)),    // Ebm - #33CCCC
    _3A("3A", new Color(102, 204, 153)),   // Bbm - #66CC99
    _4A("4A", new Color(51, 153, 102)),    // Fm - #339966
    _5A("5A", new Color(153, 204, 102)),   // Cm - #99CC66
    _6A("6A", new Color(102, 153, 0)),     // Gm - #669900
    _7A("7A", new Color(153, 204, 0)),     // Dm - #99CC00
    _8A("8A", new Color(204, 204, 0)),     // Am - #CCCC00
    _9A("9A", new Color(204, 153, 51)),    // Em - #CC9933
    _10A("10A", new Color(153, 102, 0)),   // Bm - #996600
    _11A("11A", new Color(204, 102, 0)),   // F#m - #CC6600
    _12A("12A", new Color(255, 153, 0)),   // Dbm (C#m) - #FF9900

    _1B("1B", new Color(255, 102, 0)),     // B - #FF6600
    _2B("2B", new Color(255, 51, 0)),      // F# - #FF3300
    _3B("3B", new Color(204, 51, 51)),     // Db (C#) - #CC3333
    _4B("4B", new Color(204, 0, 102)),     // Ab - #CC0066
    _5B("5B", new Color(255, 0, 153)),     // Eb - #FF0099
    _6B("6B", new Color(255, 51, 204)),    // Bb - #FF33CC
    _7B("7B", new Color(255, 102, 255)),   // F - #FF66FF
    _8B("8B", new Color(204, 102, 255)),   // C - #CC66FF
    _9B("9B", new Color(153, 51, 204)),    // G - #9933CC
    _10B("10B", new Color(102, 0, 153)),   // D - #660099
    _11B("11B", new Color(153, 0, 102)),   // A - #990066
    _12B("12B", new Color(204, 0, 153));   // E - #CC0099

    private final String key;
    private final Color color;

    CamelotKeys(String key, Color color) {
        this.key = key;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return key;
    }

    // Static method to find a CamelotKey by its key string (e.g., "7A")
    public static CamelotKeys fromString(String key) {
        for (CamelotKeys ck : values()) {
            if (ck.getKey().equalsIgnoreCase(key)) {
                return ck;
            }
        }
        throw new IllegalArgumentException("Invalid Camelot Key: " + key);
    }
}
