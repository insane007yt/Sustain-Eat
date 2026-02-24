/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.ui.panels;

import com.foodanalyzer.ui.util.AppColors;
import com.foodanalyzer.ui.util.AppFonts;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;

class AnalyzePanel.1
extends JButton {
    private final /* synthetic */ String val$icon;
    private final /* synthetic */ String val$title;
    private final /* synthetic */ String val$description;

    AnalyzePanel.1(String string, String string2, String string3) {
        this.val$icon = string;
        this.val$title = string2;
        this.val$description = string3;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (this.getModel().isRollover()) {
            g2.setColor(AppColors.SURFACE_HOVER);
        } else {
            g2.setColor(AppColors.SURFACE);
        }
        g2.fill(new RoundRectangle2D.Double(0.0, 0.0, this.getWidth(), this.getHeight(), 12.0, 12.0));
        g2.setColor(AppColors.BORDER);
        g2.draw(new RoundRectangle2D.Double(0.0, 0.0, this.getWidth() - 1, this.getHeight() - 1, 12.0, 12.0));
        g2.dispose();
        Graphics2D g3 = (Graphics2D)g.create();
        g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int y = 30;
        g3.setFont(new Font("Dialog", 0, 32));
        FontMetrics fm = g3.getFontMetrics();
        int iconX = (this.getWidth() - fm.stringWidth(this.val$icon)) / 2;
        g3.drawString(this.val$icon, iconX, y);
        g3.setFont(AppFonts.HEADING_4);
        g3.setColor(AppColors.TEXT_PRIMARY);
        fm = g3.getFontMetrics();
        int titleX = (this.getWidth() - fm.stringWidth(this.val$title)) / 2;
        g3.drawString(this.val$title, titleX, y += 35);
        g3.setFont(AppFonts.BODY_SMALL);
        g3.setColor(AppColors.TEXT_SECONDARY);
        fm = g3.getFontMetrics();
        int descX = (this.getWidth() - fm.stringWidth(this.val$description)) / 2;
        g3.drawString(this.val$description, descX, y += 25);
        g3.dispose();
    }
}
