/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.ui.components;

import javax.swing.table.DefaultTableModel;

class IngredientTable.1
extends DefaultTableModel {
    IngredientTable.1(Object[] $anonymous0, int $anonymous1) {
        super($anonymous0, $anonymous1);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
