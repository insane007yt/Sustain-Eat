/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.services;

import com.foodanalyzer.db.dao.IngredientDAO;
import com.foodanalyzer.model.Ingredient;
import com.foodanalyzer.model.IngredientClassification;
import com.foodanalyzer.model.RiskLevel;
import com.foodanalyzer.services.GeminiClient;
import com.foodanalyzer.util.Config;
import com.foodanalyzer.util.TextNormalizer;
import java.util.ArrayList;
import java.util.List;

public class IngredientAnalyzer {
    private final IngredientDAO ingredientDAO = new IngredientDAO();
    private final GeminiClient geminiClient = new GeminiClient();
    private final Config config = Config.getInstance();

    public List<IngredientClassification> analyze(List<String> ingredientNames) {
        List<IngredientClassification> results = new ArrayList<IngredientClassification>();
        if (ingredientNames == null || ingredientNames.isEmpty()) {
            return results;
        }
        if (this.config.isGeminiEnabled()) {
            try {
                String ingredientText = String.join((CharSequence)", ", ingredientNames);
                results = this.geminiClient.analyzeIngredients(ingredientText);
                for (IngredientClassification classification : results) {
                    this.enrichWithLocalData(classification);
                }
                System.out.println("\u2713 Gemini API analysis successful (" + results.size() + " ingredients)");
                return results;
            }
            catch (Exception e) {
                System.err.println("\u26a0 Gemini API failed, falling back to local: " + e.getMessage());
            }
        }
        for (String name : ingredientNames) {
            IngredientClassification classification = this.analyzeIngredient(name);
            results.add(classification);
        }
        System.out.println("\u2713 Local database analysis complete (" + results.size() + " ingredients)");
        return results;
    }

    public IngredientClassification analyzeIngredient(String ingredientName) {
        if (ingredientName == null || ingredientName.trim().isEmpty()) {
            return this.createUnknownClassification(ingredientName, "Empty ingredient name");
        }
        String normalized = TextNormalizer.normalize(ingredientName);
        Ingredient ingredient = this.ingredientDAO.findByName(normalized);
        if (ingredient == null) {
            ingredient = this.ingredientDAO.findBySynonym(normalized);
        }
        if (ingredient != null) {
            return this.createClassificationFromIngredient(ingredientName, ingredient);
        }
        return this.createUnknownClassification(ingredientName, "Ingredient not found in database. Manual review recommended.");
    }

    private IngredientClassification createClassificationFromIngredient(String originalName, Ingredient ingredient) {
        IngredientClassification classification = new IngredientClassification();
        classification.setName(originalName);
        classification.setNormalized(ingredient.getCanonicalName());
        classification.setCategory(ingredient.getRiskLevel());
        classification.setConfidence(0.95);
        classification.setSource("local");
        StringBuilder reason = new StringBuilder();
        if (ingredient.getCategory() != null) {
            reason.append(ingredient.getCategory()).append(". ");
        }
        if (ingredient.getHealthEffects() != null && !ingredient.getHealthEffects().isEmpty()) {
            Object healthEffects = ingredient.getHealthEffects();
            if (((String)healthEffects).length() > 150) {
                healthEffects = ((String)healthEffects).substring(0, 147) + "...";
            }
            reason.append((String)healthEffects);
        } else if (ingredient.getNotes() != null && !ingredient.getNotes().isEmpty()) {
            Object notes = ingredient.getNotes();
            if (((String)notes).length() > 150) {
                notes = ((String)notes).substring(0, 147) + "...";
            }
            reason.append((String)notes);
        }
        classification.setReason(reason.toString());
        return classification;
    }

    private IngredientClassification createUnknownClassification(String name, String reason) {
        IngredientClassification classification = new IngredientClassification();
        classification.setName(name);
        classification.setNormalized(TextNormalizer.normalize(name));
        classification.setCategory(RiskLevel.UNKNOWN);
        classification.setConfidence(0.0);
        classification.setSource("local");
        classification.setReason(reason);
        return classification;
    }

    public int getIngredientCount(RiskLevel riskLevel) {
        return this.ingredientDAO.findByRiskLevel(riskLevel).size();
    }

    public int getTotalIngredientCount() {
        return this.ingredientDAO.findAll().size();
    }

    private void enrichWithLocalData(IngredientClassification classification) {
        Ingredient localIngredient = this.ingredientDAO.findByName(classification.getNormalized());
        if (localIngredient == null) {
            localIngredient = this.ingredientDAO.findBySynonym(classification.getNormalized());
        }
        if (localIngredient != null) {
            if (classification.getCategory() == RiskLevel.UNKNOWN) {
                classification.setCategory(localIngredient.getRiskLevel());
                classification.setConfidence(0.9);
            }
            if (localIngredient.getHealthEffects() != null && !localIngredient.getHealthEffects().isEmpty()) {
                Object localInfo = localIngredient.getHealthEffects();
                if (((String)localInfo).length() > 100) {
                    localInfo = ((String)localInfo).substring(0, 97) + "...";
                }
                classification.setReason(classification.getReason() + " | Local: " + (String)localInfo);
            }
            classification.setSource("hybrid");
        }
    }
}
