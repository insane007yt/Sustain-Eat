/*
 */
package com.foodanalyzer.ui.components;

import com.foodanalyzer.ui.util.AppColors;
import com.foodanalyzer.ui.util.AppFonts;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import javax.swing.JPanel;
import javax.swing.Timer;

public class HealthScoreGauge
extends JPanel {
    private double score = 0.0;
    private double displayScore = 0.0;
    private String verdict = "Unknown";
    private Color scoreColor = AppColors.TEXT_SECONDARY;

    public HealthScoreGauge() {
        this.setPreferredSize(new Dimension(250, 250));
        this.setOpaque(false);
    }

    public void setScore(double score, String verdict) {
        this.score = Math.max(0.0, Math.min(100.0, score));
        this.verdict = verdict;
        this.scoreColor = AppColors.getScoreColor(score);
        this.animateScore();
    }

    private void animateScore() {
        Timer timer = new Timer(20, null);
        double increment = this.score / 50.0;
        timer.addActionListener(e -> {
            this.displayScore += increment;
            if (this.displayScore >= this.score) {
                this.displayScore = this.score;
                timer.stop();
            }
            this.repaint();
        });
        this.displayScore = 0.0;
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        int width = this.getWidth();
        int height = this.getHeight();
        int size = Math.min(width, height) - 40;
        int x = (width - size) / 2;
        int y = (height - size) / 2;
        g2.setColor(AppColors.BORDER_LIGHT);
        g2.setStroke(new BasicStroke(20.0f, 1, 1));
        g2.draw(new Arc2D.Double(x, y, size, size, 0.0, 360.0, 0));
        double percentage = this.displayScore / 100.0;
        int arcAngle = (int)(360.0 * percentage);
        GradientPaint gradient = new GradientPaint(x, y, this.scoreColor, x + size, y + size, this.scoreColor.brighter());
        g2.setPaint(gradient);
        g2.setStroke(new BasicStroke(20.0f, 1, 1));
        g2.draw(new Arc2D.Double(x, y, size, size, 90.0, -arcAngle, 0));
        g2.setColor(AppColors.TEXT_PRIMARY);
        g2.setFont(AppFonts.DISPLAY_MEDIUM);
        String scoreText = String.format("%.0f", this.displayScore);
        FontMetrics fm = g2.getFontMetrics();
        int scoreX = (width - fm.stringWidth(scoreText)) / 2;
        int scoreY = (height - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(scoreText, scoreX, scoreY);
        g2.setFont(AppFonts.BODY);
        g2.setColor(AppColors.TEXT_SECONDARY);
        String maxText = "/100";
        fm = g2.getFontMetrics();
        int maxX = (width - fm.stringWidth(maxText)) / 2;
        g2.drawString(maxText, maxX, scoreY + 25);
        g2.setFont(AppFonts.HEADING_4);
        g2.setColor(this.scoreColor);
        fm = g2.getFontMetrics();
        int verdictX = (width - fm.stringWidth(this.verdict)) / 2;
        g2.drawString(this.verdict, verdictX, scoreY + 55);
        g2.dispose();
    }
}
