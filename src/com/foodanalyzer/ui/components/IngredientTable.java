/*
 * Decompiled with CFR 0.152.
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class IngredientTable
extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    public IngredientTable() {
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        this.initializeTable();
    }

    private void initializeTable() {
        Object[] columnNames = new String[]{"Ingredient", "Category", "Confidence", "Source", "Explanation"};
        this.tableModel = new DefaultTableModel(columnNames, 0){

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(this.tableModel);
        this.customizeTable();
        JScrollPane scrollPane = new JScrollPane(this.table);
        scrollPane.setBorder(BorderFactory.createLineBorder(AppColors.BORDER, 1));
        scrollPane.getViewport().setBackground(AppColors.SURFACE);
        this.add((Component)scrollPane, "Center");
    }

    private void customizeTable() {
        this.table.setFont(AppFonts.BODY);
        this.table.setRowHeight(50);
        this.table.setGridColor(AppColors.BORDER_LIGHT);
        this.table.setSelectionBackground(AppColors.SURFACE_HOVER);
        this.table.setSelectionForeground(AppColors.TEXT_PRIMARY);
        this.table.setShowGrid(true);
        this.table.setIntercellSpacing(new Dimension(1, 1));
        this.table.setBackground(AppColors.SURFACE);
        this.table.setForeground(AppColors.TEXT_PRIMARY);
        JTableHeader header = this.table.getTableHeader();
        header.setFont(AppFonts.HEADING_4);
        header.setBackground(AppColors.SURFACE_DARK);
        header.setForeground(AppColors.TEXT_ON_DARK);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 45));
        ((DefaultTableCellRenderer)header.getDefaultRenderer()).setHorizontalAlignment(2);
        this.table.getColumnModel().getColumn(0).setPreferredWidth(200);
        this.table.getColumnModel().getColumn(1).setPreferredWidth(120);
        this.table.getColumnModel().getColumn(2).setPreferredWidth(100);
        this.table.getColumnModel().getColumn(3).setPreferredWidth(100);
        this.table.getColumnModel().getColumn(4).setPreferredWidth(350);
        this.table.getColumnModel().getColumn(1).setCellRenderer(new CategoryCellRenderer());
        this.table.getColumnModel().getColumn(2).setCellRenderer(new ConfidenceCellRenderer());
        this.table.setAutoCreateRowSorter(true);
    }

    public void addIngredient(String name, String category, double confidence, String source, String explanation) {
        this.tableModel.addRow(new Object[]{name, category, confidence, source, explanation});
    }

    public void clearIngredients() {
        this.tableModel.setRowCount(0);
    }

    private class CategoryCellRenderer
    extends DefaultTableCellRenderer {
        private CategoryCellRenderer() {
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

    private class ConfidenceCellRenderer
    extends DefaultTableCellRenderer {
        private ConfidenceCellRenderer() {
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
}
