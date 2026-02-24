/*
 */
package com.foodanalyzer.ui.panels;

import javax.swing.table.DefaultTableModel;

class HistoryPanel.2
extends DefaultTableModel {
    HistoryPanel.2(Object[] $anonymous0, int $anonymous1) {
        super($anonymous0, $anonymous1);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 5;
    }

    @Override
    public Class<?> getColumnClass(int column) {
        if (column == 2) {
            return Integer.class;
        }
        if (column == 4) {
            return Integer.class;
        }
        return String.class;
    }
}
