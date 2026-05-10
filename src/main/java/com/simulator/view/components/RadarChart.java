package com.simulator.view.components;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RadarChart extends JPanel {
    private List<String> axisLabels;
    private List<Double> dataIndices;
    private static final int SCALE_LIMIT = 5;

    public RadarChart() {
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(320, 320));
    }

    public void setData(List<String> labels, List<Double> points) {
        this.axisLabels = labels;
        this.dataIndices = points;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (axisLabels == null || axisLabels.isEmpty()) return;

        Graphics2D canvas = (Graphics2D) g;
        canvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int midX = w / 2;
        int midY = h / 2;
        int maxRadius = Math.min(w, h) / 2 - 50;

        int axesCount = axisLabels.size();
        double sliceAngle = 2 * Math.PI / axesCount;

        // Render grid skeleton
        canvas.setColor(new Color(200, 200, 200));
        for (int step = 1; step <= SCALE_LIMIT; step++) {
            int currentR = maxRadius * step / SCALE_LIMIT;
            renderWireframe(canvas, midX, midY, currentR, axesCount, sliceAngle);
        }

        // Render axes and annotations
        canvas.setColor(Color.DARK_GRAY);
        canvas.setFont(new Font("Segoe UI", Font.BOLD, 12));
        for (int i = 0; i < axesCount; i++) {
            double theta = i * sliceAngle - Math.PI / 2;
            int endX = midX + (int) (maxRadius * Math.cos(theta));
            int endY = midY + (int) (maxRadius * Math.sin(theta));
            canvas.drawLine(midX, midY, endX, endY);

            String text = axisLabels.get(i);
            int tx = midX + (int) ((maxRadius + 25) * Math.cos(theta)) - 25;
            int ty = midY + (int) ((maxRadius + 15) * Math.sin(theta));
            canvas.drawString(text, tx, ty);
        }

        // Render data overlay
        renderOverlay(canvas, midX, midY, maxRadius, axesCount, sliceAngle);
    }

    private void renderWireframe(Graphics2D g2, int cx, int cy, int r, int points, double step) {
        Polygon poly = new Polygon();
        for (int i = 0; i < points; i++) {
            double a = i * step - Math.PI / 2;
            poly.addPoint(cx + (int) (r * Math.cos(a)), cy + (int) (r * Math.sin(a)));
        }
        g2.draw(poly);
    }

    private void renderOverlay(Graphics2D g2, int cx, int cy, int maxR, int count, double step) {
        Polygon shape = new Polygon();
        for (int i = 0; i < count; i++) {
            double magnitude = dataIndices.get(i);
            double a = i * step - Math.PI / 2;
            int r = (int) (maxR * magnitude / SCALE_LIMIT);
            shape.addPoint(cx + (int) (r * Math.cos(a)), cy + (int) (r * Math.sin(a)));
        }
        
        g2.setColor(new Color(41, 128, 185, 120));
        g2.fill(shape);
        g2.setColor(new Color(41, 128, 185));
        g2.setStroke(new BasicStroke(2.5f));
        g2.draw(shape);
    }
}
