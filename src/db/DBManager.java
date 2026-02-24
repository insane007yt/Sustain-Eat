/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.zaxxer.hikari.HikariConfig
 *  com.zaxxer.hikari.HikariDataSource
 */
package com.foodanalyzer.db;

import com.foodanalyzer.util.Config;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class DBManager {
    private static DBManager instance;
    private HikariDataSource dataSource;
    private Config config = Config.getInstance();

    private DBManager() {
        this.initializeDataSource();
    }

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private void initializeDataSource() {
        try {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(this.config.getJdbcUrl());
            hikariConfig.setUsername(this.config.getDbUsername());
            hikariConfig.setPassword(this.config.getDbPassword());
            hikariConfig.setMaximumPoolSize(this.config.getInt("db.pool.maximumPoolSize", 10));
            hikariConfig.setConnectionTimeout((long)this.config.getInt("db.pool.connectionTimeout", 30000));
            hikariConfig.setIdleTimeout((long)this.config.getInt("db.pool.idleTimeout", 600000));
            hikariConfig.setMaxLifetime((long)this.config.getInt("db.pool.maxLifetime", 1800000));
            hikariConfig.setPoolName("FoodAnalyzerPool");
            hikariConfig.addDataSourceProperty("cachePrepStmts", (Object)"true");
            hikariConfig.addDataSourceProperty("prepStmtCacheSize", (Object)"250");
            hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", (Object)"2048");
            hikariConfig.addDataSourceProperty("useServerPrepStmts", (Object)"true");
            this.dataSource = new HikariDataSource(hikariConfig);
            Throwable throwable = null;
            Object var3_5 = null;
            try (Connection conn = this.getConnection();){
                System.out.println("\u2713 Database connection established successfully");
                System.out.println("  JDBC URL: " + this.config.getJdbcUrl());
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
        catch (Exception e) {
            System.err.println("\u2717 Failed to initialize database connection");
            System.err.println("  Error: " + e.getMessage());
            System.err.println("  Please check your database configuration in config.properties");
        }
    }

    public Connection getConnection() throws SQLException {
        if (this.dataSource == null) {
            throw new SQLException("DataSource not initialized");
        }
        return this.dataSource.getConnection();
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public boolean testConnection() {
        try {
            Throwable throwable = null;
            Object var2_4 = null;
            try (Connection conn = this.getConnection();){
                boolean bl = conn != null && !conn.isClosed();
                return bl;
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
        catch (SQLException e) {
            System.err.println("Connection test failed: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        if (this.dataSource != null && !this.dataSource.isClosed()) {
            this.dataSource.close();
            System.out.println("\u2713 Database connection pool closed");
        }
    }

    public String getPoolStats() {
        if (this.dataSource == null) {
            return "DataSource not initialized";
        }
        return String.format("Pool Stats: Active=%d, Idle=%d, Total=%d, Waiting=%d", this.dataSource.getHikariPoolMXBean().getActiveConnections(), this.dataSource.getHikariPoolMXBean().getIdleConnections(), this.dataSource.getHikariPoolMXBean().getTotalConnections(), this.dataSource.getHikariPoolMXBean().getThreadsAwaitingConnection());
    }
}
