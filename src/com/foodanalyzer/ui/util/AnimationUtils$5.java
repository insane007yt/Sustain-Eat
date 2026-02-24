/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.ui.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.Timer;

static class AnimationUtils.5
implements ActionListener {
    private final /* synthetic */ int[] val$frame;
    private final /* synthetic */ JComponent val$component;
    private final /* synthetic */ int val$originalY;
    private final /* synthetic */ Timer val$timer;

    AnimationUtils.5(int[] nArray, JComponent jComponent, int n, Timer timer) {
        this.val$frame = nArray;
        this.val$component = jComponent;
        this.val$originalY = n;
        this.val$timer = timer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.val$frame[0] = this.val$frame[0] + 1;
        double angle = Math.toRadians(this.val$frame[0] * 10);
        int offset = (int)(Math.abs(Math.sin(angle)) * 20.0);
        if (this.val$frame[0] > 36) {
            this.val$component.setLocation(this.val$component.getX(), this.val$originalY);
            this.val$timer.stop();
        } else {
            this.val$component.setLocation(this.val$component.getX(), this.val$originalY - offset);
        }
    }
}
