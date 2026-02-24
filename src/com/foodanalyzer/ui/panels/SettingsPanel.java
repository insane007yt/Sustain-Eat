/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.ui.panels;

import com.foodanalyzer.db.DBManager;
import com.foodanalyzer.services.GeminiClient;
import com.foodanalyzer.ui.MainFrame;
import com.foodanalyzer.ui.util.AppColors;
import com.foodanalyzer.ui.util.AppFonts;
import com.foodanalyzer.ui.util.ComponentFactory;
import com.foodanalyzer.util.Config;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class SettingsPanel
extends JPanel {
    private MainFrame mainFrame;
    private Config config;
    private DBManager dbManager;
    private GeminiClient geminiClient;
    private JTextField dbHostField;
    private JTextField dbPortField;
    private JTextField dbNameField;
    private JTextField dbUserField;
    private JPasswordField dbPasswordField;
    private JTextField geminiApiKeyField;
    private JComboBox<String> modelSelector;
    private JSlider harmfulWeightSlider;
    private JSlider moderateWeightSlider;
    private JSlider safeThresholdSlider;
    private JSlider moderateThresholdSlider;
    private JLabel harmfulWeightLabel;
    private JLabel moderateWeightLabel;
    private JLabel safeThresholdLabel;
    private JLabel moderateThresholdLabel;

    public SettingsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.config = Config.getInstance();
        this.dbManager = DBManager.getInstance();
        this.geminiClient = new GeminiClient();
        this.setLayout(new BorderLayout());
        this.setBackground(AppColors.BACKGROUND);
        this.initializeComponents();
    }

    private void initializeComponents() {
        this.add((Component)this.createTopBar(), "North");
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, 1));
        contentPanel.setBackground(AppColors.BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));
        JPanel titlePanel = this.createTitlePanel();
        titlePanel.setAlignmentX(0.0f);
        contentPanel.add(titlePanel);
        contentPanel.add(Box.createVerticalStrut(20));
        JPanel dbCard = this.createDatabaseSettingsCard();
        dbCard.setAlignmentX(0.0f);
        contentPanel.add(dbCard);
        contentPanel.add(Box.createVerticalStrut(20));
        JPanel apiCard = this.createAPISettingsCard();
        apiCard.setAlignmentX(0.0f);
        contentPanel.add(apiCard);
        contentPanel.add(Box.createVerticalStrut(20));
        JPanel scoreCard = this.createScoreSettingsCard();
        scoreCard.setAlignmentX(0.0f);
        contentPanel.add(scoreCard);
        contentPanel.add(Box.createVerticalStrut(20));
        JPanel actionPanel = this.createActionPanel();
        actionPanel.setAlignmentX(0.0f);
        contentPanel.add(actionPanel);
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.add((Component)scrollPane, "Center");
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(AppColors.SURFACE);
        topBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.BORDER), BorderFactory.createEmptyBorder(15, 20, 15, 20)));
        JButton backBtn = ComponentFactory.createTextButton("\u2190 Back to Home");
        backBtn.addActionListener(e -> this.mainFrame.navigateToLanding());
        topBar.add((Component)backBtn, "West");
        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setFont(AppFonts.HEADING_3);
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        topBar.add((Component)titleLabel, "Center");
        return topBar;
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 0;
        gbc.anchor = 17;
        gbc.fill = 2;
        JLabel titleLabel = new JLabel("Application Settings");
        titleLabel.setFont(AppFonts.HEADING_1);
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        gbc.insets = new Insets(0, 0, 5, 0);
        panel.add((Component)titleLabel, gbc);
        JLabel subtitleLabel = new JLabel("Configure database, API, and analysis parameters");
        subtitleLabel.setFont(AppFonts.BODY);
        subtitleLabel.setForeground(AppColors.TEXT_SECONDARY);
        panel.add((Component)subtitleLabel, gbc);
        return panel;
    }

    private JPanel createDatabaseSettingsCard() {
        JPanel card = this.createSettingsCard("\ud83d\uddc4\ufe0f Database Configuration");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = 2;
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        card.add((Component)this.createLabel("Host:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        this.dbHostField = ComponentFactory.createTextField("localhost");
        this.dbHostField.setText("localhost");
        card.add((Component)this.dbHostField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        card.add((Component)this.createLabel("Port:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        this.dbPortField = ComponentFactory.createTextField("3306");
        this.dbPortField.setText("3306");
        card.add((Component)this.dbPortField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        card.add((Component)this.createLabel("Database:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        this.dbNameField = ComponentFactory.createTextField("food_analyzer");
        this.dbNameField.setText("food_analyzer");
        card.add((Component)this.dbNameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        card.add((Component)this.createLabel("Username:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        this.dbUserField = ComponentFactory.createTextField("root");
        this.dbUserField.setText("root");
        card.add((Component)this.dbUserField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.3;
        card.add((Component)this.createLabel("Password:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        this.dbPasswordField = new JPasswordField();
        this.dbPasswordField.setFont(AppFonts.BODY);
        this.dbPasswordField.setBorder(BorderFactory.createCompoundBorder(new ComponentFactory.RoundedBorder(AppColors.BORDER, 1, 8), BorderFactory.createEmptyBorder(12, 16, 12, 16)));
        this.dbPasswordField.setPreferredSize(new Dimension(300, 45));
        card.add((Component)this.dbPasswordField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.insets = new Insets(15, 0, 0, 0);
        JButton testBtn = ComponentFactory.createSecondaryButton("Test Connection");
        testBtn.addActionListener(e -> this.testDatabaseConnection());
        card.add((Component)testBtn, gbc);
        return card;
    }

    private JPanel createAPISettingsCard() {
        JPanel card = this.createSettingsCard("\ud83e\udd16 Gemini API Configuration");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = 2;
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        card.add((Component)this.createLabel("API Key:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        this.geminiApiKeyField = ComponentFactory.createTextField("Enter your Gemini API key");
        card.add((Component)this.geminiApiKeyField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        card.add((Component)this.createLabel("Model:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        String[] models = new String[]{"gemini-2.0-flash-exp", "gemini-1.5-flash", "gemini-1.5-pro", "gemini-pro", "gemini-pro-vision"};
        this.modelSelector = new JComboBox<String>(models);
        this.modelSelector.setFont(AppFonts.BODY);
        this.modelSelector.setPreferredSize(new Dimension(300, 45));
        card.add(this.modelSelector, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 0, 0, 0);
        JLabel helpLabel = new JLabel("<html><i>Get your API key from: https://makersuite.google.com/app/apikey</i></html>");
        helpLabel.setFont(AppFonts.CAPTION);
        helpLabel.setForeground(AppColors.TEXT_TERTIARY);
        card.add((Component)helpLabel, gbc);
        return card;
    }

    private JPanel createScoreSettingsCard() {
        JPanel card = this.createSettingsCard("\u2699\ufe0f Score Calculation Parameters");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = 2;
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        card.add((Component)this.createLabel("Harmful Weight:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        this.harmfulWeightSlider = this.createSlider(0, 30, 15);
        card.add((Component)this.harmfulWeightSlider, gbc);
        gbc.gridx = 2;
        gbc.weightx = 0.2;
        this.harmfulWeightLabel = this.createValueLabel("1.5");
        card.add((Component)this.harmfulWeightLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        card.add((Component)this.createLabel("Moderate Weight:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        this.moderateWeightSlider = this.createSlider(0, 20, 7);
        card.add((Component)this.moderateWeightSlider, gbc);
        gbc.gridx = 2;
        gbc.weightx = 0.2;
        this.moderateWeightLabel = this.createValueLabel("0.75");
        card.add((Component)this.moderateWeightLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        card.add((Component)this.createLabel("Safe Threshold:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        this.safeThresholdSlider = this.createSlider(50, 100, 80);
        card.add((Component)this.safeThresholdSlider, gbc);
        gbc.gridx = 2;
        gbc.weightx = 0.2;
        this.safeThresholdLabel = this.createValueLabel("80");
        card.add((Component)this.safeThresholdLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        card.add((Component)this.createLabel("Moderate Threshold:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        this.moderateThresholdSlider = this.createSlider(0, 80, 50);
        card.add((Component)this.moderateThresholdSlider, gbc);
        gbc.gridx = 2;
        gbc.weightx = 0.2;
        this.moderateThresholdLabel = this.createValueLabel("50");
        card.add((Component)this.moderateThresholdLabel, gbc);
        this.harmfulWeightSlider.addChangeListener(e -> this.harmfulWeightLabel.setText(String.format("%.1f", (double)this.harmfulWeightSlider.getValue() / 10.0)));
        this.moderateWeightSlider.addChangeListener(e -> this.moderateWeightLabel.setText(String.format("%.2f", (double)this.moderateWeightSlider.getValue() / 10.0)));
        this.safeThresholdSlider.addChangeListener(e -> this.safeThresholdLabel.setText(String.valueOf(this.safeThresholdSlider.getValue())));
        this.moderateThresholdSlider.addChangeListener(e -> this.moderateThresholdLabel.setText(String.valueOf(this.moderateThresholdSlider.getValue())));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(15, 0, 0, 0);
        JLabel infoLabel = new JLabel("<html><i>Formula: health_score = 100 \u00d7 (1 - (W_h \u00d7 harmful + W_m \u00d7 moderate) / total)</i></html>");
        infoLabel.setFont(AppFonts.CAPTION);
        infoLabel.setForeground(AppColors.TEXT_TERTIARY);
        card.add((Component)infoLabel, gbc);
        return card;
    }

    private JPanel createSettingsCard(String title) {
        JPanel card = new JPanel(new GridBagLayout()){

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
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        card.setMaximumSize(new Dimension(800, Integer.MAX_VALUE));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = 17;
        gbc.insets = new Insets(0, 0, 15, 0);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(AppFonts.HEADING_3);
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        card.add((Component)titleLabel, gbc);
        return card;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(AppFonts.BODY);
        label.setForeground(AppColors.TEXT_SECONDARY);
        return label;
    }

    private JLabel createValueLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(AppFonts.HEADING_4);
        label.setForeground(AppColors.PRIMARY);
        label.setHorizontalAlignment(0);
        return label;
    }

    private JSlider createSlider(int min, int max, int value) {
        JSlider slider = new JSlider(min, max, value);
        slider.setOpaque(false);
        slider.setPreferredSize(new Dimension(250, 40));
        return slider;
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new FlowLayout(0, 15, 0));
        panel.setOpaque(false);
        JButton saveBtn = ComponentFactory.createPrimaryButton("\ud83d\udcbe Save Settings");
        saveBtn.addActionListener(e -> this.saveSettings());
        JButton resetBtn = ComponentFactory.createSecondaryButton("\ud83d\udd04 Reset to Defaults");
        resetBtn.addActionListener(e -> this.resetSettings());
        panel.add(saveBtn);
        panel.add(resetBtn);
        return panel;
    }

    public void loadSettings() {
        this.dbHostField.setText(this.config.getString("db.host", "localhost"));
        this.dbPortField.setText(this.config.getString("db.port", "3306"));
        this.dbNameField.setText(this.config.getString("db.name", "food_analyzer"));
        this.dbUserField.setText(this.config.getString("db.username", "root"));
        this.dbPasswordField.setText(this.config.getString("db.password", ""));
        this.geminiApiKeyField.setText(this.config.getString("gemini.api.key", ""));
        String model = this.config.getString("gemini.model", "gemini-pro");
        this.modelSelector.setSelectedItem(model);
        this.harmfulWeightSlider.setValue((int)(this.config.getHarmfulWeight() * 10.0));
        this.moderateWeightSlider.setValue((int)(this.config.getModerateWeight() * 10.0));
        this.safeThresholdSlider.setValue((int)this.config.getSafeThreshold());
        this.moderateThresholdSlider.setValue((int)this.config.getModerateThreshold());
        this.harmfulWeightLabel.setText(String.format("%.1f", this.config.getHarmfulWeight()));
        this.moderateWeightLabel.setText(String.format("%.2f", this.config.getModerateWeight()));
        this.safeThresholdLabel.setText(String.valueOf((int)this.config.getSafeThreshold()));
        this.moderateThresholdLabel.setText(String.valueOf((int)this.config.getModerateThreshold()));
    }

    private void saveSettings() {
        try {
            this.config.setProperty("db.host", this.dbHostField.getText().trim());
            this.config.setProperty("db.port", this.dbPortField.getText().trim());
            this.config.setProperty("db.name", this.dbNameField.getText().trim());
            this.config.setProperty("db.username", this.dbUserField.getText().trim());
            this.config.setProperty("db.password", new String(this.dbPasswordField.getPassword()));
            String apiKey = this.geminiApiKeyField.getText().trim();
            this.config.setProperty("gemini.api.key", apiKey);
            this.config.setProperty("gemini.model", (String)this.modelSelector.getSelectedItem());
            this.config.setProperty("gemini.enabled", String.valueOf(!apiKey.isEmpty()));
            this.config.setProperty("score.harmful.weight", String.valueOf((double)this.harmfulWeightSlider.getValue() / 10.0));
            this.config.setProperty("score.moderate.weight", String.valueOf((double)this.moderateWeightSlider.getValue() / 10.0));
            this.config.setProperty("score.safe.threshold", String.valueOf(this.safeThresholdSlider.getValue()));
            this.config.setProperty("score.moderate.threshold", String.valueOf(this.moderateThresholdSlider.getValue()));
            this.config.saveConfig();
            JOptionPane.showMessageDialog(this, "Settings saved successfully!\n\nNote: Database and API changes will take effect on next analysis.\nScore calculation changes are immediate.", "Settings Saved", 1);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving settings: " + e.getMessage(), "Save Error", 0);
        }
    }

    private void resetSettings() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to reset all settings to defaults?", "Confirm Reset", 0, 2);
        if (confirm == 0) {
            this.dbHostField.setText("localhost");
            this.dbPortField.setText("3306");
            this.dbNameField.setText("food_analyzer");
            this.dbUserField.setText("root");
            this.dbPasswordField.setText("");
            this.geminiApiKeyField.setText("");
            this.modelSelector.setSelectedIndex(0);
            this.harmfulWeightSlider.setValue(15);
            this.moderateWeightSlider.setValue(7);
            this.safeThresholdSlider.setValue(80);
            this.moderateThresholdSlider.setValue(50);
            JOptionPane.showMessageDialog(this, "Settings reset to defaults.", "Reset Complete", 1);
        }
    }

    private void testDatabaseConnection() {
        final JDialog progressDialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Testing...", true);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.add(new JLabel("Testing database connection..."));
        progressDialog.add(panel);
        progressDialog.setSize(300, 120);
        progressDialog.setLocationRelativeTo(this);
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>(){

            @Override
            protected Boolean doInBackground() throws Exception {
                return SettingsPanel.this.dbManager.testConnection();
            }

            @Override
            protected void done() {
                progressDialog.dispose();
                try {
                    boolean success = (Boolean)this.get();
                    if (success) {
                        String stats = SettingsPanel.this.dbManager.getPoolStats();
                        JOptionPane.showMessageDialog(SettingsPanel.this, "\u2705 Database connection successful!\n\nConnection Details:\n\u2022 Host: " + SettingsPanel.this.config.getString("db.host") + "\n\u2022 Database: " + SettingsPanel.this.config.getString("db.name") + "\n\u2022 " + stats, "Connection Test", 1);
                    } else {
                        JOptionPane.showMessageDialog(SettingsPanel.this, "\u274c Database connection failed!\n\nPlease check your database settings and ensure MySQL is running.", "Connection Test", 0);
                    }
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(SettingsPanel.this, "\u274c Database connection failed:\n\n" + e.getMessage(), "Connection Test", 0);
                }
            }
        };
        worker.execute();
        progressDialog.setVisible(true);
    }
}
