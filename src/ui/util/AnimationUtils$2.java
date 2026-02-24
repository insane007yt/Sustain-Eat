/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.ui.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.Timer;

static class AnimationUtils.2
implements ActionListener {
    private final /* synthetic */ int[] val$currentY;
    private final /* synthetic */ int val$increment;
    private final /* synthetic */ int val$originalY;
    private final /* synthetic */ Timer val$timer;
    private final /* synthetic */ JComponent val$component;

    AnimationUtils.2(int[] nArray, int n, int n2, Timer timer, JComponent jComponent) {
        this.val$currentY = nArray;
        this.val$increment = n;
        this.val$originalY = n2;
        this.val$timer = timer;
        this.val$component = jComponent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.val$currentY[0] = this.val$currentY[0] - this.val$increment;
        if (this.val$currentY[0] <= this.val$originalY) {
            this.val$currentY[0] = this.val$originalY;
            this.val$timer.stop();
        }
        this.val$component.setLocation(this.val$component.getX(), this.val$currentY[0]);
    }
}
