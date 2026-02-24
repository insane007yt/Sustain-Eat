/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.ui.panels;

import com.foodanalyzer.ui.util.AppColors;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

class LandingPanel.3
extends JPanel {
    LandingPanel.3() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(AppColors.SHADOW);
        g2.fill(new RoundRectangle2D.Double(2.0, 2.0, this.getWidth() - 4, this.getHeight() - 4, 16.0, 16.0));
        g2.setColor(AppColors.SURFACE);
        g2.fill(new RoundRectangle2D.Double(0.0, 0.0, this.getWidth() - 1, this.getHeight() - 1, 16.0, 16.0));
        g2.setColor(AppColors.BORDER);
        g2.draw(new RoundRectangle2D.Double(0.0, 0.0, this.getWidth() - 1, this.getHeight() - 1, 16.0, 16.0));
        g2.dispose();
    }
}
