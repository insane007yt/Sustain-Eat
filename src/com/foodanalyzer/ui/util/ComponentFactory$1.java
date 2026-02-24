/*
 */
package com.foodanalyzer.ui.util;

import com.foodanalyzer.ui.util.AppColors;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;

static class ComponentFactory.1
extends JButton {
    ComponentFactory.1(String $anonymous0) {
        super($anonymous0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradient = new GradientPaint(0.0f, 0.0f, AppColors.PRIMARY, this.getWidth(), this.getHeight(), AppColors.ACCENT);
        g2.setPaint(gradient);
        g2.fill(new RoundRectangle2D.Double(0.0, 0.0, this.getWidth(), this.getHeight(), 12.0, 12.0));
        g2.setColor(AppColors.TEXT_ON_PRIMARY);
        g2.setFont(this.getFont());
        FontMetrics fm = g2.getFontMetrics();
        int x = (this.getWidth() - fm.stringWidth(this.getText())) / 2;
        int y = (this.getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(this.getText(), x, y);
        g2.dispose();
    }
}
