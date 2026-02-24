/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.ui.panels;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

class SettingsPanel.2
extends SwingWorker<Boolean, Void> {
    private final /* synthetic */ JDialog val$progressDialog;

    SettingsPanel.2(JDialog jDialog) {
        this.val$progressDialog = jDialog;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        return SettingsPanel.this.dbManager.testConnection();
    }

    @Override
    protected void done() {
        this.val$progressDialog.dispose();
        try {
            boolean success = (Boolean)this.get();
            if (success) {
                String stats = SettingsPanel.this.dbManager.getPoolStats();
                JOptionPane.showMessageDialog(SettingsPanel.this, "\u2705 Database connection successful!\n\nConnection Details:\n\u2022 Host: " + SettingsPanel.this.config.getString("db.host") + "\n\u2022 Database: " + SettingsPanel.this.config.getString("db.name") + "\n\u2022 " + stats, "Connection Test", 1);
            } else {
                JOptionPane.showMessageDialog(SettingsPanel.this, "\u274c Database connection failed!\n\nPlease check your database settings and ensure MySQL is running.", "Connection Test", 0);
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(SettingsPanel.this, "\u274c Database connection failed:\n\n" + e.getMessage(), "Connection Test", 0);
        }
    }
}
