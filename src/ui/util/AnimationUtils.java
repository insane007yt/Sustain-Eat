/*
 * Decompiled with CFR 0.152.
 */
package com.foodanalyzer.ui.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.Timer;

public class AnimationUtils {
    public static void fadeIn(final Component component, int duration) {
        final Timer timer = new Timer(20, null);
        final float[] opacity = new float[]{0.0f};
        final float increment = 1.0f / (float)(duration / 20);
        timer.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                opacity[0] = opacity[0] + increment;
                if (opacity[0] >= 1.0f) {
                    opacity[0] = 1.0f;
                    timer.stop();
                }
                if (component instanceof JComponent) {
                    ((JComponent)component).putClientProperty("opacity", Float.valueOf(opacity[0]));
                    component.repaint();
                }
            }
        });
        timer.start();
    }

    public static void slideInFromBottom(final JComponent component, int duration) {
        final int originalY = component.getY();
        int startY = originalY + 100;
        component.setLocation(component.getX(), startY);
        final Timer timer = new Timer(20, null);
        final int[] currentY = new int[]{startY};
        int distance = startY - originalY;
        int steps = duration / 20;
        final int increment = distance / steps;
        timer.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                currentY[0] = currentY[0] - increment;
                if (currentY[0] <= originalY) {
                    currentY[0] = originalY;
                    timer.stop();
                }
                component.setLocation(component.getX(), currentY[0]);
            }
        });
        timer.start();
    }

    public static void pulse(final JComponent component) {
        final Timer timer = new Timer(10, null);
        final double[] scale = new double[]{1.0};
        final boolean[] growing = new boolean[]{true};
        timer.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if (growing[0]) {
                    scale[0] = scale[0] + 0.02;
                    if (scale[0] >= 1.1) {
                        growing[0] = false;
                    }
                } else {
                    scale[0] = scale[0] - 0.02;
                    if (scale[0] <= 1.0) {
                        scale[0] = 1.0;
                        timer.stop();
                    }
                }
                component.repaint();
            }
        });
        timer.start();
    }

    public static void colorTransition(final JComponent component, final Color from, final Color to, int duration) {
        final Timer timer = new Timer(20, null);
        final int steps = duration / 20;
        final int[] currentStep = new int[1];
        final int rDiff = to.getRed() - from.getRed();
        final int gDiff = to.getGreen() - from.getGreen();
        final int bDiff = to.getBlue() - from.getBlue();
        timer.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                currentStep[0] = currentStep[0] + 1;
                float progress = (float)currentStep[0] / (float)steps;
                int r = from.getRed() + (int)((float)rDiff * progress);
                int g = from.getGreen() + (int)((float)gDiff * progress);
                int b = from.getBlue() + (int)((float)bDiff * progress);
                component.setBackground(new Color(r, g, b));
                if (currentStep[0] >= steps) {
                    component.setBackground(to);
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    public static void bounce(final JComponent component) {
        final int originalY = component.getY();
        final Timer timer = new Timer(10, null);
        final int[] frame = new int[1];
        int bounceHeight = 20;
        timer.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                frame[0] = frame[0] + 1;
                double angle = Math.toRadians(frame[0] * 10);
                int offset = (int)(Math.abs(Math.sin(angle)) * 20.0);
                if (frame[0] > 36) {
                    component.setLocation(component.getX(), originalY);
                    timer.stop();
                } else {
                    component.setLocation(component.getX(), originalY - offset);
                }
            }
        });
        timer.start();
    }

    public static void shimmer(final JComponent component) {
        final Timer timer = new Timer(50, null);
        final float[] position = new float[]{-1.0f};
        timer.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                position[0] = position[0] + 0.1f;
                if (position[0] >= 2.0f) {
                    position[0] = -1.0f;
                    timer.stop();
                }
                component.putClientProperty("shimmerPosition", Float.valueOf(position[0]));
                component.repaint();
            }
        });
        timer.start();
    }

    public static Timer createRotationAnimation(final JComponent component) {
        final int[] angle = new int[1];
        Timer timer = new Timer(20, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                angle[0] = (angle[0] + 5) % 360;
                component.putClientProperty("rotationAngle", angle[0]);
                component.repaint();
            }
        });
        return timer;
    }

    public static void staggeredFadeIn(List<Component> components, int delayBetween) {
        int i = 0;
        while (i < components.size()) {
            final Component comp = components.get(i);
            Timer timer = new Timer(delayBetween * i, new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    AnimationUtils.fadeIn(comp, 300);
                    ((Timer)e.getSource()).stop();
                }
            });
            timer.setRepeats(false);
            timer.start();
            ++i;
        }
    }

    public static void animateValue(final int from, final int to, int duration, final ValueAnimationListener listener) {
        final Timer timer = new Timer(20, null);
        final int steps = duration / 20;
        final int[] currentStep = new int[1];
        final int diff = to - from;
        timer.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                currentStep[0] = currentStep[0] + 1;
                float progress = (float)currentStep[0] / (float)steps;
                int currentValue = from + (int)((float)diff * AnimationUtils.easeInOutCubic(progress));
                listener.onValueUpdate(currentValue);
                if (currentStep[0] >= steps) {
                    listener.onValueUpdate(to);
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    private static float easeInOutCubic(float t) {
        return t < 0.5f ? 4.0f * t * t * t : 1.0f - (float)Math.pow(-2.0f * t + 2.0f, 3.0) / 2.0f;
    }

    public static interface ValueAnimationListener {
        public void onValueUpdate(int var1);
    }
}
