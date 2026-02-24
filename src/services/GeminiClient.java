/*
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 */
package com.foodanalyzer.services;

import com.foodanalyzer.model.IngredientClassification;
import com.foodanalyzer.model.RiskLevel;
import com.foodanalyzer.util.Config;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GeminiClient {
    private static final String API_BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/";
    private final Config config = Config.getInstance();
    private final HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(30L)).build();
    private final Gson gson = new Gson();

    public List<IngredientClassification> analyzeIngredients(String ingredientText) throws Exception {
        if (!this.config.isGeminiEnabled() || this.config.getGeminiApiKey().isEmpty()) {
            throw new Exception("Gemini API is not configured. Please add your API key in Settings.");
        }
        String prompt = this.buildAnalysisPrompt(ingredientText);
        String response = this.callGeminiAPI(prompt);
        return this.parseGeminiResponse(response);
    }

    private String buildAnalysisPrompt(String ingredientText) {
        return String.format("You are a food safety expert analyzing ingredient lists for health risks.\n\nTASK: Analyze the following ingredients and return ONLY a valid JSON array (no markdown, no explanation).\n\nREQUIREMENTS:\n- Return a JSON array of objects\n- Each object must have: \"name\", \"normalized\", \"category\", \"confidence\", \"reason\"\n- \"category\" must be EXACTLY one of: \"harmful\", \"moderate\", \"safe\", \"unknown\"\n- \"confidence\" is a float between 0.0 and 1.0\n- \"reason\" is a brief explanation (max 150 characters)\n- \"normalized\" is the standardized ingredient name (lowercase)\n\nINGREDIENT LIST:\n%s\n\nRETURN ONLY THE JSON ARRAY, NO OTHER TEXT:\n", ingredientText);
    }

    private String callGeminiAPI(String prompt) throws Exception {
        String apiKey = this.config.getGeminiApiKey();
        String model = this.config.getString("gemini.model", "gemini-2.0-flash-exp");
        String url = API_BASE_URL + model + ":generateContent?key=" + apiKey;
        Map<String, List<Map<String, String>>> requestBody = Map.of("contents", List.of(Map.of("parts", List.of(Map.of("text", prompt)))), "generationConfig", Map.of("temperature", 0.2, "topK", 40, "topP", 0.95, "maxOutputTokens", 2048), "safetySettings", List.of(Map.of("category", "HARM_CATEGORY_DANGEROUS_CONTENT", "threshold", "BLOCK_NONE")));
        String jsonBody = this.gson.toJson(requestBody);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonBody)).timeout(Duration.ofSeconds(this.config.getInt("gemini.timeout", 30000) / 1000)).build();
        HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new Exception("Gemini API error: " + response.statusCode() + " - " + response.body());
        }
        return response.body();
    }

    private List<IngredientClassification> parseGeminiResponse(String jsonResponse) throws Exception {
        try {
            JsonObject responseObj = JsonParser.parseString((String)jsonResponse).getAsJsonObject();
            JsonArray candidates = responseObj.getAsJsonArray("candidates");
            if (candidates == null || candidates.isEmpty()) {
                throw new Exception("No response from Gemini API");
            }
            JsonObject firstCandidate = candidates.get(0).getAsJsonObject();
            JsonObject content = firstCandidate.getAsJsonObject("content");
            JsonArray parts = content.getAsJsonArray("parts");
            if (parts == null || parts.isEmpty()) {
                throw new Exception("Empty response from Gemini API");
            }
            String textContent = parts.get(0).getAsJsonObject().get("text").getAsString();
            if ((textContent = textContent.trim()).startsWith("```json")) {
                textContent = textContent.substring(7);
            }
            if (textContent.startsWith("```")) {
                textContent = textContent.substring(3);
            }
            if (textContent.endsWith("```")) {
                textContent = textContent.substring(0, textContent.length() - 3);
            }
            textContent = textContent.trim();
            JsonArray ingredientsArray = JsonParser.parseString((String)textContent).getAsJsonArray();
            ArrayList<IngredientClassification> results = new ArrayList<IngredientClassification>();
            int i = 0;
            while (i < ingredientsArray.size()) {
                JsonObject ingredientObj = ingredientsArray.get(i).getAsJsonObject();
                IngredientClassification classification = new IngredientClassification();
                classification.setName(ingredientObj.get("name").getAsString());
                classification.setNormalized(ingredientObj.get("normalized").getAsString());
                String category = ingredientObj.get("category").getAsString().toLowerCase();
                classification.setCategory(RiskLevel.fromString(category));
                classification.setConfidence(ingredientObj.get("confidence").getAsDouble());
                classification.setReason(ingredientObj.get("reason").getAsString());
                classification.setSource("gemini");
                results.add(classification);
                ++i;
            }
            return results;
        }
        catch (Exception e) {
            System.err.println("Error parsing Gemini response: " + e.getMessage());
            System.err.println("Response was: " + jsonResponse);
            throw new Exception("Failed to parse Gemini response: " + e.getMessage());
        }
    }

    public boolean testConnection() {
        try {
            String testPrompt = "Analyze this ingredient: Water";
            String response = this.callGeminiAPI(this.buildAnalysisPrompt(testPrompt));
            return response != null && !response.isEmpty();
        }
        catch (Exception e) {
            System.err.println("Gemini API test failed: " + e.getMessage());
            return false;
        }
    }
}
