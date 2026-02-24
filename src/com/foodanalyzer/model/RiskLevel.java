/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.model;

public enum RiskLevel {
    HARMFUL("harmful"),
    MODERATE("moderate"),
    SAFE("safe"),
    UNKNOWN("unknown");

    private final String value;

    private RiskLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static RiskLevel fromString(String text) {
        if (text == null) {
            return UNKNOWN;
        }
        RiskLevel[] riskLevelArray = RiskLevel.values();
        int n = riskLevelArray.length;
        int n2 = 0;
        while (n2 < n) {
            RiskLevel level = riskLevelArray[n2];
            if (level.value.equalsIgnoreCase(text)) {
                return level;
            }
            ++n2;
        }
        return UNKNOWN;
    }

    public String toString() {
        return this.value;
    }
}
