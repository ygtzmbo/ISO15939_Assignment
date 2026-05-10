package com.simulator.view.panels;

import com.simulator.model.Dimension;
import com.simulator.model.Metric;
import com.simulator.model.Scenario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel to display the measurement plan details.
 */
public class PlanPanel extends StepPanel {
    private final JTable dataTable;
    private final DefaultTableModel listModel;

    public PlanPanel() {
        String[] header = {"Quality Aspect", "Data Point", "Information", "Threshold"};
        listModel = new DefaultTableModel(header, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        dataTable = new JTable(listModel);
        dataTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dataTable.setRowHeight(25);
        dataTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane container = new JScrollPane(dataTable);
        add(container, BorderLayout.CENTER);

        JLabel statusLabel = new JLabel("Examine the structured measurement strategy below.");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        add(statusLabel, BorderLayout.NORTH);
    }

    public void refreshSchema(Scenario activeScenario) {
        listModel.setRowCount(0);
        if (activeScenario == null) return;

        activeScenario.getAspects().forEach(aspect -> {
            aspect.getDataPoints().forEach(point -> {
                listModel.addRow(new Object[]{
                    aspect.getIdentifier(),
                    point.getLabel(),
                    point.getInfo(),
                    point.getUpperBound()
                });
            });
        });
    }

    @Override
    public String getStepTitle() { return "Step 3: Measurement Plan"; }

    @Override
    public boolean validateInput() { return true; }
}
