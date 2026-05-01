package com.simulator.view.components;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RadarChart extends JPanel {
    private List<String> labels;
    private List<Double> values;
    private final int MAX_SCORE = 5;

    public RadarChart() {
        setOpaque(false);
        setPreferredSize(new Dimension(300, 300));
    }

    public void setData(List<String> labels, List<Double> values) {
        this.labels = labels;
        this.values = values;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (labels == null || labels.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(width, height) / 2 - 40;

        int numAxes = labels.size();
        double angleStep = 2 * Math.PI / numAxes;

        // Draw background circles/polygons
        g2.setColor(Color.LIGHT_GRAY);
        for (int i = 1; i <= MAX_SCORE; i++) {
            int r = radius * i / MAX_SCORE;
            drawPolygon(g2, centerX, centerY, r, numAxes, angleStep, false);
        }

        // Draw axes
        g2.setColor(Color.GRAY);
        for (int i = 0; i < numAxes; i++) {
            double angle = i * angleStep - Math.PI / 2;
            int x2 = centerX + (int) (radius * Math.cos(angle));
            int y2 = centerY + (int) (radius * Math.sin(angle));
            g2.drawLine(centerX, centerY, x2, y2);

            // Labels
            String label = labels.get(i);
            int lx = centerX + (int) ((radius + 20) * Math.cos(angle)) - 20;
            int ly = centerY + (int) ((radius + 20) * Math.sin(angle));
            g2.drawString(label, lx, ly);
        }

        // Draw data polygon
        g2.setColor(new Color(52, 152, 219, 150));
        Polygon dataPoly = new Polygon();
        for (int i = 0; i < numAxes; i++) {
            double val = values.get(i);
            double angle = i * angleStep - Math.PI / 2;
            int r = (int) (radius * val / MAX_SCORE);
            int x = centerX + (int) (r * Math.cos(angle));
            int y = centerY + (int) (r * Math.sin(angle));
            dataPoly.addPoint(x, y);
        }
        g2.fill(dataPoly);
        g2.setColor(new Color(52, 152, 219));
        g2.setStroke(new BasicStroke(2));
        g2.draw(dataPoly);
    }

    private void drawPolygon(Graphics2D g2, int cx, int cy, int r, int sides, double angleStep, boolean fill) {
        Polygon p = new Polygon();
        for (int i = 0; i < sides; i++) {
            double angle = i * angleStep - Math.PI / 2;
            p.addPoint(cx + (int) (r * Math.cos(angle)), cy + (int) (r * Math.sin(angle)));
        }
        if (fill) g2.fill(p);
        else g2.draw(p);
    }
}
