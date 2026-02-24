/*
 */
package com.foodanalyzer.ui.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.border.AbstractBorder;

public static class ComponentFactory.RoundedBorder
extends AbstractBorder {
    private Color color;
    private int thickness;
    private int radius;

    public ComponentFactory.RoundedBorder(Color color, int thickness, int radius) {
        this.color = color;
        this.thickness = thickness;
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(this.color);
        g2.setStroke(new BasicStroke(this.thickness));
        g2.drawRoundRect(x, y, width - 1, height - 1, this.radius, this.radius);
        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.thickness, this.thickness, this.thickness, this.thickness);
    }
}
