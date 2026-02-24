/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.ui.util;

import com.foodanalyzer.ui.util.AnimationUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

static class AnimationUtils.9
implements ActionListener {
    private final /* synthetic */ int[] val$currentStep;
    private final /* synthetic */ int val$steps;
    private final /* synthetic */ int val$from;
    private final /* synthetic */ int val$diff;
    private final /* synthetic */ AnimationUtils.ValueAnimationListener val$listener;
    private final /* synthetic */ int val$to;
    private final /* synthetic */ Timer val$timer;

    AnimationUtils.9(int[] nArray, int n, int n2, int n3, AnimationUtils.ValueAnimationListener valueAnimationListener, int n4, Timer timer) {
        this.val$currentStep = nArray;
        this.val$steps = n;
        this.val$from = n2;
        this.val$diff = n3;
        this.val$listener = valueAnimationListener;
        this.val$to = n4;
        this.val$timer = timer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.val$currentStep[0] = this.val$currentStep[0] + 1;
        float progress = (float)this.val$currentStep[0] / (float)this.val$steps;
        int currentValue = this.val$from + (int)((float)this.val$diff * AnimationUtils.easeInOutCubic(progress));
        this.val$listener.onValueUpdate(currentValue);
        if (this.val$currentStep[0] >= this.val$steps) {
            this.val$listener.onValueUpdate(this.val$to);
            this.val$timer.stop();
        }
    }
}
