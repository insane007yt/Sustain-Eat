package com.foodanalyzer.app;

import com.foodanalyzer.ui.MainFrame;
import java.awt.Component;
import java.awt.Insets;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        setupLookAndFeel();
        SwingUtilities.invokeLater(() -> {
            try {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            } catch (Exception var1) {
                var1.printStackTrace();
                JOptionPane.showMessageDialog((Component)null, "Failed to start application: " + var1.getMessage(), "Error", 0);
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
        } catch (Exception var1) {
            System.err.println("Could not set look and feel: " + var1.getMessage());
        }

    }
}
