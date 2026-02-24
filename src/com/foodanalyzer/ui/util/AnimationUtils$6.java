/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.ui.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.Timer;

static class AnimationUtils.6
implements ActionListener {
    private final /* synthetic */ float[] val$position;
    private final /* synthetic */ Timer val$timer;
    private final /* synthetic */ JComponent val$component;

    AnimationUtils.6(float[] fArray, Timer timer, JComponent jComponent) {
        this.val$position = fArray;
        this.val$timer = timer;
        this.val$component = jComponent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.val$position[0] = this.val$position[0] + 0.1f;
        if (this.val$position[0] >= 2.0f) {
            this.val$position[0] = -1.0f;
            this.val$timer.stop();
        }
        this.val$component.putClientProperty("shimmerPosition", Float.valueOf(this.val$position[0]));
        this.val$component.repaint();
    }
}
