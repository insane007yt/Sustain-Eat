/*
 */
package com.foodanalyzer.ui.util;

import com.foodanalyzer.ui.util.AppColors;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.Timer;

static class ComponentFactory.2
extends MouseAdapter {
    private final /* synthetic */ JButton val$button;

    ComponentFactory.2(JButton jButton) {
        this.val$button = jButton;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.val$button.setForeground(Color.WHITE);
        Timer scaleTimer = new Timer(10, null);
        float[] scale = new float[]{1.0f};
        scaleTimer.addActionListener(evt -> {
            fArray[0] = scale[0] + 0.02f;
            if (scale[0] >= 1.05f) {
                fArray[0] = 1.05f;
                scaleTimer.stop();
            }
            this.val$button.putClientProperty("hoverScale", Float.valueOf(scale[0]));
            this.val$button.repaint();
        });
        scaleTimer.start();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.val$button.setForeground(AppColors.TEXT_ON_PRIMARY);
        Timer scaleTimer = new Timer(10, null);
        float[] scale = new float[]{1.05f};
        scaleTimer.addActionListener(evt -> {
            fArray[0] = scale[0] - 0.02f;
            if (scale[0] <= 1.0f) {
                fArray[0] = 1.0f;
                scaleTimer.stop();
            }
            this.val$button.putClientProperty("hoverScale", Float.valueOf(scale[0]));
            this.val$button.repaint();
        });
        scaleTimer.start();
    }
}
