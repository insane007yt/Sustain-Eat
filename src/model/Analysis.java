/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.model;

import com.foodanalyzer.model.IngredientClassification;
import com.foodanalyzer.model.InputType;
import com.foodanalyzer.model.Verdict;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Analysis {
    private Long id;
    private LocalDateTime timestamp = LocalDateTime.now();
    private InputType inputType;
    private String productName;
    private String rawInput;
    private List<String> normalizedIngredients = new ArrayList<String>();
    private List<IngredientClassification> classifiedIngredients = new ArrayList<IngredientClassification>();
    private int harmfulCount;
    private int moderateCount;
    private int safeCount;
    private int totalCount;
    private BigDecimal healthScore = BigDecimal.ZERO;
    private Verdict verdict = Verdict.SAFE;
    private String geminiResponse;
    private String imagePath;
    private String userNote;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public InputType getInputType() {
        return this.inputType;
    }

    public void setInputType(InputType inputType) {
        this.inputType = inputType;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRawInput() {
        return this.rawInput;
    }

    public void setRawInput(String rawInput) {
        this.rawInput = rawInput;
    }

    public List<String> getNormalizedIngredients() {
        return this.normalizedIngredients;
    }

    public void setNormalizedIngredients(List<String> normalizedIngredients) {
        this.normalizedIngredients = normalizedIngredients != null ? normalizedIngredients : new ArrayList();
    }

    public List<IngredientClassification> getClassifiedIngredients() {
        return this.classifiedIngredients;
    }

    public void setClassifiedIngredients(List<IngredientClassification> classifiedIngredients) {
        this.classifiedIngredients = classifiedIngredients != null ? classifiedIngredients : new ArrayList();
    }

    public int getHarmfulCount() {
        return this.harmfulCount;
    }

    public void setHarmfulCount(int harmfulCount) {
        this.harmfulCount = harmfulCount;
    }

    public int getModerateCount() {
        return this.moderateCount;
    }

    public void setModerateCount(int moderateCount) {
        this.moderateCount = moderateCount;
    }

    public int getSafeCount() {
        return this.safeCount;
    }

    public void setSafeCount(int safeCount) {
        this.safeCount = safeCount;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getHealthScore() {
        return this.healthScore;
    }

    public void setHealthScore(BigDecimal healthScore) {
        this.healthScore = healthScore != null ? healthScore : BigDecimal.ZERO;
    }

    public Verdict getVerdict() {
        return this.verdict;
    }

    public void setVerdict(Verdict verdict) {
        this.verdict = verdict != null ? verdict : Verdict.SAFE;
    }

    public String getGeminiResponse() {
        return this.geminiResponse;
    }

    public void setGeminiResponse(String geminiResponse) {
        this.geminiResponse = geminiResponse;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getUserNote() {
        return this.userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String toString() {
        return "Analysis{id=" + String.valueOf(this.id) + ", productName='" + this.productName + "', healthScore=" + String.valueOf(this.healthScore) + ", verdict=" + String.valueOf((Object)this.verdict) + ", totalCount=" + this.totalCount + ", timestamp=" + String.valueOf(this.timestamp) + "}";
    }
}
