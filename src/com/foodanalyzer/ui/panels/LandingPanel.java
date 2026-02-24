/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.ui.panels;

import com.foodanalyzer.ui.MainFrame;
import com.foodanalyzer.ui.util.AnimationUtils;
import com.foodanalyzer.ui.util.AppColors;
import com.foodanalyzer.ui.util.AppFonts;
import com.foodanalyzer.ui.util.ComponentFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class LandingPanel
extends JPanel {
    private MainFrame mainFrame;

    public LandingPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.setLayout(new BorderLayout());
        this.setBackground(AppColors.BACKGROUND);
        this.initializeComponents();
        SwingUtilities.invokeLater(() -> {
            Timer initTimer = new Timer(100, e -> {
                this.animateEntrance();
                ((Timer)e.getSource()).stop();
            });
            initTimer.setRepeats(false);
            initTimer.start();
        });
    }

    private void initializeComponents() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(AppColors.BACKGROUND);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 0;
        gbc.anchor = 10;
        gbc.insets = new Insets(10, 0, 10, 0);
        JPanel logoPanel = this.createLogoPanel();
        gbc.insets = new Insets(60, 0, 20, 0);
        contentPanel.add((Component)logoPanel, gbc);
        JLabel titleLabel = new JLabel("Food Label Analyzer");
        titleLabel.setFont(AppFonts.DISPLAY_MEDIUM);
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        gbc.insets = new Insets(0, 0, 10, 0);
        contentPanel.add((Component)titleLabel, gbc);
        JLabel subtitleLabel = new JLabel("AI-Powered Ingredient Analysis for Healthier Choices");
        subtitleLabel.setFont(AppFonts.BODY_LARGE);
        subtitleLabel.setForeground(AppColors.TEXT_SECONDARY);
        gbc.insets = new Insets(0, 0, 50, 0);
        contentPanel.add((Component)subtitleLabel, gbc);
        JPanel buttonPanel = this.createButtonPanel();
        gbc.insets = new Insets(0, 0, 30, 0);
        contentPanel.add((Component)buttonPanel, gbc);
        JPanel featuresPanel = this.createFeaturesPanel();
        gbc.insets = new Insets(20, 0, 30, 0);
        contentPanel.add((Component)featuresPanel, gbc);
        JPanel bottomPanel = this.createBottomPanel();
        gbc.insets = new Insets(20, 0, 40, 0);
        contentPanel.add((Component)bottomPanel, gbc);
        this.add((Component)contentPanel, "Center");
    }

    private JPanel createLogoPanel() {
        final JPanel logoPanel = new JPanel(){
            private float rotationAngle = 0.0f;
            private float floatOffset = 0.0f;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int size = 120;
                g2.translate(0.0, this.floatOffset);
                g2.setColor(new Color(255, 149, 110, 30));
                g2.fillOval(5, 5, size, size);
                GradientPaint gradient = new GradientPaint(0.0f, 0.0f, AppColors.PRIMARY, size, size, AppColors.ACCENT);
                g2.setPaint(gradient);
                g2.fillOval(0, 0, size, size);
                g2.setColor(new Color(255, 255, 255, 40));
                g2.fillOval(10, 10, 40, 40);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Dialog", 0, 64));
                String icon = "\ud83c\udf4e";
                FontMetrics fm = g2.getFontMetrics();
                int x = (size - fm.stringWidth(icon)) / 2;
                int y = (size - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(icon, x, y);
                g2.dispose();
            }
        };
        logoPanel.setPreferredSize(new Dimension(120, 120));
        logoPanel.setOpaque(false);
        Timer floatTimer = new Timer(50, new ActionListener(){
            private int frame = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                ++this.frame;
                double angle = Math.toRadians(this.frame * 2);
                float offset = (float)(Math.sin(angle) * 5.0);
                try {
                    Field field = logoPanel.getClass().getDeclaredField("floatOffset");
                    field.setAccessible(true);
                    field.setFloat(logoPanel, offset);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                logoPanel.repaint();
            }
        });
        floatTimer.start();
        return logoPanel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(1, 20, 0));
        panel.setOpaque(false);
        JButton analyzeBtn = ComponentFactory.createPrimaryButton("Start Analysis");
        analyzeBtn.setPreferredSize(new Dimension(220, 56));
        analyzeBtn.setFont(AppFonts.BUTTON_LARGE);
        analyzeBtn.addActionListener(e -> this.mainFrame.navigateToAnalyze());
        JButton historyBtn = ComponentFactory.createSecondaryButton("View History");
        historyBtn.setPreferredSize(new Dimension(220, 56));
        historyBtn.setFont(AppFonts.BUTTON_LARGE);
        historyBtn.addActionListener(e -> this.mainFrame.navigateToHistory());
        panel.add(analyzeBtn);
        panel.add(historyBtn);
        return panel;
    }

    private JPanel createFeaturesPanel() {
        JPanel panel = new JPanel(new FlowLayout(1, 20, 0));
        panel.setOpaque(false);
        JPanel card1 = this.createFeatureCard("\ud83e\udd16", "AI-Powered", "Advanced analysis using Gemini AI");
        JPanel card2 = this.createFeatureCard("\u26a1", "Instant Results", "Get detailed insights in seconds");
        JPanel card3 = this.createFeatureCard("\ud83d\udcda", "Track History", "Save and review past analyses");
        panel.add(card1);
        panel.add(card2);
        panel.add(card3);
        return panel;
    }

    private JPanel createFeatureCard(String icon, String title, String description) {
        JPanel card = new JPanel(){

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppColors.SHADOW);
                g2.fill(new RoundRectangle2D.Double(2.0, 2.0, this.getWidth() - 4, this.getHeight() - 4, 16.0, 16.0));
                g2.setColor(AppColors.SURFACE);
                g2.fill(new RoundRectangle2D.Double(0.0, 0.0, this.getWidth() - 1, this.getHeight() - 1, 16.0, 16.0));
                g2.setColor(AppColors.BORDER);
                g2.draw(new RoundRectangle2D.Double(0.0, 0.0, this.getWidth() - 1, this.getHeight() - 1, 16.0, 16.0));
                g2.dispose();
            }
        };
        card.setLayout(new BoxLayout(card, 1));
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(200, 140));
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Dialog", 0, 40));
        iconLabel.setAlignmentX(0.5f);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(AppFonts.HEADING_4);
        titleLabel.setForeground(AppColors.TEXT_PRIMARY);
        titleLabel.setAlignmentX(0.5f);
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(AppFonts.BODY_SMALL);
        descLabel.setForeground(AppColors.TEXT_SECONDARY);
        descLabel.setAlignmentX(0.5f);
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(descLabel);
        return card;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(1, 30, 0));
        panel.setOpaque(false);
        JButton settingsBtn = ComponentFactory.createTextButton("\u2699\ufe0f  Settings");
        settingsBtn.addActionListener(e -> this.mainFrame.navigateToSettings());
        JButton aboutBtn = ComponentFactory.createTextButton("\u2139\ufe0f  About");
        aboutBtn.addActionListener(e -> this.showAboutDialog());
        panel.add(settingsBtn);
        panel.add(new JLabel("|"){
            {
                this.setForeground(AppColors.BORDER);
            }
        });
        panel.add(aboutBtn);
        return panel;
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this, "Food Label Analyzer v1.0\n\nAI-powered ingredient analysis for healthier food choices.\nBuilt with Java Swing and Gemini API.\n\n\u00a9 2025 Food Analyzer Team", "About", 1);
    }

    private void animateEntrance() {
        ArrayList<Component> componentsToAnimate = new ArrayList<Component>();
        this.findAnimatableComponents(this, componentsToAnimate);
        if (!componentsToAnimate.isEmpty()) {
            AnimationUtils.staggeredFadeIn(componentsToAnimate, 100);
        }
    }

    private void findAnimatableComponents(Container parent, List<Component> list) {
        Component[] componentArray = parent.getComponents();
        int n = componentArray.length;
        int n2 = 0;
        while (n2 < n) {
            Component comp = componentArray[n2];
            if (comp instanceof JButton || comp instanceof JLabel) {
                list.add(comp);
            }
            if (comp instanceof Container) {
                this.findAnimatableComponents((Container)comp, list);
            }
            ++n2;
        }
    }
}
