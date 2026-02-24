/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.ui.panels;

import com.foodanalyzer.ui.util.AppColors;
import com.foodanalyzer.ui.util.AppFonts;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

private class HistoryPanel.ScoreCellRenderer
extends DefaultTableCellRenderer {
    private HistoryPanel.ScoreCellRenderer() {
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value instanceof Integer) {
            int score = (Integer)value;
            JLabel label = (JLabel)c;
            label.setHorizontalAlignment(0);
            label.setFont(AppFonts.HEADING_3);
            label.setText(score + "/100");
            label.setForeground(AppColors.getScoreColor(score));
        }
        return c;
    }
}
