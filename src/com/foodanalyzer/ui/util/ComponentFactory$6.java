/*
 */
package com.foodanalyzer.ui.util;

import com.foodanalyzer.ui.util.AppColors;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JTextField;

static class ComponentFactory.6
extends JTextField {
    private final /* synthetic */ String val$placeholder;

    ComponentFactory.6(String string) {
        this.val$placeholder = string;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.getText().isEmpty() && !this.isFocusOwner()) {
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setColor(AppColors.TEXT_TERTIARY);
            g2.setFont(this.getFont());
            g2.drawString(this.val$placeholder, this.getInsets().left, g.getFontMetrics().getMaxAscent() + this.getInsets().top);
            g2.dispose();
        }
    }
}
