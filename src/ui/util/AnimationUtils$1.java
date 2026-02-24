/*
 */
package com.foodanalyzer.ui.util;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.Timer;

static class AnimationUtils.1
implements ActionListener {
    private final /* synthetic */ float[] val$opacity;
    private final /* synthetic */ float val$increment;
    private final /* synthetic */ Timer val$timer;
    private final /* synthetic */ Component val$component;

    AnimationUtils.1(float[] fArray, float f, Timer timer, Component component) {
        this.val$opacity = fArray;
        this.val$increment = f;
        this.val$timer = timer;
        this.val$component = component;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.val$opacity[0] = this.val$opacity[0] + this.val$increment;
        if (this.val$opacity[0] >= 1.0f) {
            this.val$opacity[0] = 1.0f;
            this.val$timer.stop();
        }
        if (this.val$component instanceof JComponent) {
            ((JComponent)this.val$component).putClientProperty("opacity", Float.valueOf(this.val$opacity[0]));
            this.val$component.repaint();
        }
    }
}
