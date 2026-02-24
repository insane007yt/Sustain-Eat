/*
 */
package com.foodanalyzer.model;

public enum Verdict {
    HARMFUL("harmful", "Harmful"),
    MODERATELY_HARMFUL("moderately_harmful", "Moderately Harmful"),
    SAFE("safe", "Safe");

    private final String value;
    private final String displayName;

    private Verdict(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public String getValue() {
        return this.value;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public static Verdict fromString(String text) {
        if (text == null) {
            return SAFE;
        }
        Verdict[] verdictArray = Verdict.values();
        int n = verdictArray.length;
        int n2 = 0;
        while (n2 < n) {
            Verdict verdict = verdictArray[n2];
            if (verdict.value.equalsIgnoreCase(text) || verdict.displayName.equalsIgnoreCase(text)) {
                return verdict;
            }
            ++n2;
        }
        return SAFE;
    }

    public String toString() {
        return this.displayName;
    }
}
