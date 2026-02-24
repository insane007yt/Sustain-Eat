/*
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.reflect.TypeToken
 */
package com.foodanalyzer.db.dao;

import com.foodanalyzer.db.DBManager;
import com.foodanalyzer.model.Analysis;
import com.foodanalyzer.model.IngredientClassification;
import com.foodanalyzer.model.InputType;
import com.foodanalyzer.model.Verdict;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AnalysisDAO {
    private final DBManager dbManager = DBManager.getInstance();
    private final Gson gson = new Gson();

    /*
     * Exception decompiling
     */
    public Long save(Analysis analysis) {
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
    public Analysis findById(Long id) {
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
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public List<Analysis> findAll(int limit) {
        ArrayList<Analysis> analyses = new ArrayList<Analysis>();
        String sql = "SELECT * FROM analyses ORDER BY timestamp DESC LIMIT ?";
        try {
            Throwable throwable = null;
            Object var5_7 = null;
            try {
                Connection conn = this.dbManager.getConnection();
                try {
                    try (PreparedStatement ps = conn.prepareStatement(sql);){
                        ps.setInt(1, limit);
                        Throwable throwable2 = null;
                        Object var9_14 = null;
                        try (ResultSet rs = ps.executeQuery();){
                            while (rs.next()) {
                                analyses.add(this.mapResultSetToAnalysis(rs));
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
                    if (conn == null) return analyses;
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
                return analyses;
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
            System.err.println("Error finding all analyses: " + e.getMessage());
        }
        return analyses;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public List<Analysis> findRecent(int days, int limit) {
        ArrayList<Analysis> analyses = new ArrayList<Analysis>();
        String sql = "SELECT * FROM analyses WHERE timestamp >= DATE_SUB(NOW(), INTERVAL ? DAY) ORDER BY timestamp DESC LIMIT ?";
        try {
            Throwable throwable = null;
            Object var6_8 = null;
            try {
                Connection conn = this.dbManager.getConnection();
                try {
                    try (PreparedStatement ps = conn.prepareStatement(sql);){
                        ps.setInt(1, days);
                        ps.setInt(2, limit);
                        Throwable throwable2 = null;
                        Object var10_15 = null;
                        try (ResultSet rs = ps.executeQuery();){
                            while (rs.next()) {
                                analyses.add(this.mapResultSetToAnalysis(rs));
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
                    if (conn == null) return analyses;
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
                return analyses;
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
            System.err.println("Error finding recent analyses: " + e.getMessage());
        }
        return analyses;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public List<Analysis> searchByProductName(String searchTerm, int limit) {
        ArrayList<Analysis> analyses = new ArrayList<Analysis>();
        String sql = "SELECT * FROM analyses WHERE product_name LIKE ? ORDER BY timestamp DESC LIMIT ?";
        try {
            Throwable throwable = null;
            Object var6_8 = null;
            try {
                Connection conn = this.dbManager.getConnection();
                try {
                    try (PreparedStatement ps = conn.prepareStatement(sql);){
                        ps.setString(1, "%" + searchTerm + "%");
                        ps.setInt(2, limit);
                        Throwable throwable2 = null;
                        Object var10_15 = null;
                        try (ResultSet rs = ps.executeQuery();){
                            while (rs.next()) {
                                analyses.add(this.mapResultSetToAnalysis(rs));
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
                    if (conn == null) return analyses;
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
                return analyses;
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
            System.err.println("Error searching analyses: " + e.getMessage());
        }
        return analyses;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public List<Analysis> findByVerdict(Verdict verdict, int limit) {
        ArrayList<Analysis> analyses = new ArrayList<Analysis>();
        String sql = "SELECT * FROM analyses WHERE verdict = ? ORDER BY timestamp DESC LIMIT ?";
        try {
            Throwable throwable = null;
            Object var6_8 = null;
            try {
                Connection conn = this.dbManager.getConnection();
                try {
                    try (PreparedStatement ps = conn.prepareStatement(sql);){
                        ps.setString(1, verdict.getValue());
                        ps.setInt(2, limit);
                        Throwable throwable2 = null;
                        Object var10_15 = null;
                        try (ResultSet rs = ps.executeQuery();){
                            while (rs.next()) {
                                analyses.add(this.mapResultSetToAnalysis(rs));
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
                    if (conn == null) return analyses;
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
                return analyses;
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
            System.err.println("Error finding analyses by verdict: " + e.getMessage());
        }
        return analyses;
    }

    /*
     * Loose catch block
     */
    public boolean update(Analysis analysis) {
        String sql = "UPDATE analyses SET product_name = ?, user_note = ? WHERE id = ?";
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
                        ps.setString(1, analysis.getProductName());
                        ps.setString(2, analysis.getUserNote());
                        ps.setLong(3, analysis.getId());
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
            System.err.println("Error updating analysis: " + e.getMessage());
            return false;
        }
    }

    /*
     * Loose catch block
     */
    public boolean delete(Long id) {
        String sql = "DELETE FROM analyses WHERE id = ?";
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
            System.err.println("Error deleting analysis: " + e.getMessage());
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getTotalCount() {
        String sql = "SELECT COUNT(*) FROM analyses";
        try {
            Throwable throwable = null;
            Object var3_5 = null;
            try {
                ResultSet rs;
                PreparedStatement ps;
                Connection conn;
                block21: {
                    int n;
                    conn = this.dbManager.getConnection();
                    try {
                        block23: {
                            ps = conn.prepareStatement(sql);
                            try {
                                block22: {
                                    rs = ps.executeQuery();
                                    try {
                                        if (!rs.next()) break block21;
                                        n = rs.getInt(1);
                                        if (rs == null) break block22;
                                    }
                                    catch (Throwable throwable2) {
                                        if (rs == null) throw throwable2;
                                        rs.close();
                                        throw throwable2;
                                    }
                                    rs.close();
                                }
                                if (ps == null) break block23;
                            }
                            catch (Throwable throwable3) {
                                if (throwable == null) {
                                    throwable = throwable3;
                                } else if (throwable != throwable3) {
                                    throwable.addSuppressed(throwable3);
                                }
                                if (ps == null) throw throwable;
                                ps.close();
                                throw throwable;
                            }
                            ps.close();
                        }
                        if (conn == null) return n;
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
                    return n;
                }
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn == null) return 0;
                conn.close();
                return 0;
            }
            catch (Throwable throwable5) {
                if (throwable == null) {
                    throwable = throwable5;
                    throw throwable;
                }
                if (throwable == throwable5) throw throwable;
                throwable.addSuppressed(throwable5);
                throw throwable;
            }
        }
        catch (SQLException e) {
            System.err.println("Error getting total count: " + e.getMessage());
        }
        return 0;
    }

    private Analysis mapResultSetToAnalysis(ResultSet rs) throws SQLException {
        Timestamp updatedAt;
        String classifiedJson;
        Analysis analysis = new Analysis();
        analysis.setId(rs.getLong("id"));
        Timestamp timestamp = rs.getTimestamp("timestamp");
        if (timestamp != null) {
            analysis.setTimestamp(timestamp.toLocalDateTime());
        }
        analysis.setInputType(InputType.fromString(rs.getString("input_type")));
        analysis.setProductName(rs.getString("product_name"));
        analysis.setRawInput(rs.getString("raw_input"));
        String normalizedJson = rs.getString("normalized_ingredients");
        if (normalizedJson != null) {
            List normalized = (List)this.gson.fromJson(normalizedJson, new TypeToken<List<String>>(){}.getType());
            analysis.setNormalizedIngredients(normalized);
        }
        if ((classifiedJson = rs.getString("classified_ingredients")) != null) {
            List classified = (List)this.gson.fromJson(classifiedJson, new TypeToken<List<IngredientClassification>>(){}.getType());
            analysis.setClassifiedIngredients(classified);
        }
        analysis.setHarmfulCount(rs.getInt("harmful_count"));
        analysis.setModerateCount(rs.getInt("moderate_count"));
        analysis.setSafeCount(rs.getInt("safe_count"));
        analysis.setTotalCount(rs.getInt("total_count"));
        analysis.setHealthScore(rs.getBigDecimal("health_score"));
        analysis.setVerdict(Verdict.fromString(rs.getString("verdict")));
        analysis.setGeminiResponse(rs.getString("gemini_response"));
        analysis.setImagePath(rs.getString("image_path"));
        analysis.setUserNote(rs.getString("user_note"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            analysis.setCreatedAt(createdAt.toLocalDateTime());
        }
        if ((updatedAt = rs.getTimestamp("updated_at")) != null) {
            analysis.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return analysis;
    }
}
