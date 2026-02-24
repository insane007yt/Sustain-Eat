/*
 */
package com.foodanalyzer.ui.panels;

import com.foodanalyzer.ui.util.AppColors;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JLabel;

class ResultsPanel.2
extends JLabel {
    private final /* synthetic */ Color val$color;

    ResultsPanel.2(Color color) {
        this.val$color = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(AppColors.withAlpha(this.val$color, 20));
        g2.fill(new RoundRectangle2D.Double(0.0, 0.0, this.getWidth(), this.getHeight(), 12.0, 12.0));
        g2.setColor(AppColors.withAlpha(this.val$color, 50));
        g2.draw(new RoundRectangle2D.Double(0.0, 0.0, this.getWidth() - 1, this.getHeight() - 1, 12.0, 12.0));
        g2.dispose();
        super.paintComponent(g);
    }
}
