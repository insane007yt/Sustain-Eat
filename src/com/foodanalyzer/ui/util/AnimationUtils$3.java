/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.ui.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.Timer;

static class AnimationUtils.3
implements ActionListener {
    private final /* synthetic */ boolean[] val$growing;
    private final /* synthetic */ double[] val$scale;
    private final /* synthetic */ Timer val$timer;
    private final /* synthetic */ JComponent val$component;

    AnimationUtils.3(boolean[] blArray, double[] dArray, Timer timer, JComponent jComponent) {
        this.val$growing = blArray;
        this.val$scale = dArray;
        this.val$timer = timer;
        this.val$component = jComponent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.val$growing[0]) {
            this.val$scale[0] = this.val$scale[0] + 0.02;
            if (this.val$scale[0] >= 1.1) {
                this.val$growing[0] = false;
            }
        } else {
            this.val$scale[0] = this.val$scale[0] - 0.02;
            if (this.val$scale[0] <= 1.0) {
                this.val$scale[0] = 1.0;
                this.val$timer.stop();
            }
        }
        this.val$component.repaint();
    }
}
