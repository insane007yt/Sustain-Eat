/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.ui.util;

import java.awt.Color;
import java.awt.GradientPaint;

public class AppColors {
    public static final Color PRIMARY = new Color(255, 149, 110);
    public static final Color PRIMARY_DARK = new Color(255, 118, 69);
    public static final Color PRIMARY_LIGHT = new Color(255, 183, 153);
    public static final Color ACCENT = new Color(255, 107, 129);
    public static final Color ACCENT_LIGHT = new Color(255, 184, 198);
    public static final Color SUCCESS = new Color(34, 197, 94);
    public static final Color SUCCESS_LIGHT = new Color(134, 239, 172);
    public static final Color WARNING = new Color(251, 191, 36);
    public static final Color WARNING_LIGHT = new Color(253, 224, 71);
    public static final Color DANGER = new Color(239, 68, 68);
    public static final Color DANGER_LIGHT = new Color(252, 165, 165);
    public static final Color INFO = new Color(59, 130, 246);
    public static final Color BACKGROUND_DARK = new Color(15, 23, 42);
    public static final Color BACKGROUND = new Color(255, 251, 248);
    public static final Color SURFACE = new Color(255, 255, 255);
    public static final Color SURFACE_DARK = new Color(255, 149, 110);
    public static final Color SURFACE_HOVER = new Color(255, 245, 240);
    public static final Color TEXT_PRIMARY = new Color(15, 23, 42);
    public static final Color TEXT_SECONDARY = new Color(100, 116, 139);
    public static final Color TEXT_TERTIARY = new Color(148, 163, 184);
    public static final Color TEXT_ON_PRIMARY = new Color(255, 255, 255);
    public static final Color TEXT_ON_DARK = new Color(248, 250, 252);
    public static final Color BORDER = new Color(226, 232, 240);
    public static final Color BORDER_LIGHT = new Color(241, 245, 249);
    public static final Color BORDER_FOCUS = PRIMARY;
    public static final Color SHADOW = new Color(0, 0, 0, 10);
    public static final Color SHADOW_MEDIUM = new Color(0, 0, 0, 25);
    public static final Color SHADOW_STRONG = new Color(0, 0, 0, 40);
    public static final Color GRADIENT_START = new Color(99, 102, 241);
    public static final Color GRADIENT_END = new Color(168, 85, 247);
    public static final Color SCORE_EXCELLENT = new Color(34, 197, 94);
    public static final Color SCORE_GOOD = new Color(132, 204, 22);
    public static final Color SCORE_MODERATE = new Color(251, 191, 36);
    public static final Color SCORE_POOR = new Color(249, 115, 22);
    public static final Color SCORE_HARMFUL = new Color(239, 68, 68);

    public static GradientPaint createGradient(int width, int height) {
        return new GradientPaint(0.0f, 0.0f, GRADIENT_START, width, height, GRADIENT_END);
    }

    public static Color getScoreColor(double score) {
        if (score >= 90.0) {
            return SCORE_EXCELLENT;
        }
        if (score >= 75.0) {
            return SCORE_GOOD;
        }
        if (score >= 50.0) {
            return SCORE_MODERATE;
        }
        if (score >= 30.0) {
            return SCORE_POOR;
        }
        return SCORE_HARMFUL;
    }

    public static Color withAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
}
