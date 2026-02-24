/*
 */
package com.foodanalyzer.model;

import com.foodanalyzer.model.RiskLevel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Ingredient {
    private Long id;
    private String canonicalName;
    private List<String> synonyms = new ArrayList<String>();
    private RiskLevel riskLevel = RiskLevel.UNKNOWN;
    private String category;
    private String notes;
    private String scientificName;
    private String commonUses;
    private String healthEffects;
    private boolean isActive = true;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Ingredient() {
    }

    public Ingredient(String canonicalName, RiskLevel riskLevel) {
        this();
        this.canonicalName = canonicalName;
        this.riskLevel = riskLevel;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCanonicalName() {
        return this.canonicalName;
    }

    public void setCanonicalName(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    public List<String> getSynonyms() {
        return this.synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms != null ? synonyms : new ArrayList();
    }

    public RiskLevel getRiskLevel() {
        return this.riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel != null ? riskLevel : RiskLevel.UNKNOWN;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getScientificName() {
        return this.scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getCommonUses() {
        return this.commonUses;
    }

    public void setCommonUses(String commonUses) {
        this.commonUses = commonUses;
    }

    public String getHealthEffects() {
        return this.healthEffects;
    }

    public void setHealthEffects(String healthEffects) {
        this.healthEffects = healthEffects;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
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
        return "Ingredient{id=" + String.valueOf(this.id) + ", canonicalName='" + this.canonicalName + "', riskLevel=" + String.valueOf((Object)this.riskLevel) + ", category='" + this.category + "'}";
    }
}
