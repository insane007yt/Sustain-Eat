/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.ui.panels;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class HistoryPanel.1
implements DocumentListener {
    HistoryPanel.1() {
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        HistoryPanel.this.filterTable();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        HistoryPanel.this.filterTable();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        HistoryPanel.this.filterTable();
    }
}
