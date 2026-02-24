/*
 */
package com.foodanalyzer.ui.util;

import com.foodanalyzer.ui.util.AppColors;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.border.AbstractBorder;

static class ComponentFactory.ShadowBorder
extends AbstractBorder {
    ComponentFactory.ShadowBorder() {
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(AppColors.SHADOW);
        g2.fillRoundRect(x + 2, y + 2, width - 4, height - 4, 16, 16);
        g2.setColor(AppColors.BORDER_LIGHT);
        g2.drawRoundRect(x, y, width - 1, height - 1, 16, 16);
        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(4, 4, 4, 4);
    }
}
