package com.simulator.view.panels;

import com.simulator.model.Dimension;
import com.simulator.model.Scenario;
import com.simulator.view.components.RadarChart;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Final step panel for result analysis and visualization.
 */
public class AnalysePanel extends StepPanel {
    private final JPanel summaryBox;
    private final RadarChart spiderGraph;
    private final JLabel scoreSummary;
    private final JTextArea gapReport;

    public AnalysePanel() {
        setLayout(new BorderLayout());

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setOpaque(false);
        sidebar.setPreferredSize(new java.awt.Dimension(350, 400));

        summaryBox = new JPanel();
        summaryBox.setLayout(new BoxLayout(summaryBox, BoxLayout.Y_AXIS));
        summaryBox.setOpaque(false);
        
        sidebar.add(new JLabel("Performance Metrics:"));
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(summaryBox);
        sidebar.add(Box.createVerticalGlue());

        scoreSummary = new JLabel("Aggregate Weighted Index: 0.0");
        scoreSummary.setFont(new Font("Segoe UI", Font.BOLD, 18));
        scoreSummary.setForeground(new Color(20, 40, 60));
        sidebar.add(scoreSummary);
        sidebar.add(Box.createVerticalStrut(20));

        add(sidebar, BorderLayout.WEST);

        spiderGraph = new RadarChart();
        add(spiderGraph, BorderLayout.CENTER);

        JPanel infoRegion = new JPanel(new BorderLayout());
        infoRegion.setOpaque(false);
        infoRegion.setBorder(BorderFactory.createTitledBorder("Strategic Gap Analysis"));
        
        gapReport = new JTextArea(4, 50);
        gapReport.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gapReport.setEditable(false);
        gapReport.setLineWrap(true);
        gapReport.setWrapStyleWord(true);
        infoRegion.add(new JScrollPane(gapReport), BorderLayout.CENTER);
        
        add(infoRegion, BorderLayout.SOUTH);
    }

    public void processAnalytics(Scenario activeScenario) {
        summaryBox.removeAll();
        List<String> tags = new ArrayList<>();
        List<Double> metrics = new ArrayList<>();
        StringBuilder analysisLog = new StringBuilder();

        activeScenario.getAspects().forEach(aspect -> {
            double mean = aspect.calculateMeanScore();
            tags.add(aspect.getIdentifier());
            metrics.add(mean);

            JPanel item = new JPanel(new BorderLayout());
            item.setOpaque(false);
            item.setMaximumSize(new java.awt.Dimension(300, 45));
            item.add(new JLabel(aspect.getIdentifier() + " (Weight: " + (int)(aspect.getImportanceFactor()*100) + "%)"), BorderLayout.NORTH);
            
            JProgressBar indicator = new JProgressBar(0, 100);
            indicator.setValue((int)(mean * 20));
            indicator.setStringPainted(true);
            indicator.setString(String.format("%.2f pts", mean));
            item.add(indicator, BorderLayout.CENTER);
            
            summaryBox.add(item);
            summaryBox.add(Box.createVerticalStrut(12));

            // Heuristic Gap Detection
            if (mean < 2.5) {
                analysisLog.append("[CRITICAL] ").append(aspect.getIdentifier()).append(" requires immediate corrective action.\n");
            } else if (mean < 4.0) {
                analysisLog.append("[MODERATE] ").append(aspect.getIdentifier()).append(" has potential for optimization.\n");
            } else {
                analysisLog.append("[OPTIMAL] ").append(aspect.getIdentifier()).append(" meets quality standards.\n");
            }
        });

        double totalIndex = activeScenario.computeOverallResult();
        scoreSummary.setText(String.format("Aggregate Weighted Index: %.2f / 5.0", totalIndex));
        
        spiderGraph.setData(tags, metrics);
        gapReport.setText(analysisLog.toString());

        revalidate();
        repaint();
    }

    @Override
    public String getStepTitle() { return "Step 5: Measurement Analysis"; }

    @Override
    public boolean validateInput() { return true; }
}
