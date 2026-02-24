/*
 */
package com.foodanalyzer.ui.util;

import com.foodanalyzer.ui.util.AnimationUtils;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

static class AnimationUtils.8
implements ActionListener {
    private final /* synthetic */ Component val$comp;

    AnimationUtils.8(Component component) {
        this.val$comp = component;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AnimationUtils.fadeIn(this.val$comp, 300);
        ((Timer)e.getSource()).stop();
    }
}
