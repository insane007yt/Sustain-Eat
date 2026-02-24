/*
 */
package com.foodanalyzer.ui.components;

import com.foodanalyzer.ui.util.AppColors;
import com.foodanalyzer.ui.util.AppFonts;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

private class IngredientTable.CategoryCellRenderer
extends DefaultTableCellRenderer {
    private IngredientTable.CategoryCellRenderer() {
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c;
        block13: {
            c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (value == null || isSelected) break block13;
            String category = value.toString().toLowerCase();
            JLabel label = (JLabel)c;
            label.setOpaque(true);
            label.setHorizontalAlignment(0);
            label.setFont(AppFonts.BUTTON_SMALL);
            switch (category) {
                case "harmful": {
                    label.setBackground(AppColors.withAlpha(AppColors.DANGER, 30));
                    label.setForeground(AppColors.DANGER);
                    label.setText("\ud83d\uded1 Harmful");
                    break;
                }
                case "moderate": {
                    label.setBackground(AppColors.withAlpha(AppColors.WARNING, 30));
                    label.setForeground(AppColors.WARNING.darker());
                    label.setText("\u26a0\ufe0f Moderate");
                    break;
                }
                case "safe": {
                    label.setBackground(AppColors.withAlpha(AppColors.SUCCESS, 30));
                    label.setForeground(AppColors.SUCCESS.darker());
                    label.setText("\u2705 Safe");
                    break;
                }
                default: {
                    label.setBackground(AppColors.withAlpha(AppColors.TEXT_TERTIARY, 30));
                    label.setForeground(AppColors.TEXT_SECONDARY);
                    label.setText("\u2753 Unknown");
                }
            }
        }
        return c;
    }
}
