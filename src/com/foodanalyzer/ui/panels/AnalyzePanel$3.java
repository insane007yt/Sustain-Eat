/*
 */
package com.foodanalyzer.ui.panels;

import com.foodanalyzer.model.Analysis;
import com.foodanalyzer.model.AnalysisResult;
import com.foodanalyzer.model.IngredientClassification;
import com.foodanalyzer.model.InputType;
import com.foodanalyzer.util.TextNormalizer;
import java.awt.Component;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

class AnalyzePanel.3
extends SwingWorker<Analysis, String> {
    private final /* synthetic */ String val$rawInput;
    private final /* synthetic */ JDialog val$progressDialog;

    AnalyzePanel.3(String string, JDialog jDialog) {
        this.val$rawInput = string;
        this.val$progressDialog = jDialog;
    }

    @Override
    protected Analysis doInBackground() throws Exception {
        this.publish("Parsing ingredients...");
        List<String> ingredientNames = TextNormalizer.parseIngredients(this.val$rawInput);
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
        analysis.setProductName(AnalyzePanel.this.extractProductName(this.val$rawInput));
        analysis.setRawInput(this.val$rawInput);
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
            Component[] componentArray = components = this.val$progressDialog.getContentPane().getComponents();
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
        this.val$progressDialog.dispose();
        try {
            Analysis analysis = (Analysis)this.get();
            AnalyzePanel.this.mainFrame.navigateToResults(analysis);
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(AnalyzePanel.this, "Analysis failed: " + e.getMessage(), "Error", 0);
        }
    }
}
