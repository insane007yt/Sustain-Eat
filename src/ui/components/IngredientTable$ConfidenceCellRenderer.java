/*
 */
package com.foodanalyzer.ui.components;

import com.foodanalyzer.ui.util.AppColors;
import com.foodanalyzer.ui.util.AppFonts;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

private class IngredientTable.ConfidenceCellRenderer
extends DefaultTableCellRenderer {
    private IngredientTable.ConfidenceCellRenderer() {
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof Double) {
            double confidence = (Double)value;
            JPanel panel = new JPanel(new BorderLayout(5, 0));
            panel.setOpaque(true);
            panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            JProgressBar progressBar = new JProgressBar(0, 100);
            progressBar.setValue((int)(confidence * 100.0));
            progressBar.setStringPainted(false);
            progressBar.setPreferredSize(new Dimension(60, 20));
            progressBar.setForeground(AppColors.PRIMARY);
            progressBar.setBackground(AppColors.BORDER_LIGHT);
            JLabel label = new JLabel(String.format("%.0f%%", confidence * 100.0));
            label.setFont(AppFonts.BODY_SMALL);
            label.setForeground(isSelected ? table.getSelectionForeground() : AppColors.TEXT_SECONDARY);
            panel.add((Component)progressBar, "Center");
            panel.add((Component)label, "East");
            return panel;
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
