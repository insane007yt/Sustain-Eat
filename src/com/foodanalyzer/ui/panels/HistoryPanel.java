/*
 */
package com.foodanalyzer.ui.panels;

import com.foodanalyzer.db.dao.AnalysisDAO;
import com.foodanalyzer.model.Analysis;
import com.foodanalyzer.ui.MainFrame;
import com.foodanalyzer.ui.util.AppColors;
import com.foodanalyzer.ui.util.AppFonts;
import com.foodanalyzer.ui.util.ComponentFactory;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

public class HistoryPanel
extends JPanel {
    private MainFrame mainFrame;
    private JTable historyTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JLabel totalRecordsLabel;
    private AnalysisDAO analysisDAO;
    private List<Analysis> allAnalyses;

    public HistoryPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.analysisDAO = new AnalysisDAO();
        this.allAnalyses = new ArrayList<Analysis>();
        this.setLayout(new BorderLayout());
        this.setBackground(AppColors.BACKGROUND);
        this.initializeComponents();
    }

    private void initializeComponents() {
        this.add((Component)this.createTopBar(), "North");
        JPanel contentPanel = new JPanel(new BorderLayout(0, 20));
        contentPanel.setBackground(AppColors.BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));
        JPanel headerPanel = this.createHeaderPanel();
        contentPanel.add((Component)headerPanel, "North");
        JPanel tablePanel = this.createTablePanel();
        contentPanel.add((Component)tablePanel, "Center");
        JPanel footerPanel = this.createFooterPanel();
        contentPanel.add((Component)footerPanel, "South");
        this.add((Component)contentPanel, "Center");
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(AppColors.SURFACE);
        topBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.BORDER), BorderFactory.createEmptyBorder(15, 20, 15, 20)));
        JButton backBtn = ComponentFactory.createTextButton("\u2190 Back to Home");
        backBtn.addActionListener(e -> this.mainFrame.navigateToLanding());
        topBar.add((Component)backBtn, "West");
        JLabel titleLabel = new JLabel("Analysis History");
        titleLabel.setFont(AppFonts.HEADING_3);
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        topBar.add((Component)titleLabel, "Center");
        return topBar;
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setOpaque(false);
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 0;
        gbc.anchor = 17;
        gbc.fill = 2;
        JLabel titleLabel = new JLabel("Past Analyses");
        titleLabel.setFont(AppFonts.HEADING_2);
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        gbc.insets = new Insets(0, 0, 5, 0);
        titlePanel.add((Component)titleLabel, gbc);
        JLabel subtitleLabel = new JLabel("View and manage your analysis history");
        subtitleLabel.setFont(AppFonts.BODY);
        subtitleLabel.setForeground(AppColors.TEXT_SECONDARY);
        gbc.insets = new Insets(0, 0, 0, 0);
        titlePanel.add((Component)subtitleLabel, gbc);
        panel.add((Component)titlePanel, "West");
        JPanel searchPanel = new JPanel(new FlowLayout(2, 10, 0));
        searchPanel.setOpaque(false);
        this.searchField = ComponentFactory.createTextField("Search products...");
        this.searchField.setPreferredSize(new Dimension(250, 40));
        this.searchField.getDocument().addDocumentListener(new DocumentListener(){

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
        });
        JButton refreshBtn = ComponentFactory.createIconButton("\ud83d\udd04");
        refreshBtn.setToolTipText("Refresh");
        refreshBtn.addActionListener(e -> this.refreshData());
        JButton deleteBtn = ComponentFactory.createIconButton("\ud83d\uddd1\ufe0f");
        deleteBtn.setToolTipText("Delete Selected");
        deleteBtn.addActionListener(e -> this.deleteSelected());
        searchPanel.add(this.searchField);
        searchPanel.add(refreshBtn);
        searchPanel.add(deleteBtn);
        panel.add((Component)searchPanel, "East");
        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        Object[] columnNames = new String[]{"Date & Time", "Product Name", "Health Score", "Verdict", "Ingredients", "Actions"};
        this.tableModel = new DefaultTableModel(columnNames, 0){

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
        };
        this.historyTable = new JTable(this.tableModel);
        this.customizeTable();
        JScrollPane scrollPane = new JScrollPane(this.historyTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(AppColors.BORDER, 1));
        scrollPane.getViewport().setBackground(AppColors.SURFACE);
        panel.add((Component)scrollPane, "Center");
        return panel;
    }

    private void customizeTable() {
        this.historyTable.setFont(AppFonts.BODY);
        this.historyTable.setRowHeight(60);
        this.historyTable.setGridColor(AppColors.BORDER_LIGHT);
        this.historyTable.setSelectionBackground(AppColors.SURFACE_HOVER);
        this.historyTable.setSelectionForeground(AppColors.TEXT_PRIMARY);
        this.historyTable.setShowGrid(true);
        this.historyTable.setIntercellSpacing(new Dimension(1, 1));
        this.historyTable.setBackground(AppColors.SURFACE);
        JTableHeader header = this.historyTable.getTableHeader();
        header.setFont(AppFonts.HEADING_4);
        header.setBackground(AppColors.SURFACE_DARK);
        header.setForeground(AppColors.TEXT_ON_DARK);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 45));
        this.historyTable.getColumnModel().getColumn(0).setPreferredWidth(180);
        this.historyTable.getColumnModel().getColumn(1).setPreferredWidth(250);
        this.historyTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        this.historyTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        this.historyTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        this.historyTable.getColumnModel().getColumn(5).setPreferredWidth(150);
        this.historyTable.getColumnModel().getColumn(2).setCellRenderer(new ScoreCellRenderer());
        this.historyTable.getColumnModel().getColumn(3).setCellRenderer(new VerdictCellRenderer());
        this.historyTable.getColumnModel().getColumn(5).setCellRenderer(new ActionCellRenderer());
        this.historyTable.getColumnModel().getColumn(5).setCellEditor(new ActionCellEditor());
        this.historyTable.setAutoCreateRowSorter(true);
        this.historyTable.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    HistoryPanel.this.viewSelectedRecord();
                }
            }
        });
    }

    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new FlowLayout(0));
        panel.setOpaque(false);
        this.totalRecordsLabel = new JLabel("Total: 0 records");
        this.totalRecordsLabel.setFont(AppFonts.BODY);
        this.totalRecordsLabel.setForeground(AppColors.TEXT_SECONDARY);
        panel.add(this.totalRecordsLabel);
        return panel;
    }

    public void refreshData() {
        this.tableModel.setRowCount(0);
        try {
            this.allAnalyses = this.analysisDAO.findAll(100);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm");
            for (Analysis analysis : this.allAnalyses) {
                this.tableModel.addRow(new Object[]{dateFormat.format(Timestamp.valueOf(analysis.getTimestamp())), analysis.getProductName() != null ? analysis.getProductName() : "Unknown Product", analysis.getHealthScore().intValue(), analysis.getVerdict().getDisplayName(), analysis.getTotalCount(), ""});
            }
            if (this.allAnalyses.isEmpty()) {
                System.out.println("No analysis history found. Perform some analyses to see history.");
            }
        }
        catch (Exception e) {
            System.err.println("Error loading history: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Failed to load history from database: " + e.getMessage(), "Database Error", 0);
        }
        this.updateRecordCount();
    }

    private void filterTable() {
        String searchText = this.searchField.getText().toLowerCase();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(this.tableModel);
        this.historyTable.setRowSorter(sorter);
        if (searchText.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, new int[0]));
        }
        this.updateRecordCount();
    }

    private void updateRecordCount() {
        int total = this.historyTable.getRowCount();
        this.totalRecordsLabel.setText(String.format("Total: %d record%s", total, total != 1 ? "s" : ""));
    }

    private void viewSelectedRecord() {
        int selectedRow = this.historyTable.getSelectedRow();
        if (selectedRow >= 0) {
            this.viewRecord(selectedRow);
        }
    }

    private void viewRecord(int row) {
        if (row >= 0 && row < this.allAnalyses.size()) {
            Analysis analysis = this.allAnalyses.get(row);
            this.mainFrame.navigateToResults(analysis);
        }
    }

    private void deleteSelected() {
        int selectedRow = this.historyTable.getSelectedRow();
        if (selectedRow >= 0) {
            this.deleteRecord(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a record to delete.", "No Selection", 2);
        }
    }

    private void deleteRecord(int row) {
        if (row < 0 || row >= this.allAnalyses.size()) {
            return;
        }
        Analysis analysis = this.allAnalyses.get(row);
        String productName = analysis.getProductName() != null ? analysis.getProductName() : "Unknown Product";
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the analysis for:\n" + productName + "?", "Confirm Delete", 0, 2);
        if (confirm == 0) {
            try {
                boolean deleted = this.analysisDAO.delete(analysis.getId());
                if (deleted) {
                    this.tableModel.removeRow(row);
                    this.allAnalyses.remove(row);
                    this.updateRecordCount();
                    JOptionPane.showMessageDialog(this, "Record deleted successfully!", "Deleted", 1);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete record from database.", "Error", 0);
                }
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting record: " + e.getMessage(), "Error", 0);
            }
        }
    }

    private class ActionCellEditor
    extends AbstractCellEditor
    implements TableCellEditor {
        private JPanel panel = new JPanel(new FlowLayout(1, 5, 5));
        private JButton viewBtn = new JButton("\ud83d\udc41\ufe0f");
        private JButton deleteBtn;
        private int currentRow;

        public ActionCellEditor() {
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

    private class ActionCellRenderer
    extends JPanel
    implements TableCellRenderer {
        private JButton viewBtn;
        private JButton deleteBtn;

        public ActionCellRenderer() {
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

    private class ScoreCellRenderer
    extends DefaultTableCellRenderer {
        private ScoreCellRenderer() {
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

    private class VerdictCellRenderer
    extends DefaultTableCellRenderer {
        private VerdictCellRenderer() {
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c;
            block13: {
                c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value == null || isSelected) break block13;
                String verdict = value.toString();
                JLabel label = (JLabel)c;
                label.setOpaque(true);
                label.setHorizontalAlignment(0);
                label.setFont(AppFonts.BUTTON_SMALL);
                switch (verdict.toLowerCase()) {
                    case "harmful": {
                        label.setBackground(AppColors.withAlpha(AppColors.DANGER, 30));
                        label.setForeground(AppColors.DANGER);
                        label.setText("\ud83d\uded1 " + verdict);
                        break;
                    }
                    case "moderately harmful": {
                        label.setBackground(AppColors.withAlpha(AppColors.WARNING, 30));
                        label.setForeground(AppColors.WARNING.darker());
                        label.setText("\u26a0\ufe0f " + verdict);
                        break;
                    }
                    case "safe": {
                        label.setBackground(AppColors.withAlpha(AppColors.SUCCESS, 30));
                        label.setForeground(AppColors.SUCCESS.darker());
                        label.setText("\u2705 " + verdict);
                    }
                }
            }
            return c;
        }
    }
}
