package com.morizkraemer.utils;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public enum CamelotKeys {
    _1A("1A", "Abm", new Color(0, 153, 153)), 
    _2A("2A", "Ebm", new Color(51, 204, 204)),    
    _3A("3A", "Bbm", new Color(102, 204, 153)),   
    _4A("4A", "Fm", new Color(51, 153, 102)),    
    _5A("5A", "Cm", new Color(153, 204, 102)),    
    _6A("6A", "Gm", new Color(102, 153, 0)),     
    _7A("7A", "Dm", new Color(153, 204, 0)),    
    _8A("8A", "Am", new Color(204, 204, 0)),    
    _9A("9A", "Em", new Color(204, 153, 51)),    
    _10A("10A", "Bm", new Color(153, 102, 0)),   
    _11A("11A", "F#m", new Color(204, 102, 0)),  
    _12A("12A", "Dbm", new Color(255, 153, 0)),

    _1B("1B", "B", new Color(255, 102, 0)),    
    _2B("2B", "F#", new Color(255, 51, 0)),     
    _3B("3B", "Db", new Color(204, 51, 51)),   
    _4B("4B", "Ab", new Color(204, 0, 102)),    
    _5B("5B", "Eb", new Color(255, 0, 153)),    
    _6B("6B", "Bb", new Color(255, 51, 204)),    
    _7B("7B", "F", new Color(255, 102, 255)),    
    _8B("8B", "C", new Color(204, 102, 255)),    
    _9B("9B", "G", new Color(153, 51, 204)),    
    _10B("10B", "D", new Color(102, 0, 153)),   
    _11B("11B", "A", new Color(153, 0, 102)),   
    _12B("12B", "E", new Color(204, 0, 153));

    private final String camelotKey;
    private final String musicalKey;
    private final Color color;

    private static final Map<String, CamelotKeys> BY_CAMELOT = new HashMap<>();
    private static final Map<String, CamelotKeys> BY_MUSICAL = new HashMap<>();

    static {
        for (CamelotKeys k : values()) {
            BY_CAMELOT.put(k.camelotKey, k);
            BY_MUSICAL.put(k.musicalKey, k);
        }
    }

    CamelotKeys(String camelotKey, String musicalKey, Color color) {
        this.camelotKey = camelotKey;
        this.musicalKey = musicalKey;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public String getCamelotKey() {
        return camelotKey;
    }

    public String getMusicalKey() {
        return musicalKey;
    }

    @Override
    public String toString() {
        return camelotKey + " (" + musicalKey + ")";
    }

    public static CamelotKeys fromString(String keyInput) {
        if (BY_CAMELOT.containsKey(keyInput)) {
            return BY_CAMELOT.get(keyInput);
        } else if (BY_MUSICAL.containsKey(keyInput)) {
            return BY_MUSICAL.get(keyInput);
        } else {
            throw new IllegalArgumentException("Invalid key: " + keyInput);
        }
    }
}
