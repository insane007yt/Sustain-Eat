/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.reflect.TypeToken
 */
package com.foodanalyzer.db.dao;

import com.foodanalyzer.db.DBManager;
import com.foodanalyzer.model.Ingredient;
import com.foodanalyzer.model.RiskLevel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO {
    private final DBManager dbManager = DBManager.getInstance();
    private final Gson gson = new Gson();

    /*
     * Exception decompiling
     */
    public Ingredient findByName(String name) {
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
     * Exception decompiling
     */
    public Ingredient findBySynonym(String synonym) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[TRYBLOCK]], but top level block is 14[DOLOOP]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
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
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<Ingredient> findAll() {
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        String sql = "SELECT * FROM ingredients WHERE is_active = TRUE ORDER BY canonical_name";
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
                                    ingredients.add(this.mapResultSetToIngredient(rs));
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
                    if (conn == null) return ingredients;
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
                return ingredients;
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
            System.err.println("Error finding all ingredients: " + e.getMessage());
        }
        return ingredients;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public List<Ingredient> findByRiskLevel(RiskLevel riskLevel) {
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        String sql = "SELECT * FROM ingredients WHERE risk_level = ? AND is_active = TRUE ORDER BY canonical_name";
        try {
            Throwable throwable = null;
            Object var5_7 = null;
            try {
                Connection conn = this.dbManager.getConnection();
                try {
                    try (PreparedStatement ps = conn.prepareStatement(sql);){
                        ps.setString(1, riskLevel.getValue());
                        Throwable throwable2 = null;
                        Object var9_14 = null;
                        try (ResultSet rs = ps.executeQuery();){
                            while (rs.next()) {
                                ingredients.add(this.mapResultSetToIngredient(rs));
                            }
                        }
                        catch (Throwable throwable3) {
                            if (throwable2 == null) {
                                throwable2 = throwable3;
                                throw throwable2;
                            } else {
                                if (throwable2 == throwable3) throw throwable2;
                                throwable2.addSuppressed(throwable3);
                            }
                            throw throwable2;
                        }
                    }
                    if (conn == null) return ingredients;
                }
                catch (Throwable throwable4) {
                    if (throwable == null) {
                        throwable = throwable4;
                    } else if (throwable != throwable4) {
                        throwable.addSuppressed(throwable4);
                    }
                    if (conn == null) throw throwable;
                    conn.close();
                    throw throwable;
                }
                conn.close();
                return ingredients;
            }
            catch (Throwable throwable5) {
                if (throwable == null) {
                    throwable = throwable5;
                    throw throwable;
                } else {
                    if (throwable == throwable5) throw throwable;
                    throwable.addSuppressed(throwable5);
                }
                throw throwable;
            }
        }
        catch (SQLException e) {
            System.err.println("Error finding ingredients by risk level: " + e.getMessage());
        }
        return ingredients;
    }

    /*
     * Exception decompiling
     */
    public Long insert(Ingredient ingredient) {
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
    public boolean update(Ingredient ingredient) {
        String sql = "UPDATE ingredients SET canonical_name = ?, synonyms = ?, risk_level = ?, category = ?, notes = ?, scientific_name = ?, common_uses = ?, health_effects = ? WHERE id = ?";
        try {
            Throwable throwable = null;
            Object var4_6 = null;
            try {
                boolean bl;
                PreparedStatement ps;
                Connection conn;
                block18: {
                    block17: {
                        conn = this.dbManager.getConnection();
                        ps = conn.prepareStatement(sql);
                        ps.setString(1, ingredient.getCanonicalName());
                        ps.setString(2, this.gson.toJson(ingredient.getSynonyms()));
                        ps.setString(3, ingredient.getRiskLevel().getValue());
                        ps.setString(4, ingredient.getCategory());
                        ps.setString(5, ingredient.getNotes());
                        ps.setString(6, ingredient.getScientificName());
                        ps.setString(7, ingredient.getCommonUses());
                        ps.setString(8, ingredient.getHealthEffects());
                        ps.setLong(9, ingredient.getId());
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
            System.err.println("Error updating ingredient: " + e.getMessage());
            return false;
        }
    }

    /*
     * Loose catch block
     */
    public boolean deactivate(Long id) {
        String sql = "UPDATE ingredients SET is_active = FALSE WHERE id = ?";
        try {
            Throwable throwable = null;
            Object var4_6 = null;
            try {
                boolean bl;
                PreparedStatement ps;
                Connection conn;
                block18: {
                    block17: {
                        conn = this.dbManager.getConnection();
                        ps = conn.prepareStatement(sql);
                        ps.setLong(1, id);
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
            System.err.println("Error deactivating ingredient: " + e.getMessage());
            return false;
        }
    }

    private Ingredient mapResultSetToIngredient(ResultSet rs) throws SQLException {
        Timestamp updatedAt;
        Ingredient ingredient = new Ingredient();
        ingredient.setId(rs.getLong("id"));
        ingredient.setCanonicalName(rs.getString("canonical_name"));
        String synonymsJson = rs.getString("synonyms");
        if (synonymsJson != null) {
            List synonyms = (List)this.gson.fromJson(synonymsJson, new TypeToken<List<String>>(){}.getType());
            ingredient.setSynonyms(synonyms);
        }
        ingredient.setRiskLevel(RiskLevel.fromString(rs.getString("risk_level")));
        ingredient.setCategory(rs.getString("category"));
        ingredient.setNotes(rs.getString("notes"));
        ingredient.setScientificName(rs.getString("scientific_name"));
        ingredient.setCommonUses(rs.getString("common_uses"));
        ingredient.setHealthEffects(rs.getString("health_effects"));
        ingredient.setActive(rs.getBoolean("is_active"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            ingredient.setCreatedAt(createdAt.toLocalDateTime());
        }
        if ((updatedAt = rs.getTimestamp("updated_at")) != null) {
            ingredient.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return ingredient;
    }
}
