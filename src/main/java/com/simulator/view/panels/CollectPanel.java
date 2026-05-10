package com.simulator.view.panels;

import com.simulator.model.Dimension;
import com.simulator.model.Metric;
import com.simulator.model.Scenario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Panel for inputting raw measurement data.
 */
public class CollectPanel extends StepPanel {
    private final JTable inputGrid;
    private final DefaultTableModel gridModel;
    private Scenario activeScenario;

    public CollectPanel() {
        String[] headers = {"Metric Point", "Threshold", "Actual Value (Input)", "Indexed Score (1-5)"};
        gridModel = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return column == 2; }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex >= 1 ? Double.class : String.class;
            }
        };
        
        inputGrid = new JTable(gridModel);
        inputGrid.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inputGrid.setRowHeight(30);
        inputGrid.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Real-time score indexing
        gridModel.addTableModelListener(e -> {
            int r = e.getFirstRow();
            int c = e.getColumn();
            if (c == 2) {
                Double val = (Double) gridModel.getValueAt(r, 2);
                Double limit = (Double) gridModel.getValueAt(r, 1);
                if (val != null && limit != null && limit > 0) {
                    double indexed = Math.min(5.0, (val / limit) * 5.0);
                    gridModel.setValueAt(Math.round(indexed * 100.0) / 100.0, r, 3);
                }
            }
        });

        JScrollPane wrapper = new JScrollPane(inputGrid);
        add(wrapper, BorderLayout.CENTER);

        JLabel hintLabel = new JLabel("Input the observed metrics. The system calculates indexed results dynamically.");
        hintLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        hintLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        add(hintLabel, BorderLayout.NORTH);
    }

    public void setupScenario(Scenario target) {
        this.activeScenario = target;
        gridModel.setRowCount(0);
        if (target == null) return;

        target.getAspects().forEach(aspect -> {
            aspect.getDataPoints().forEach(point -> {
                gridModel.addRow(new Object[]{
                    point.getLabel(),
                    point.getUpperBound(),
                    0.0,
                    0.0
                });
            });
        });
    }

    public void commitData() {
        if (activeScenario == null) return;
        int row = 0;
        for (Dimension aspect : activeScenario.getAspects()) {
            for (Metric point : aspect.getDataPoints()) {
                Double value = (Double) gridModel.getValueAt(row++, 2);
                point.recordData(value != null ? value : 0.0);
            }
        }
    }

    @Override
    public String getStepTitle() { return "Step 4: Data Collection"; }

    @Override
    public boolean validateInput() {
        for (int i = 0; i < gridModel.getRowCount(); i++) {
            Double val = (Double) gridModel.getValueAt(i, 2);
            if (val == null || val < 0) {
                JOptionPane.showMessageDialog(this, "Input must be a non-negative numeric value.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        commitData();
        return true;
    }
}
