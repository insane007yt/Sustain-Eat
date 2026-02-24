/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.ui;

import com.foodanalyzer.ui.panels.AnalyzePanel;
import com.foodanalyzer.ui.panels.HistoryPanel;
import com.foodanalyzer.ui.panels.LandingPanel;
import com.foodanalyzer.ui.panels.ResultsPanel;
import com.foodanalyzer.ui.panels.SettingsPanel;
import com.foodanalyzer.ui.util.AppColors;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame
extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private LandingPanel landingPanel;
    private AnalyzePanel analyzePanel;
    private ResultsPanel resultsPanel;
    private HistoryPanel historyPanel;
    private SettingsPanel settingsPanel;
    public static final String LANDING = "LANDING";
    public static final String ANALYZE = "ANALYZE";
    public static final String RESULTS = "RESULTS";
    public static final String HISTORY = "HISTORY";
    public static final String SETTINGS = "SETTINGS";

    public MainFrame() {
        this.initializeFrame();
        this.initializePanels();
        this.setupLayout();
    }

    private void initializeFrame() {
        this.setTitle("SustainEat - Sustainable Food Analyser");
        this.setDefaultCloseOperation(3);
        this.setSize(1200, 800);
        this.setMinimumSize(new Dimension(1000, 700));
        this.setLocationRelativeTo(null);
        this.setIconImage(this.createAppIcon());
        this.getContentPane().setBackground(AppColors.BACKGROUND);
    }

    private void initializePanels() {
        this.landingPanel = new LandingPanel(this);
        this.analyzePanel = new AnalyzePanel(this);
        this.resultsPanel = new ResultsPanel(this);
        this.historyPanel = new HistoryPanel(this);
        this.settingsPanel = new SettingsPanel(this);
    }

    private void setupLayout() {
        this.cardLayout = new CardLayout();
        this.contentPanel = new JPanel(this.cardLayout);
        this.contentPanel.setBackground(AppColors.BACKGROUND);
        this.contentPanel.add((Component)this.landingPanel, LANDING);
        this.contentPanel.add((Component)this.analyzePanel, ANALYZE);
        this.contentPanel.add((Component)this.resultsPanel, RESULTS);
        this.contentPanel.add((Component)this.historyPanel, HISTORY);
        this.contentPanel.add((Component)this.settingsPanel, SETTINGS);
        this.add(this.contentPanel);
        this.showPanel(LANDING);
    }

    public void showPanel(String panelName) {
        this.cardLayout.show(this.contentPanel, panelName);
        switch (panelName) {
            case "HISTORY": {
                this.historyPanel.refreshData();
                break;
            }
            case "SETTINGS": {
                this.settingsPanel.loadSettings();
            }
        }
    }

    public void navigateToAnalyze() {
        this.analyzePanel.reset();
        this.showPanel(ANALYZE);
    }

    public void navigateToResults(Object analysisData) {
        this.resultsPanel.displayResults(analysisData);
        this.showPanel(RESULTS);
    }

    public void navigateToHistory() {
        this.showPanel(HISTORY);
    }

    public void navigateToSettings() {
        this.showPanel(SETTINGS);
    }

    public void navigateToLanding() {
        this.showPanel(LANDING);
    }

    private Image createAppIcon() {
        int size = 64;
        BufferedImage icon = new BufferedImage(size, size, 2);
        Graphics2D g2 = icon.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradient = new GradientPaint(0.0f, 0.0f, AppColors.PRIMARY, size, size, AppColors.ACCENT);
        g2.setPaint(gradient);
        g2.fillOval(0, 0, size, size);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Dialog", 1, 32));
        g2.drawString("\ud83c\udf4e", 16, 44);
        g2.dispose();
        return icon;
    }
}
