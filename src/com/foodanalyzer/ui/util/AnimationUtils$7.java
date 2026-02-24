/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.ui.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;

static class AnimationUtils.7
implements ActionListener {
    private final /* synthetic */ int[] val$angle;
    private final /* synthetic */ JComponent val$component;

    AnimationUtils.7(int[] nArray, JComponent jComponent) {
        this.val$angle = nArray;
        this.val$component = jComponent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.val$angle[0] = (this.val$angle[0] + 5) % 360;
        this.val$component.putClientProperty("rotationAngle", this.val$angle[0]);
        this.val$component.repaint();
    }
}
