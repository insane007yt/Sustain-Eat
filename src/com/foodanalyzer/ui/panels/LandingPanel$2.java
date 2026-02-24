/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import javax.swing.JPanel;

class LandingPanel.2
implements ActionListener {
    private int frame = 0;
    private final /* synthetic */ JPanel val$logoPanel;

    LandingPanel.2(JPanel jPanel) {
        this.val$logoPanel = jPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ++this.frame;
        double angle = Math.toRadians(this.frame * 2);
        float offset = (float)(Math.sin(angle) * 5.0);
        try {
            Field field = this.val$logoPanel.getClass().getDeclaredField("floatOffset");
            field.setAccessible(true);
            field.setFloat(this.val$logoPanel, offset);
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.val$logoPanel.repaint();
    }
}
