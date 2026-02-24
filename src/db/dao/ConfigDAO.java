/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.db.dao;

import com.foodanalyzer.db.DBManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ConfigDAO {
    private final DBManager dbManager = DBManager.getInstance();

    /*
     * Exception decompiling
     */
    public String getValue(String key, String defaultValue) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doClass(Driver.java:84)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:78)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Loose catch block
     */
    public boolean setValue(String key, String value) {
        String sql = "INSERT INTO app_config (config_key, config_value) VALUES (?, ?) ON DUPLICATE KEY UPDATE config_value = ?";
        try {
            Throwable throwable = null;
            Object var5_7 = null;
            try {
                boolean bl;
                PreparedStatement ps;
                Connection conn;
                block18: {
                    block17: {
                        conn = this.dbManager.getConnection();
                        ps = conn.prepareStatement(sql);
                        ps.setString(1, key);
                        ps.setString(2, value);
                        ps.setString(3, value);
                        bl = ps.executeUpdate() > 0;
                        if (ps == null) break block17;
                        ps.close();
                    }
                    if (conn == null) break block18;
                    conn.close();
                }
                return bl;
                {
                    catch (Throwable throwable2) {
                        try {
                            if (ps != null) {
                                ps.close();
                            }
                            throw throwable2;
                        }
                        catch (Throwable throwable3) {
                            if (throwable == null) {
                                throwable = throwable3;
                            } else if (throwable != throwable3) {
                                throwable.addSuppressed(throwable3);
                            }
                            if (conn != null) {
                                conn.close();
                            }
                            throw throwable;
                        }
                    }
                }
            }
            catch (Throwable throwable4) {
                if (throwable == null) {
                    throwable = throwable4;
                } else if (throwable != throwable4) {
                    throwable.addSuppressed(throwable4);
                }
                throw throwable;
            }
        }
        catch (SQLException e) {
            System.err.println("Error setting config value: " + e.getMessage());
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Map<String, String> getAll() {
        HashMap<String, String> config = new HashMap<String, String>();
        String sql = "SELECT config_key, config_value FROM app_config";
        try {
            Throwable throwable = null;
            Object var4_6 = null;
            try {
                Connection conn = this.dbManager.getConnection();
                try {
                    block21: {
                        PreparedStatement ps = conn.prepareStatement(sql);
                        try {
                            try (ResultSet rs = ps.executeQuery();){
                                while (rs.next()) {
                                    config.put(rs.getString("config_key"), rs.getString("config_value"));
                                }
                            }
                            if (ps == null) break block21;
                        }
                        catch (Throwable throwable2) {
                            if (throwable == null) {
                                throwable = throwable2;
                            } else if (throwable != throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                            if (ps == null) throw throwable;
                            ps.close();
                            throw throwable;
                        }
                        ps.close();
                    }
                    if (conn == null) return config;
                }
                catch (Throwable throwable3) {
                    if (throwable == null) {
                        throwable = throwable3;
                    } else if (throwable != throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                    if (conn == null) throw throwable;
                    conn.close();
                    throw throwable;
                }
                conn.close();
                return config;
            }
            catch (Throwable throwable4) {
                if (throwable == null) {
                    throwable = throwable4;
                    throw throwable;
                }
                if (throwable == throwable4) throw throwable;
                throwable.addSuppressed(throwable4);
                throw throwable;
            }
        }
        catch (SQLException e) {
            System.err.println("Error getting all config: " + e.getMessage());
        }
        return config;
    }

    public double getDouble(String key, double defaultValue) {
        try {
            String value = this.getValue(key, String.valueOf(defaultValue));
            return Double.parseDouble(value);
        }
        catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public int getInt(String key, int defaultValue) {
        try {
            String value = this.getValue(key, String.valueOf(defaultValue));
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String value = this.getValue(key, String.valueOf(defaultValue));
        return Boolean.parseBoolean(value);
    }
}
