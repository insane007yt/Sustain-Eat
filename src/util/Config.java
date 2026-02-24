/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final String CONFIG_FILE = "config.properties";
    private static final String DEFAULT_CONFIG_FILE = "config.default.properties";
    private static Config instance;
    private Properties properties = new Properties();

    private Config() {
        this.loadConfig();
    }

    public static synchronized Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    private void loadConfig() {
        File configFile = new File(CONFIG_FILE);
        if (configFile.exists()) {
            try {
                Throwable throwable = null;
                Object var3_5 = null;
                try (FileInputStream fis = new FileInputStream(configFile);){
                    this.properties.load(fis);
                    return;
                }
                catch (Throwable throwable2) {
                    if (throwable == null) {
                        throwable = throwable2;
                    } else if (throwable != throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                    throw throwable;
                }
            }
            catch (IOException e) {
                System.err.println("Error loading config file: " + e.getMessage());
            }
        }
        this.loadDefaultConfig();
    }

    private void loadDefaultConfig() {
        this.properties.setProperty("db.host", "localhost");
        this.properties.setProperty("db.port", "3306");
        this.properties.setProperty("db.name", "food_analyzer");
        this.properties.setProperty("db.username", "root");
        this.properties.setProperty("db.password", "");
        this.properties.setProperty("db.useSSL", "false");
        this.properties.setProperty("db.serverTimezone", "UTC");
        this.properties.setProperty("db.pool.maximumPoolSize", "10");
        this.properties.setProperty("db.pool.connectionTimeout", "30000");
        this.properties.setProperty("db.pool.idleTimeout", "600000");
        this.properties.setProperty("db.pool.maxLifetime", "1800000");
        this.properties.setProperty("gemini.api.key", "");
        this.properties.setProperty("gemini.model", "gemini-2.0-flash-exp");
        this.properties.setProperty("gemini.timeout", "30000");
        this.properties.setProperty("gemini.enabled", "false");
        this.properties.setProperty("score.harmful.weight", "1.5");
        this.properties.setProperty("score.moderate.weight", "0.75");
        this.properties.setProperty("score.safe.threshold", "80");
        this.properties.setProperty("score.moderate.threshold", "50");
        this.properties.setProperty("app.version", "1.0.0");
        this.properties.setProperty("app.enable.local.fallback", "true");
        this.properties.setProperty("app.max.history.records", "1000");
        this.saveConfig();
    }

    public void saveConfig() {
        try {
            Throwable throwable = null;
            Object var2_4 = null;
            try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE);){
                this.properties.store(fos, "Food Label Analyzer Configuration");
            }
            catch (Throwable throwable2) {
                if (throwable == null) {
                    throwable = throwable2;
                } else if (throwable != throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
        }
        catch (IOException e) {
            System.err.println("Error saving config file: " + e.getMessage());
        }
    }

    public String getString(String key, String defaultValue) {
        return this.properties.getProperty(key, defaultValue);
    }

    public String getString(String key) {
        return this.properties.getProperty(key);
    }

    public int getInt(String key, int defaultValue) {
        try {
            String value = this.properties.getProperty(key);
            return value != null ? Integer.parseInt(value) : defaultValue;
        }
        catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public double getDouble(String key, double defaultValue) {
        try {
            String value = this.properties.getProperty(key);
            return value != null ? Double.parseDouble(value) : defaultValue;
        }
        catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String value = this.properties.getProperty(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    public void setProperty(String key, String value) {
        this.properties.setProperty(key, value);
    }

    public String getJdbcUrl() {
        String host = this.getString("db.host", "localhost");
        String port = this.getString("db.port", "3306");
        String dbName = this.getString("db.name", "food_analyzer");
        String useSSL = this.getString("db.useSSL", "false");
        String serverTimezone = this.getString("db.serverTimezone", "UTC");
        return String.format("jdbc:mysql://%s:%s/%s?useSSL=%s&serverTimezone=%s", host, port, dbName, useSSL, serverTimezone);
    }

    public String getDbUsername() {
        return this.getString("db.username", "root");
    }

    public String getDbPassword() {
        return this.getString("db.password", "");
    }

    public String getGeminiApiKey() {
        return this.getString("gemini.api.key", "");
    }

    public boolean isGeminiEnabled() {
        return this.getBoolean("gemini.enabled", false) && !this.getGeminiApiKey().isEmpty();
    }

    public double getHarmfulWeight() {
        return this.getDouble("score.harmful.weight", 1.5);
    }

    public double getModerateWeight() {
        return this.getDouble("score.moderate.weight", 0.75);
    }

    public double getSafeThreshold() {
        return this.getDouble("score.safe.threshold", 80.0);
    }

    public double getModerateThreshold() {
        return this.getDouble("score.moderate.threshold", 50.0);
    }
}
