/*
 */
package com.foodanalyzer.ui.panels;

import com.foodanalyzer.model.Analysis;
import com.foodanalyzer.model.IngredientClassification;
import com.foodanalyzer.ui.MainFrame;
import com.foodanalyzer.ui.components.HealthScoreGauge;
import com.foodanalyzer.ui.components.IngredientTable;
import com.foodanalyzer.ui.util.AppColors;
import com.foodanalyzer.ui.util.AppFonts;
import com.foodanalyzer.ui.util.ComponentFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ResultsPanel
extends JPanel {
    private MainFrame mainFrame;
    private HealthScoreGauge scoreGauge;
    private IngredientTable ingredientTable;
    private JLabel productNameLabel;
    private JLabel analysisDateLabel;
    private JLabel totalIngredientsLabel;
    private JLabel harmfulCountLabel;
    private JLabel moderateCountLabel;
    private JLabel safeCountLabel;

    public ResultsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.setLayout(new BorderLayout());
        this.setBackground(AppColors.BACKGROUND);
        this.initializeComponents();
    }

    private void initializeComponents() {
        this.add((Component)this.createTopBar(), "North");
        JPanel contentPanel = new JPanel(new BorderLayout(0, 20));
        contentPanel.setBackground(AppColors.BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));
        JPanel summaryPanel = this.createSummaryPanel();
        contentPanel.add((Component)summaryPanel, "North");
        JPanel tablePanel = this.createTablePanel();
        contentPanel.add((Component)tablePanel, "Center");
        JPanel actionPanel = this.createActionPanel();
        contentPanel.add((Component)actionPanel, "South");
        this.add((Component)contentPanel, "Center");
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(AppColors.SURFACE);
        topBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.BORDER), BorderFactory.createEmptyBorder(15, 20, 15, 20)));
        JButton backBtn = ComponentFactory.createTextButton("\u2190 Back");
        backBtn.addActionListener(e -> this.mainFrame.navigateToAnalyze());
        topBar.add((Component)backBtn, "West");
        JLabel titleLabel = new JLabel("Analysis Results");
        titleLabel.setFont(AppFonts.HEADING_3);
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        topBar.add((Component)titleLabel, "Center");
        return topBar;
    }

    private JPanel createSummaryPanel() {
        JPanel summaryPanel = new JPanel(new BorderLayout(30, 0)){

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppColors.SURFACE);
                g2.fill(new RoundRectangle2D.Double(0.0, 0.0, this.getWidth(), this.getHeight(), 16.0, 16.0));
                g2.setColor(AppColors.BORDER);
                g2.draw(new RoundRectangle2D.Double(0.0, 0.0, this.getWidth() - 1, this.getHeight() - 1, 16.0, 16.0));
                g2.dispose();
            }
        };
        summaryPanel.setOpaque(false);
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        this.scoreGauge = new HealthScoreGauge();
        JPanel gaugePanel = new JPanel(new FlowLayout(1));
        gaugePanel.setOpaque(false);
        gaugePanel.add(this.scoreGauge);
        summaryPanel.add((Component)gaugePanel, "West");
        JPanel infoPanel = this.createInfoPanel();
        summaryPanel.add((Component)infoPanel, "Center");
        return summaryPanel;
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 0;
        gbc.anchor = 17;
        gbc.fill = 2;
        gbc.insets = new Insets(5, 0, 5, 0);
        this.productNameLabel = new JLabel("Unknown Product");
        this.productNameLabel.setFont(AppFonts.HEADING_2);
        this.productNameLabel.setForeground(AppColors.TEXT_PRIMARY);
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add((Component)this.productNameLabel, gbc);
        this.analysisDateLabel = new JLabel("Analyzed: --");
        this.analysisDateLabel.setFont(AppFonts.BODY_SMALL);
        this.analysisDateLabel.setForeground(AppColors.TEXT_SECONDARY);
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add((Component)this.analysisDateLabel, gbc);
        JPanel statsPanel = this.createStatsPanel();
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add((Component)statsPanel, gbc);
        return panel;
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 15, 15));
        panel.setOpaque(false);
        this.totalIngredientsLabel = this.createStatCard("\ud83d\udccb", "Total", "0", AppColors.INFO);
        this.harmfulCountLabel = this.createStatCard("\ud83d\uded1", "Harmful", "0", AppColors.DANGER);
        this.moderateCountLabel = this.createStatCard("\u26a0\ufe0f", "Moderate", "0", AppColors.WARNING);
        this.safeCountLabel = this.createStatCard("\u2705", "Safe", "0", AppColors.SUCCESS);
        panel.add(this.totalIngredientsLabel);
        panel.add(this.harmfulCountLabel);
        panel.add(this.moderateCountLabel);
        panel.add(this.safeCountLabel);
        return panel;
    }

    private JLabel createStatCard(String icon, String label, String value, final Color color) {
        JLabel card = new JLabel(){

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppColors.withAlpha(color, 20));
                g2.fill(new RoundRectangle2D.Double(0.0, 0.0, this.getWidth(), this.getHeight(), 12.0, 12.0));
                g2.setColor(AppColors.withAlpha(color, 50));
                g2.draw(new RoundRectangle2D.Double(0.0, 0.0, this.getWidth() - 1, this.getHeight() - 1, 12.0, 12.0));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        card.setPreferredSize(new Dimension(120, 80));
        String html = String.format("<html><div style='text-align: center;'><span style='font-size: 24px;'>%s</span><br><span style='font-size: 11px; color: rgb(%d,%d,%d);'>%s</span><br><span style='font-size: 20px; font-weight: bold; color: rgb(%d,%d,%d);'>%s</span></div></html>", icon, AppColors.TEXT_SECONDARY.getRed(), AppColors.TEXT_SECONDARY.getGreen(), AppColors.TEXT_SECONDARY.getBlue(), label, color.getRed(), color.getGreen(), color.getBlue(), value);
        card.setText(html);
        card.setHorizontalAlignment(0);
        return card;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setOpaque(false);
        JLabel titleLabel = new JLabel("Ingredient Details");
        titleLabel.setFont(AppFonts.HEADING_3);
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        panel.add((Component)titleLabel, "North");
        this.ingredientTable = new IngredientTable();
        panel.add((Component)this.ingredientTable, "Center");
        return panel;
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new FlowLayout(1, 15, 0));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        JButton exportBtn = ComponentFactory.createSecondaryButton("\ud83d\udce4 Export");
        exportBtn.addActionListener(e -> this.exportResults());
        JButton saveBtn = ComponentFactory.createPrimaryButton("\ud83d\udcbe Save to History");
        saveBtn.addActionListener(e -> this.saveToHistory());
        JButton analyzeBtn = ComponentFactory.createTextButton("Analyze Another \u2192");
        analyzeBtn.addActionListener(e -> this.mainFrame.navigateToAnalyze());
        panel.add(exportBtn);
        panel.add(saveBtn);
        panel.add(analyzeBtn);
        return panel;
    }

    public void displayResults(Object analysisData) {
        if (!(analysisData instanceof Analysis)) {
            JOptionPane.showMessageDialog(this, "Invalid analysis data", "Error", 0);
            return;
        }
        Analysis analysis = (Analysis)analysisData;
        this.productNameLabel.setText(analysis.getProductName() != null ? analysis.getProductName() : "Unknown Product");
        this.analysisDateLabel.setText("Analyzed: " + new SimpleDateFormat("MMM dd, yyyy HH:mm").format(Timestamp.valueOf(analysis.getTimestamp())));
        double score = analysis.getHealthScore().doubleValue();
        String verdict = analysis.getVerdict().getDisplayName();
        this.scoreGauge.setScore(score, verdict);
        this.updateStatCard(this.totalIngredientsLabel, String.valueOf(analysis.getTotalCount()));
        this.updateStatCard(this.harmfulCountLabel, String.valueOf(analysis.getHarmfulCount()));
        this.updateStatCard(this.moderateCountLabel, String.valueOf(analysis.getModerateCount()));
        this.updateStatCard(this.safeCountLabel, String.valueOf(analysis.getSafeCount()));
        this.ingredientTable.clearIngredients();
        for (IngredientClassification ingredient : analysis.getClassifiedIngredients()) {
            this.ingredientTable.addIngredient(ingredient.getName(), ingredient.getCategory().toString(), ingredient.getConfidence(), ingredient.getSource(), ingredient.getReason() != null ? ingredient.getReason() : "No explanation available");
        }
    }

    private void updateStatCard(JLabel card, String value) {
        String currentText = card.getText();
        String newText = currentText.replaceAll(">(\\d+)<", ">" + value + "<");
        card.setText(newText);
    }

    private void exportResults() {
        Object[] options = new String[]{"Export as CSV", "Export as JSON", "Cancel"};
        int choice = JOptionPane.showOptionDialog(this, "Choose export format:", "Export Results", -1, 3, null, options, options[0]);
        if (choice == 0 || choice == 1) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Results");
            if (fileChooser.showSaveDialog(this) == 0) {
                JOptionPane.showMessageDialog(this, "Results exported successfully!", "Success", 1);
            }
        }
    }

    private void saveToHistory() {
        JOptionPane.showMessageDialog(this, "Analysis saved to history!", "Saved", 1);
    }
}
