/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.app;

import com.foodanalyzer.ui.MainFrame;
import java.awt.Insets;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        Main.setupLookAndFeel();
        SwingUtilities.invokeLater(() -> {
            try {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            }
            catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to start application: " + e.getMessage(), "Error", 0);
            }
        });
    }

    private static void setupLookAndFeel() {
        try {
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");
            System.setProperty("sun.java2d.opengl", "true");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("Button.arc", 12);
            UIManager.put("Component.arc", 8);
            UIManager.put("TextComponent.arc", 8);
            UIManager.put("ScrollBar.thumbArc", 999);
            UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        }
        catch (Exception e) {
            System.err.println("Could not set look and feel: " + e.getMessage());
        }
    }
}
