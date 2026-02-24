/*
 */
package com.foodanalyzer.ui.util;

import com.foodanalyzer.ui.util.AppColors;
import com.foodanalyzer.ui.util.AppFonts;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.AbstractBorder;
import javax.swing.border.CompoundBorder;

public class ComponentFactory {
    public static JButton createPrimaryButton(String text) {
        final JButton button = new JButton(text){

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(0.0f, 0.0f, AppColors.PRIMARY, this.getWidth(), this.getHeight(), AppColors.ACCENT);
                g2.setPaint(gradient);
                g2.fill(new RoundRectangle2D.Double(0.0, 0.0, this.getWidth(), this.getHeight(), 12.0, 12.0));
                g2.setColor(AppColors.TEXT_ON_PRIMARY);
                g2.setFont(this.getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (this.getWidth() - fm.stringWidth(this.getText())) / 2;
                int y = (this.getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(this.getText(), x, y);
                g2.dispose();
            }
        };
        button.setFont(AppFonts.BUTTON);
        button.setForeground(AppColors.TEXT_ON_PRIMARY);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(12));
        button.setPreferredSize(new Dimension(200, 48));
        button.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(Color.WHITE);
                Timer scaleTimer = new Timer(10, null);
                float[] scale = new float[]{1.0f};
                scaleTimer.addActionListener(evt -> {
                    fArray[0] = scale[0] + 0.02f;
                    if (scale[0] >= 1.05f) {
                        fArray[0] = 1.05f;
                        scaleTimer.stop();
                    }
                    button.putClientProperty("hoverScale", Float.valueOf(scale[0]));
                    button.repaint();
                });
                scaleTimer.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(AppColors.TEXT_ON_PRIMARY);
                Timer scaleTimer = new Timer(10, null);
                float[] scale = new float[]{1.05f};
                scaleTimer.addActionListener(evt -> {
                    fArray[0] = scale[0] - 0.02f;
                    if (scale[0] <= 1.0f) {
                        fArray[0] = 1.0f;
                        scaleTimer.stop();
                    }
                    button.putClientProperty("hoverScale", Float.valueOf(scale[0]));
                    button.repaint();
                });
                scaleTimer.start();
            }
        });
        return button;
    }

    public static JButton createSecondaryButton(String text) {
        final JButton button = new JButton(text);
        button.setFont(AppFonts.BUTTON);
        button.setForeground(AppColors.PRIMARY);
        button.setBackground(AppColors.SURFACE);
        button.setBorder(new CompoundBorder(new RoundedBorder(AppColors.PRIMARY, 2, 12), BorderFactory.createEmptyBorder(12, 24, 12, 24)));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(12));
        button.setPreferredSize(new Dimension(180, 48));
        button.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(AppColors.SURFACE_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(AppColors.SURFACE);
            }
        });
        return button;
    }

    public static JButton createTextButton(String text) {
        final JButton button = new JButton(text);
        button.setFont(AppFonts.BUTTON_SMALL);
        button.setForeground(AppColors.PRIMARY);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(12));
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(AppColors.PRIMARY_DARK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(AppColors.PRIMARY);
            }
        });
        return button;
    }

    public static JButton createIconButton(String icon) {
        final JButton button = new JButton(icon);
        button.setFont(new Font("Dialog", 0, 20));
        button.setForeground(AppColors.TEXT_SECONDARY);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(12));
        button.setPreferredSize(new Dimension(40, 40));
        button.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(AppColors.PRIMARY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(AppColors.TEXT_SECONDARY);
            }
        });
        return button;
    }

    public static JPanel createCard() {
        JPanel card = new JPanel();
        card.setBackground(AppColors.SURFACE);
        card.setBorder(new CompoundBorder(new ShadowBorder(), BorderFactory.createEmptyBorder(24, 24, 24, 24)));
        return card;
    }

    public static JTextField createTextField(final String placeholder) {
        JTextField field = new JTextField(){

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (this.getText().isEmpty() && !this.isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D)g.create();
                    g2.setColor(AppColors.TEXT_TERTIARY);
                    g2.setFont(this.getFont());
                    g2.drawString(placeholder, this.getInsets().left, g.getFontMetrics().getMaxAscent() + this.getInsets().top);
                    g2.dispose();
                }
            }
        };
        field.setFont(AppFonts.BODY);
        field.setForeground(AppColors.TEXT_PRIMARY);
        field.setBackground(AppColors.SURFACE);
        field.setBorder(new CompoundBorder(new RoundedBorder(AppColors.BORDER, 1, 8), BorderFactory.createEmptyBorder(12, 16, 12, 16)));
        field.setPreferredSize(new Dimension(300, 45));
        return field;
    }

    public static JTextArea createTextArea(String placeholder, int rows) {
        JTextArea area = new JTextArea(rows, 40);
        area.setFont(AppFonts.BODY);
        area.setForeground(AppColors.TEXT_PRIMARY);
        area.setBackground(AppColors.SURFACE);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        return area;
    }

    public static JScrollPane createScrollPane(Component component) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setBorder(new RoundedBorder(AppColors.BORDER, 1, 8));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }

    public static JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    public static class RoundedBorder
    extends AbstractBorder {
        private Color color;
        private int thickness;
        private int radius;

        public RoundedBorder(Color color, int thickness, int radius) {
            this.color = color;
            this.thickness = thickness;
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(this.color);
            g2.setStroke(new BasicStroke(this.thickness));
            g2.drawRoundRect(x, y, width - 1, height - 1, this.radius, this.radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.thickness, this.thickness, this.thickness, this.thickness);
        }
    }

    static class ShadowBorder
    extends AbstractBorder {
        ShadowBorder() {
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(AppColors.SHADOW);
            g2.fillRoundRect(x + 2, y + 2, width - 4, height - 4, 16, 16);
            g2.setColor(AppColors.BORDER_LIGHT);
            g2.drawRoundRect(x, y, width - 1, height - 1, 16, 16);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 4, 4, 4);
        }
    }
}
