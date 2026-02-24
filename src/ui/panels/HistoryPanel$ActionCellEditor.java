/*
 */
package com.foodanalyzer.ui.panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

private class HistoryPanel.ActionCellEditor
extends AbstractCellEditor
implements TableCellEditor {
    private JPanel panel = new JPanel(new FlowLayout(1, 5, 5));
    private JButton viewBtn = new JButton("\ud83d\udc41\ufe0f");
    private JButton deleteBtn;
    private int currentRow;

    public HistoryPanel.ActionCellEditor() {
        this.viewBtn.setFont(new Font("Dialog", 0, 16));
        this.viewBtn.setPreferredSize(new Dimension(40, 35));
        this.viewBtn.setContentAreaFilled(false);
        this.viewBtn.setBorderPainted(false);
        this.viewBtn.addActionListener(e -> {
            HistoryPanel.this.viewRecord(this.currentRow);
            this.fireEditingStopped();
        });
        this.deleteBtn = new JButton("\ud83d\uddd1\ufe0f");
        this.deleteBtn.setFont(new Font("Dialog", 0, 16));
        this.deleteBtn.setPreferredSize(new Dimension(40, 35));
        this.deleteBtn.setContentAreaFilled(false);
        this.deleteBtn.setBorderPainted(false);
        this.deleteBtn.addActionListener(e -> {
            HistoryPanel.this.deleteRecord(this.currentRow);
            this.fireEditingStopped();
        });
        this.panel.add(this.viewBtn);
        this.panel.add(this.deleteBtn);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.currentRow = row;
        return this.panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }
}
