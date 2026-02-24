/*
 */
package com.foodanalyzer.ui.panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

private class HistoryPanel.ActionCellRenderer
extends JPanel
implements TableCellRenderer {
    private JButton viewBtn;
    private JButton deleteBtn;

    public HistoryPanel.ActionCellRenderer() {
        this.setLayout(new FlowLayout(1, 5, 5));
        this.setOpaque(true);
        this.viewBtn = new JButton("\ud83d\udc41\ufe0f");
        this.viewBtn.setFont(new Font("Dialog", 0, 16));
        this.viewBtn.setPreferredSize(new Dimension(40, 35));
        this.viewBtn.setToolTipText("View");
        this.viewBtn.setContentAreaFilled(false);
        this.viewBtn.setBorderPainted(false);
        this.viewBtn.setFocusPainted(false);
        this.deleteBtn = new JButton("\ud83d\uddd1\ufe0f");
        this.deleteBtn.setFont(new Font("Dialog", 0, 16));
        this.deleteBtn.setPreferredSize(new Dimension(40, 35));
        this.deleteBtn.setToolTipText("Delete");
        this.deleteBtn.setContentAreaFilled(false);
        this.deleteBtn.setBorderPainted(false);
        this.deleteBtn.setFocusPainted(false);
        this.add(this.viewBtn);
        this.add(this.deleteBtn);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            this.setBackground(table.getSelectionBackground());
        } else {
            this.setBackground(table.getBackground());
        }
        return this;
    }
}
