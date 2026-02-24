/*
 */
package com.foodanalyzer.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextNormalizer {
    private static final Pattern INGREDIENTS_PATTERN = Pattern.compile("ingredients?\\s*:(.+?)(?=\\n\\n|nutrition|$)", 34);

    public static String normalize(String text) {
        if (text == null) {
            return "";
        }
        return text.toLowerCase().replaceAll("\\(.*?\\)", "").replaceAll("[\u00ae\u2122\u00a9]", "").replaceAll("\\d+%?", "").replaceAll("[^a-z\\s-]", "").replaceAll("\\s+", " ").trim();
    }

    public static String extractIngredientsList(String rawText) {
        if (rawText == null) {
            return "";
        }
        Matcher matcher = INGREDIENTS_PATTERN.matcher(rawText);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return rawText;
    }

    public static List<String> splitIngredients(String ingredientText) {
        if (ingredientText == null || ingredientText.trim().isEmpty()) {
            return new ArrayList<String>();
        }
        return Arrays.stream(ingredientText.split("[,;\\n]")).map(String::trim).filter(s -> !s.isEmpty()).filter(s -> s.length() > 1).collect(Collectors.toList());
    }

    public static List<String> parseIngredients(String rawText) {
        String ingredientText = TextNormalizer.extractIngredientsList(rawText);
        return TextNormalizer.splitIngredients(ingredientText);
    }

    public static String cleanText(String text) {
        if (text == null) {
            return "";
        }
        return text.replaceAll("\\s+", " ").replaceAll("[\u00ae\u2122\u00a9]", "").trim();
    }
}
