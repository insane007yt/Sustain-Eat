/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.ui.panels;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class HistoryPanel.3
extends MouseAdapter {
    HistoryPanel.3() {
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
            HistoryPanel.this.viewSelectedRecord();
        }
    }
}
