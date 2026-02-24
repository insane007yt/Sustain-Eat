/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.model;

import com.foodanalyzer.model.RiskLevel;

public class IngredientClassification {
    private String name;
    private String normalized;
    private RiskLevel category;
    private double confidence;
    private String reason;
    private String source;

    public IngredientClassification() {
        this.confidence = 0.0;
        this.category = RiskLevel.UNKNOWN;
        this.source = "unknown";
    }

    public IngredientClassification(String name, String normalized, RiskLevel category, double confidence, String reason, String source) {
        this.name = name;
        this.normalized = normalized;
        this.category = category;
        this.confidence = confidence;
        this.reason = reason;
        this.source = source;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNormalized() {
        return this.normalized;
    }

    public void setNormalized(String normalized) {
        this.normalized = normalized;
    }

    public RiskLevel getCategory() {
        return this.category;
    }

    public void setCategory(RiskLevel category) {
        this.category = category != null ? category : RiskLevel.UNKNOWN;
    }

    public double getConfidence() {
        return this.confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = Math.max(0.0, Math.min(1.0, confidence));
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String toString() {
        return "IngredientClassification{name='" + this.name + "', category=" + String.valueOf((Object)this.category) + ", confidence=" + String.format("%.2f", this.confidence) + ", source='" + this.source + "'}";
    }
}
