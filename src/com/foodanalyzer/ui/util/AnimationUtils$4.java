/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.ui.util;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.Timer;

static class AnimationUtils.4
implements ActionListener {
    private final /* synthetic */ int[] val$currentStep;
    private final /* synthetic */ int val$steps;
    private final /* synthetic */ Color val$from;
    private final /* synthetic */ int val$rDiff;
    private final /* synthetic */ int val$gDiff;
    private final /* synthetic */ int val$bDiff;
    private final /* synthetic */ JComponent val$component;
    private final /* synthetic */ Color val$to;
    private final /* synthetic */ Timer val$timer;

    AnimationUtils.4(int[] nArray, int n, Color color, int n2, int n3, int n4, JComponent jComponent, Color color2, Timer timer) {
        this.val$currentStep = nArray;
        this.val$steps = n;
        this.val$from = color;
        this.val$rDiff = n2;
        this.val$gDiff = n3;
        this.val$bDiff = n4;
        this.val$component = jComponent;
        this.val$to = color2;
        this.val$timer = timer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.val$currentStep[0] = this.val$currentStep[0] + 1;
        float progress = (float)this.val$currentStep[0] / (float)this.val$steps;
        int r = this.val$from.getRed() + (int)((float)this.val$rDiff * progress);
        int g = this.val$from.getGreen() + (int)((float)this.val$gDiff * progress);
        int b = this.val$from.getBlue() + (int)((float)this.val$bDiff * progress);
        this.val$component.setBackground(new Color(r, g, b));
        if (this.val$currentStep[0] >= this.val$steps) {
            this.val$component.setBackground(this.val$to);
            this.val$timer.stop();
        }
    }
}
