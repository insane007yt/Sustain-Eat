/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.model;

public enum InputType {
    IMAGE("image"),
    TEXT("text"),
    PASTE("paste");

    private final String value;

    private InputType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static InputType fromString(String text) {
        if (text == null) {
            return TEXT;
        }
        InputType[] inputTypeArray = InputType.values();
        int n = inputTypeArray.length;
        int n2 = 0;
        while (n2 < n) {
            InputType type = inputTypeArray[n2];
            if (type.value.equalsIgnoreCase(text)) {
                return type;
            }
            ++n2;
        }
        return TEXT;
    }

    public String toString() {
        return this.value;
    }
}
