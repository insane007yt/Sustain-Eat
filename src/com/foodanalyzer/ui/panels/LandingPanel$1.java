/*
 */
package com.foodanalyzer.ui.panels;

import com.foodanalyzer.ui.util.AppColors;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

class LandingPanel.1
extends JPanel {
    private float rotationAngle = 0.0f;
    private float floatOffset = 0.0f;

    LandingPanel.1() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int size = 120;
        g2.translate(0.0, this.floatOffset);
        g2.setColor(new Color(255, 149, 110, 30));
        g2.fillOval(5, 5, size, size);
        GradientPaint gradient = new GradientPaint(0.0f, 0.0f, AppColors.PRIMARY, size, size, AppColors.ACCENT);
        g2.setPaint(gradient);
        g2.fillOval(0, 0, size, size);
        g2.setColor(new Color(255, 255, 255, 40));
        g2.fillOval(10, 10, 40, 40);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Dialog", 0, 64));
        String icon = "\ud83c\udf4e";
        FontMetrics fm = g2.getFontMetrics();
        int x = (size - fm.stringWidth(icon)) / 2;
        int y = (size - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(icon, x, y);
        g2.dispose();
    }
}
