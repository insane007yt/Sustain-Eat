/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.ui.util;

import java.awt.Font;

public class AppFonts {
    private static final String FONT_FAMILY = AppFonts.getSansSerifFont();
    public static final Font DISPLAY_LARGE = new Font(FONT_FAMILY, 1, 48);
    public static final Font DISPLAY_MEDIUM = new Font(FONT_FAMILY, 1, 36);
    public static final Font DISPLAY_SMALL = new Font(FONT_FAMILY, 1, 28);
    public static final Font HEADING_1 = new Font(FONT_FAMILY, 1, 24);
    public static final Font HEADING_2 = new Font(FONT_FAMILY, 1, 20);
    public static final Font HEADING_3 = new Font(FONT_FAMILY, 1, 18);
    public static final Font HEADING_4 = new Font(FONT_FAMILY, 1, 16);
    public static final Font BODY_LARGE = new Font(FONT_FAMILY, 0, 16);
    public static final Font BODY = new Font(FONT_FAMILY, 0, 14);
    public static final Font BODY_SMALL = new Font(FONT_FAMILY, 0, 12);
    public static final Font BUTTON_LARGE = new Font(FONT_FAMILY, 1, 16);
    public static final Font BUTTON = new Font(FONT_FAMILY, 1, 14);
    public static final Font BUTTON_SMALL = new Font(FONT_FAMILY, 1, 12);
    public static final Font CAPTION = new Font(FONT_FAMILY, 0, 11);
    public static final Font OVERLINE = new Font(FONT_FAMILY, 1, 10);
    public static final Font CODE = new Font("Monospaced", 0, 13);
    public static final Font CODE_SMALL = new Font("Monospaced", 0, 11);

    private static String getSansSerifFont() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("mac")) {
            return "SF Pro Display";
        }
        if (os.contains("win")) {
            return "Segoe UI";
        }
        return "Ubuntu";
    }

    public static Font custom(int style, int size) {
        return new Font(FONT_FAMILY, style, size);
    }
}
