/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.ui.panels;

import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class AnalyzePanel.2
implements DocumentListener {
    private final /* synthetic */ JLabel val$charCountLabel;

    AnalyzePanel.2(JLabel jLabel) {
        this.val$charCountLabel = jLabel;
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        this.update();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        this.update();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        this.update();
    }

    public void update() {
        int length = AnalyzePanel.this.ingredientTextArea.getText().length();
        this.val$charCountLabel.setText(length + " characters");
        AnalyzePanel.this.startAnalysisBtn.setEnabled(length > 0);
    }
}
