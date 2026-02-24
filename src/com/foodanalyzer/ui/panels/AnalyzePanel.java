/*
 */
package com.foodanalyzer.ui.panels;

import com.foodanalyzer.db.dao.AnalysisDAO;
import com.foodanalyzer.model.Analysis;
import com.foodanalyzer.model.AnalysisResult;
import com.foodanalyzer.model.IngredientClassification;
import com.foodanalyzer.model.InputType;
import com.foodanalyzer.services.IngredientAnalyzer;
import com.foodanalyzer.services.ScoreCalculator;
import com.foodanalyzer.ui.MainFrame;
import com.foodanalyzer.ui.util.AppColors;
import com.foodanalyzer.ui.util.AppFonts;
import com.foodanalyzer.ui.util.ComponentFactory;
import com.foodanalyzer.util.TextNormalizer;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AnalyzePanel
extends JPanel {
    private MainFrame mainFrame;
    private JPanel inputPanel;
    private JButton uploadImageBtn;
    private JButton typeTextBtn;
    private JButton pasteTextBtn;
    private JPanel imagePreviewPanel;
    private JTextArea ingredientTextArea;
    private JButton startAnalysisBtn;
    private JButton backBtn;
    private File selectedImageFile;
    private String selectedInputMode = "";
    private IngredientAnalyzer ingredientAnalyzer;
    private ScoreCalculator scoreCalculator;
    private AnalysisDAO analysisDAO;

    public AnalyzePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.setLayout(new BorderLayout());
        this.setBackground(AppColors.BACKGROUND);
        this.ingredientAnalyzer = new IngredientAnalyzer();
        this.scoreCalculator = new ScoreCalculator();
        this.analysisDAO = new AnalysisDAO();
        this.initializeComponents();
    }

    private void initializeComponents() {
        this.add((Component)this.createTopBar(), "North");
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, 1));
        contentPanel.setBackground(AppColors.BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));
        JPanel titlePanel = new JPanel(new FlowLayout(0));
        titlePanel.setOpaque(false);
        titlePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        JPanel titleContainer = new JPanel();
        titleContainer.setLayout(new BoxLayout(titleContainer, 1));
        titleContainer.setOpaque(false);
        JLabel titleLabel = new JLabel("Analyze Food Label");
        titleLabel.setFont(AppFonts.HEADING_1);
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        titleLabel.setAlignmentX(0.0f);
        JLabel subtitleLabel = new JLabel("Choose how you want to input the ingredient information");
        subtitleLabel.setFont(AppFonts.BODY);
        subtitleLabel.setForeground(AppColors.TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(0.0f);
        titleContainer.add(titleLabel);
        titleContainer.add(Box.createVerticalStrut(5));
        titleContainer.add(subtitleLabel);
        titlePanel.add(titleContainer);
        contentPanel.add(titlePanel);
        contentPanel.add(Box.createVerticalStrut(30));
        JPanel methodPanel = this.createInputMethodPanel();
        methodPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        methodPanel.setAlignmentX(0.5f);
        contentPanel.add(methodPanel);
        contentPanel.add(Box.createVerticalStrut(30));
        this.inputPanel = new JPanel(new BorderLayout());
        this.inputPanel.setOpaque(false);
        this.inputPanel.setPreferredSize(new Dimension(800, 350));
        this.inputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
        this.inputPanel.setAlignmentX(0.5f);
        contentPanel.add(this.inputPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        JPanel actionPanel = this.createActionPanel();
        actionPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        actionPanel.setAlignmentX(0.5f);
        contentPanel.add(actionPanel);
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(31);
        this.add((Component)scrollPane, "Center");
        this.showWelcomePanel();
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(AppColors.SURFACE);
        topBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.BORDER), BorderFactory.createEmptyBorder(15, 20, 15, 20)));
        this.backBtn = ComponentFactory.createTextButton("\u2190 Back to Home");
        this.backBtn.addActionListener(e -> this.mainFrame.navigateToLanding());
        topBar.add((Component)this.backBtn, "West");
        return topBar;
    }

    private JPanel createInputMethodPanel() {
        JPanel panel = new JPanel(new FlowLayout(1, 20, 10));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(900, 140));
        this.uploadImageBtn = this.createMethodButton("\ud83d\udcf8", "Upload Image", "Select a photo of the label");
        this.uploadImageBtn.addActionListener(e -> this.selectImageFile());
        this.typeTextBtn = this.createMethodButton("\u2328\ufe0f", "Type Ingredients", "Manually enter ingredients");
        this.typeTextBtn.addActionListener(e -> this.showTextInputPanel());
        this.pasteTextBtn = this.createMethodButton("\ud83d\udccb", "Paste Text", "Copy and paste ingredient list");
        this.pasteTextBtn.addActionListener(e -> this.showPastePanel());
        panel.add(this.uploadImageBtn);
        panel.add(this.typeTextBtn);
        panel.add(this.pasteTextBtn);
        return panel;
    }

    private JButton createMethodButton(final String icon, final String title, final String description) {
        JButton button = new JButton(){

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (this.getModel().isRollover()) {
                    g2.setColor(AppColors.SURFACE_HOVER);
                } else {
                    g2.setColor(AppColors.SURFACE);
                }
                g2.fill(new RoundRectangle2D.Double(0.0, 0.0, this.getWidth(), this.getHeight(), 12.0, 12.0));
                g2.setColor(AppColors.BORDER);
                g2.draw(new RoundRectangle2D.Double(0.0, 0.0, this.getWidth() - 1, this.getHeight() - 1, 12.0, 12.0));
                g2.dispose();
                Graphics2D g3 = (Graphics2D)g.create();
                g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int y = 30;
                g3.setFont(new Font("Dialog", 0, 32));
                FontMetrics fm = g3.getFontMetrics();
                int iconX = (this.getWidth() - fm.stringWidth(icon)) / 2;
                g3.drawString(icon, iconX, y);
                g3.setFont(AppFonts.HEADING_4);
                g3.setColor(AppColors.TEXT_PRIMARY);
                fm = g3.getFontMetrics();
                int titleX = (this.getWidth() - fm.stringWidth(title)) / 2;
                g3.drawString(title, titleX, y += 35);
                g3.setFont(AppFonts.BODY_SMALL);
                g3.setColor(AppColors.TEXT_SECONDARY);
                fm = g3.getFontMetrics();
                int descX = (this.getWidth() - fm.stringWidth(description)) / 2;
                g3.drawString(description, descX, y += 25);
                g3.dispose();
            }
        };
        button.setPreferredSize(new Dimension(200, 120));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(12));
        return button;
    }

    private void showWelcomePanel() {
        this.inputPanel.removeAll();
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        welcomePanel.setOpaque(false);
        JLabel welcomeLabel = new JLabel("\ud83d\udc46 Select an input method above to get started");
        welcomeLabel.setFont(AppFonts.BODY_LARGE);
        welcomeLabel.setForeground(AppColors.TEXT_SECONDARY);
        welcomePanel.add(welcomeLabel);
        this.inputPanel.add((Component)welcomePanel, "Center");
        this.inputPanel.revalidate();
        this.inputPanel.repaint();
        this.startAnalysisBtn.setEnabled(false);
    }

    private void selectImageFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Food Label Image");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(this);
        if (result == 0) {
            this.selectedImageFile = fileChooser.getSelectedFile();
            this.selectedInputMode = "image";
            this.showImagePreview();
        }
    }

    private void showImagePreview() {
        this.inputPanel.removeAll();
        JPanel previewPanel = new JPanel(new BorderLayout(10, 10));
        previewPanel.setOpaque(false);
        try {
            ImageIcon originalIcon = new ImageIcon(this.selectedImageFile.getAbsolutePath());
            Image scaledImage = originalIcon.getImage().getScaledInstance(400, 300, 4);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            imageLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(AppColors.BORDER, 1), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            JPanel imageContainer = new JPanel(new FlowLayout(1));
            imageContainer.setOpaque(false);
            imageContainer.add(imageLabel);
            previewPanel.add((Component)imageContainer, "Center");
            JLabel fileInfoLabel = new JLabel("\ud83d\udcc1 " + this.selectedImageFile.getName());
            fileInfoLabel.setFont(AppFonts.BODY);
            fileInfoLabel.setForeground(AppColors.TEXT_SECONDARY);
            fileInfoLabel.setHorizontalAlignment(0);
            previewPanel.add((Component)fileInfoLabel, "South");
        }
        catch (Exception e) {
            JLabel errorLabel = new JLabel("\u274c Failed to load image");
            errorLabel.setFont(AppFonts.BODY);
            errorLabel.setForeground(AppColors.DANGER);
            previewPanel.add((Component)errorLabel, "Center");
        }
        this.inputPanel.add((Component)previewPanel, "Center");
        this.inputPanel.revalidate();
        this.inputPanel.repaint();
        this.startAnalysisBtn.setEnabled(true);
    }

    private void showTextInputPanel() {
        this.inputPanel.removeAll();
        this.selectedInputMode = "text";
        JPanel textPanel = new JPanel(new BorderLayout(0, 10));
        textPanel.setOpaque(false);
        JLabel instructionLabel = new JLabel("Enter the ingredient list below:");
        instructionLabel.setFont(AppFonts.BODY);
        instructionLabel.setForeground(AppColors.TEXT_SECONDARY);
        textPanel.add((Component)instructionLabel, "North");
        this.ingredientTextArea = ComponentFactory.createTextArea("Example: Water, Sugar, Salt, Sodium Nitrite, Natural Flavors...", 10);
        JScrollPane scrollPane = ComponentFactory.createScrollPane(this.ingredientTextArea);
        scrollPane.setPreferredSize(new Dimension(700, 300));
        textPanel.add((Component)scrollPane, "Center");
        final JLabel charCountLabel = new JLabel("0 characters");
        charCountLabel.setFont(AppFonts.CAPTION);
        charCountLabel.setForeground(AppColors.TEXT_TERTIARY);
        this.ingredientTextArea.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void changedUpdate(DocumentEvent e) {
                this.update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                this.update();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                this.update();
            }

            public void update() {
                int length = AnalyzePanel.this.ingredientTextArea.getText().length();
                charCountLabel.setText(length + " characters");
                AnalyzePanel.this.startAnalysisBtn.setEnabled(length > 0);
            }
        });
        textPanel.add((Component)charCountLabel, "South");
        this.inputPanel.add((Component)textPanel, "Center");
        this.inputPanel.revalidate();
        this.inputPanel.repaint();
        this.startAnalysisBtn.setEnabled(false);
    }

    private void showPastePanel() {
        this.showTextInputPanel();
        this.selectedInputMode = "paste";
        try {
            String clipboardText = (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
            if (clipboardText != null && !clipboardText.isEmpty()) {
                this.ingredientTextArea.setText(clipboardText);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new FlowLayout(1, 15, 0));
        panel.setOpaque(false);
        this.startAnalysisBtn = ComponentFactory.createPrimaryButton("Start Analysis");
        this.startAnalysisBtn.setPreferredSize(new Dimension(200, 50));
        this.startAnalysisBtn.setEnabled(false);
        this.startAnalysisBtn.addActionListener(e -> this.startAnalysis());
        JButton clearBtn = ComponentFactory.createSecondaryButton("Clear");
        clearBtn.setPreferredSize(new Dimension(120, 50));
        clearBtn.addActionListener(e -> this.reset());
        panel.add(this.startAnalysisBtn);
        panel.add(clearBtn);
        return panel;
    }

    private void startAnalysis() {
        String rawInput;
        String string = rawInput = this.ingredientTextArea != null ? this.ingredientTextArea.getText() : "";
        if (rawInput.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter ingredients to analyze.", "No Input", 2);
            return;
        }
        final JDialog progressDialog = this.createProgressDialog();
        SwingWorker<Analysis, String> worker = new SwingWorker<Analysis, String>(){

            @Override
            protected Analysis doInBackground() throws Exception {
                this.publish("Parsing ingredients...");
                List<String> ingredientNames = TextNormalizer.parseIngredients(rawInput);
                if (ingredientNames.isEmpty()) {
                    throw new Exception("No valid ingredients found in input");
                }
                this.publish("Analyzing " + ingredientNames.size() + " ingredients...");
                List<IngredientClassification> classifications = AnalyzePanel.this.ingredientAnalyzer.analyze(ingredientNames);
                this.publish("Calculating health score...");
                AnalysisResult result = AnalyzePanel.this.scoreCalculator.calculate(classifications);
                this.publish("Saving analysis...");
                Analysis analysis = new Analysis();
                analysis.setTimestamp(LocalDateTime.now());
                analysis.setInputType(InputType.fromString(AnalyzePanel.this.selectedInputMode));
                analysis.setProductName(AnalyzePanel.this.extractProductName(rawInput));
                analysis.setRawInput(rawInput);
                analysis.setNormalizedIngredients(ingredientNames);
                analysis.setClassifiedIngredients(classifications);
                analysis.setHarmfulCount(result.getHarmfulCount());
                analysis.setModerateCount(result.getModerateCount());
                analysis.setSafeCount(result.getSafeCount());
                analysis.setTotalCount(result.getTotalCount());
                analysis.setHealthScore(result.getScore());
                analysis.setVerdict(result.getVerdict());
                Long id = AnalyzePanel.this.analysisDAO.save(analysis);
                analysis.setId(id);
                this.publish("Analysis complete!");
                return analysis;
            }

            @Override
            protected void process(List<String> chunks) {
                if (!chunks.isEmpty()) {
                    Component[] components;
                    Component[] componentArray = components = progressDialog.getContentPane().getComponents();
                    int n = components.length;
                    int n2 = 0;
                    while (n2 < n) {
                        Component comp = componentArray[n2];
                        if (comp instanceof JPanel) {
                            Component[] panelComps;
                            Component[] componentArray2 = panelComps = ((JPanel)comp).getComponents();
                            int n3 = panelComps.length;
                            int n4 = 0;
                            while (n4 < n3) {
                                Component pc = componentArray2[n4];
                                if (pc instanceof JLabel && ((JLabel)pc).getText().contains("Analyzing")) {
                                    ((JLabel)pc).setText(chunks.get(chunks.size() - 1));
                                }
                                ++n4;
                            }
                        }
                        ++n2;
                    }
                }
            }

            @Override
            protected void done() {
                progressDialog.dispose();
                try {
                    Analysis analysis = (Analysis)this.get();
                    AnalyzePanel.this.mainFrame.navigateToResults(analysis);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(AnalyzePanel.this, "Analysis failed: " + e.getMessage(), "Error", 0);
                }
            }
        };
        worker.execute();
        progressDialog.setVisible(true);
    }

    private String extractProductName(String input) {
        String firstLine;
        String[] lines = input.split("\\n");
        if (lines.length > 0 && !(firstLine = lines[0].trim()).toLowerCase().contains("ingredient") && firstLine.length() < 100) {
            return firstLine;
        }
        return "Unknown Product";
    }

    private JDialog createProgressDialog() {
        JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Analyzing...", true);
        dialog.setDefaultCloseOperation(0);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(AppColors.BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 0;
        gbc.anchor = 10;
        gbc.insets = new Insets(10, 0, 10, 0);
        JLabel iconLabel = new JLabel("\ud83d\udd0d");
        iconLabel.setFont(new Font("Dialog", 0, 48));
        panel.add((Component)iconLabel, gbc);
        JLabel messageLabel = new JLabel("Analyzing ingredients...");
        messageLabel.setFont(AppFonts.HEADING_3);
        messageLabel.setForeground(AppColors.TEXT_PRIMARY);
        panel.add((Component)messageLabel, gbc);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setPreferredSize(new Dimension(300, 8));
        panel.add((Component)progressBar, gbc);
        dialog.add(panel);
        return dialog;
    }

    public void reset() {
        this.selectedImageFile = null;
        this.selectedInputMode = "";
        if (this.ingredientTextArea != null) {
            this.ingredientTextArea.setText("");
        }
        this.showWelcomePanel();
    }
}
