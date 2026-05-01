package com.simulator.view.panels;

import com.simulator.model.Dimension;
import com.simulator.model.Scenario;
import com.simulator.view.components.RadarChart;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AnalysePanel extends StepPanel {
    private JPanel resultsPanel;
    private RadarChart radarChart;
    private JLabel lblFinalScore;
    private JTextArea txtGapAnalysis;

    public AnalysePanel() {
        setLayout(new BorderLayout());

        // Left side: Progress bars and Final Score
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        leftPanel.setPreferredSize(new java.awt.Dimension(350, 400));

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setOpaque(false);
        
        leftPanel.add(new JLabel("Dimension Scores:"));
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(resultsPanel);
        leftPanel.add(Box.createVerticalGlue());

        lblFinalScore = new JLabel("Final Weighted Score: 0.0");
        lblFinalScore.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblFinalScore.setForeground(new Color(44, 62, 80));
        leftPanel.add(lblFinalScore);
        leftPanel.add(Box.createVerticalStrut(20));

        add(leftPanel, BorderLayout.WEST);

        // Center: Radar Chart
        radarChart = new RadarChart();
        add(radarChart, BorderLayout.CENTER);

        // Bottom: Gap Analysis
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Gap Analysis"));
        
        txtGapAnalysis = new JTextArea(4, 50);
        txtGapAnalysis.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtGapAnalysis.setEditable(false);
        txtGapAnalysis.setLineWrap(true);
        txtGapAnalysis.setWrapStyleWord(true);
        bottomPanel.add(new JScrollPane(txtGapAnalysis), BorderLayout.CENTER);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void updateAnalysis(Scenario scenario) {
        resultsPanel.removeAll();
        List<String> labels = new ArrayList<>();
        List<Double> values = new ArrayList<>();
        StringBuilder gapText = new StringBuilder();

        for (Dimension d : scenario.getDimensions()) {
            double score = d.getAverageScore();
            labels.add(d.getName());
            values.add(score);

            JPanel p = new JPanel(new BorderLayout());
            p.setOpaque(false);
            p.setMaximumSize(new java.awt.Dimension(300, 40));
            p.add(new JLabel(d.getName() + " (" + (int)(d.getWeight()*100) + "%)"), BorderLayout.NORTH);
            
            JProgressBar pb = new JProgressBar(0, 50);
            pb.setValue((int)(score * 10));
            pb.setStringPainted(true);
            pb.setString(String.format("%.2f / 5.0", score));
            p.add(pb, BorderLayout.CENTER);
            
            resultsPanel.add(p);
            resultsPanel.add(Box.createVerticalStrut(10));

            // Simple Gap Analysis logic
            if (score < 3.0) {
                gapText.append("Critical Gap in ").append(d.getName()).append(": Score is below 3.0. Immediate improvement needed.\n");
            } else if (score < 4.5) {
                gapText.append("Moderate Gap in ").append(d.getName()).append(": Room for optimization.\n");
            } else {
                gapText.append(d.getName()).append(" is performing excellently.\n");
            }
        }

        double finalScore = scenario.calculateFinalScore();
        lblFinalScore.setText(String.format("Final Weighted Score: %.2f / 5.0", finalScore));
        
        radarChart.setData(labels, values);
        txtGapAnalysis.setText(gapText.toString());

        revalidate();
        repaint();
    }

    @Override
    public String getStepTitle() { return "Step 5: Measurement Analysis"; }

    @Override
    public boolean validateInput() { return true; }
}
