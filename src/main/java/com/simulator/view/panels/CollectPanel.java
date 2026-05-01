package com.simulator.view.panels;

import com.simulator.model.Dimension;
import com.simulator.model.Metric;
import com.simulator.model.Scenario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CollectPanel extends StepPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private Scenario currentScenario;

    public CollectPanel() {
        String[] columns = {"Metric", "Max Value", "Raw Value (Input)", "Normalized (1-5)"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return column == 2; }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex >= 1 ? Double.class : String.class;
            }
        };
        
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Auto-calculate normalized score on edit
        tableModel.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int col = e.getColumn();
            if (col == 2) {
                Double raw = (Double) tableModel.getValueAt(row, 2);
                Double max = (Double) tableModel.getValueAt(row, 1);
                if (raw != null && max != null) {
                    double norm = Math.min(5.0, (raw / max) * 5.0);
                    tableModel.setValueAt(Math.round(norm * 100.0) / 100.0, row, 3);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JLabel lblInfo = new JLabel("Enter raw data for each metric. Normalized scores update automatically.");
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        add(lblInfo, BorderLayout.NORTH);
    }

    public void updateScenario(Scenario scenario) {
        this.currentScenario = scenario;
        tableModel.setRowCount(0);
        if (scenario == null) return;

        for (Dimension d : scenario.getDimensions()) {
            for (Metric m : d.getMetrics()) {
                tableModel.addRow(new Object[]{
                    m.getName(),
                    m.getMaxValue(),
                    0.0,
                    0.0
                });
            }
        }
    }

    public void saveToModel() {
        if (currentScenario == null) return;
        int rowIndex = 0;
        for (Dimension d : currentScenario.getDimensions()) {
            for (Metric m : d.getMetrics()) {
                Double raw = (Double) tableModel.getValueAt(rowIndex++, 2);
                m.setRawValue(raw != null ? raw : 0.0);
            }
        }
    }

    @Override
    public String getStepTitle() { return "Step 4: Data Collection"; }

    @Override
    public boolean validateInput() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Double raw = (Double) tableModel.getValueAt(i, 2);
            if (raw == null || raw < 0) {
                JOptionPane.showMessageDialog(this, "Please enter valid positive numbers for all metrics.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
        saveToModel();
        return true;
    }
}
