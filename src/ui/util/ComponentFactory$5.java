/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.ui.util;

import com.foodanalyzer.ui.util.AppColors;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

static class ComponentFactory.5
extends MouseAdapter {
    private final /* synthetic */ JButton val$button;

    ComponentFactory.5(JButton jButton) {
        this.val$button = jButton;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.val$button.setForeground(AppColors.PRIMARY);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.val$button.setForeground(AppColors.TEXT_SECONDARY);
    }
}
