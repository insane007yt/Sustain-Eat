/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.model;

import com.foodanalyzer.model.Verdict;
import java.math.BigDecimal;

public class AnalysisResult {
    private final BigDecimal score;
    private final Verdict verdict;
    private final int harmfulCount;
    private final int moderateCount;
    private final int safeCount;
    private final int totalCount;

    public AnalysisResult(BigDecimal score, Verdict verdict, int harmfulCount, int moderateCount, int safeCount, int totalCount) {
        this.score = score;
        this.verdict = verdict;
        this.harmfulCount = harmfulCount;
        this.moderateCount = moderateCount;
        this.safeCount = safeCount;
        this.totalCount = totalCount;
    }

    public BigDecimal getScore() {
        return this.score;
    }

    public Verdict getVerdict() {
        return this.verdict;
    }

    public int getHarmfulCount() {
        return this.harmfulCount;
    }

    public int getModerateCount() {
        return this.moderateCount;
    }

    public int getSafeCount() {
        return this.safeCount;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public String toString() {
        return "AnalysisResult{score=" + String.valueOf(this.score) + ", verdict=" + String.valueOf((Object)this.verdict) + ", harmful=" + this.harmfulCount + ", moderate=" + this.moderateCount + ", safe=" + this.safeCount + ", total=" + this.totalCount + "}";
    }
}
