/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.services;

import com.foodanalyzer.model.AnalysisResult;
import com.foodanalyzer.model.IngredientClassification;
import com.foodanalyzer.model.Verdict;
import com.foodanalyzer.util.Config;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ScoreCalculator {
    private final Config config = Config.getInstance();

    public AnalysisResult calculate(List<IngredientClassification> ingredients) {
        if (ingredients == null || ingredients.isEmpty()) {
            return new AnalysisResult(BigDecimal.ZERO, Verdict.SAFE, 0, 0, 0, 0);
        }
        int harmfulCount = 0;
        int moderateCount = 0;
        int safeCount = 0;
        int unknownCount = 0;
        for (IngredientClassification ingredient : ingredients) {
            switch (ingredient.getCategory()) {
                case HARMFUL: {
                    ++harmfulCount;
                    break;
                }
                case MODERATE: {
                    ++moderateCount;
                    break;
                }
                case SAFE: {
                    ++safeCount;
                    break;
                }
                case UNKNOWN: {
                    ++unknownCount;
                }
            }
        }
        int totalCount = ingredients.size();
        double harmfulWeight = this.config.getHarmfulWeight();
        double moderateWeight = this.config.getModerateWeight();
        double penalty = (harmfulWeight * (double)harmfulCount + moderateWeight * (double)moderateCount) / (double)totalCount;
        double scoreValue = Math.max(0.0, Math.min(100.0, 100.0 * (1.0 - penalty)));
        BigDecimal score = BigDecimal.valueOf(scoreValue).setScale(2, RoundingMode.HALF_UP);
        Verdict verdict = this.determineVerdict(scoreValue);
        return new AnalysisResult(score, verdict, harmfulCount, moderateCount, safeCount, totalCount);
    }

    private Verdict determineVerdict(double score) {
        double safeThreshold = this.config.getSafeThreshold();
        double moderateThreshold = this.config.getModerateThreshold();
        if (score >= safeThreshold) {
            return Verdict.SAFE;
        }
        if (score >= moderateThreshold) {
            return Verdict.MODERATELY_HARMFUL;
        }
        return Verdict.HARMFUL;
    }
}
