package com.simulator.view.panels;

import com.simulator.model.Scenario;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DefinePanel extends StepPanel {
    private JComboBox<String> cbQualityType;
    private JComboBox<String> cbMode;
    private JComboBox<ScenarioWrapper> cbScenario;

    public DefinePanel() {
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 20));
        formPanel.setOpaque(false);

        JLabel lblQuality = new JLabel("Quality Type:");
        lblQuality.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cbQualityType = new JComboBox<>(new String[]{"Internal Quality", "External Quality"});
        cbQualityType.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel lblMode = new JLabel("Application Mode:");
        lblMode.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cbMode = new JComboBox<>(new String[]{"Health", "Education"});
        cbMode.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel lblScenario = new JLabel("Select Scenario:");
        lblScenario.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cbScenario = new JComboBox<>();
        cbScenario.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        formPanel.add(lblQuality);
        formPanel.add(cbQualityType);
        formPanel.add(lblMode);
        formPanel.add(cbMode);
        formPanel.add(lblScenario);
        formPanel.add(cbScenario);

        add(formPanel, BorderLayout.NORTH);
    }

    public void setScenarios(List<Scenario> scenarios) {
        cbScenario.removeAllItems();
        for (Scenario s : scenarios) {
            cbScenario.addItem(new ScenarioWrapper(s));
        }
    }

    public JComboBox<String> getCbMode() { return cbMode; }
    public String getSelectedQualityType() { return (String) cbQualityType.getSelectedItem(); }
    public String getSelectedMode() { return (String) cbMode.getSelectedItem(); }
    public Scenario getSelectedScenario() {
        ScenarioWrapper wrapper = (ScenarioWrapper) cbScenario.getSelectedItem();
        return wrapper != null ? wrapper.getScenario() : null;
    }

    @Override
    public String getStepTitle() { return "Step 2: Measurement Definition"; }

    @Override
    public boolean validateInput() {
        if (cbScenario.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a scenario.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    // Helper class for JComboBox display
    private static class ScenarioWrapper {
        private Scenario scenario;
        public ScenarioWrapper(Scenario scenario) { this.scenario = scenario; }
        public Scenario getScenario() { return scenario; }
        @Override
        public String toString() { return scenario.getName(); }
    }
}
