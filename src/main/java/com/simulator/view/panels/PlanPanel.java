package com.simulator.view.panels;

import com.simulator.model.Dimension;
import com.simulator.model.Metric;
import com.simulator.model.Scenario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PlanPanel extends StepPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    public PlanPanel() {
        String[] columns = {"Dimension", "Metric", "Description", "Max Value"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JLabel lblInfo = new JLabel("Review the measurement plan for the selected scenario.");
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        add(lblInfo, BorderLayout.NORTH);
    }

    public void updatePlan(Scenario scenario) {
        tableModel.setRowCount(0);
        if (scenario == null) return;

        for (Dimension d : scenario.getDimensions()) {
            for (Metric m : d.getMetrics()) {
                tableModel.addRow(new Object[]{
                    d.getName(),
                    m.getName(),
                    m.getDescription(),
                    m.getMaxValue()
                });
            }
        }
    }

    @Override
    public String getStepTitle() { return "Step 3: Measurement Plan"; }

    @Override
    public boolean validateInput() { return true; }
}
