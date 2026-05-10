package com.simulator.view.panels;

import com.simulator.model.Scenario;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DefinePanel extends StepPanel {
    private final JComboBox<String> qualityPicker;
    private final JComboBox<String> modePicker;
    private final JComboBox<ScenarioItem> scenarioPicker;

    public DefinePanel() {
        JPanel formGrid = new JPanel(new GridLayout(3, 2, 10, 20));
        formGrid.setOpaque(false);

        JLabel qualityLabel = new JLabel("Quality Model:");
        qualityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        qualityPicker = new JComboBox<>(new String[]{"Internal Quality", "External Quality"});
        qualityPicker.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel modeLabel = new JLabel("Operating Context:");
        modeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        modePicker = new JComboBox<>(new String[]{"Health", "Education"});
        modePicker.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel scenarioLabel = new JLabel("Target Scenario:");
        scenarioLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        scenarioPicker = new JComboBox<>();
        scenarioPicker.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        formGrid.add(qualityLabel);
        formGrid.add(qualityPicker);
        formGrid.add(modeLabel);
        formGrid.add(modePicker);
        formGrid.add(scenarioLabel);
        formGrid.add(scenarioPicker);

        add(formGrid, BorderLayout.NORTH);
    }

    public void setScenarios(List<Scenario> scenarios) {
        scenarioPicker.removeAllItems();
        scenarios.forEach(s -> scenarioPicker.addItem(new ScenarioItem(s)));
    }

    public JComboBox<String> getCbMode() { return modePicker; }
    public String getSelectedQualityType() { return (String) qualityPicker.getSelectedItem(); }
    public String getSelectedMode() { return (String) modePicker.getSelectedItem(); }
    public Scenario getSelectedScenario() {
        ScenarioItem item = (ScenarioItem) scenarioPicker.getSelectedItem();
        return item != null ? item.getScenario() : null;
    }

    @Override
    public String getStepTitle() { return "Step 2: Measurement Definition"; }

    @Override
    public boolean validateInput() {
        if (scenarioPicker.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Select a valid scenario to continue.", "Input Required", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Wrapper for Scenario display in ComboBox.
     */
    private static class ScenarioItem {
        private final Scenario scenario;
        public ScenarioItem(Scenario scenario) { this.scenario = scenario; }
        public Scenario getScenario() { return scenario; }
        @Override
        public String toString() { return scenario.getTitle(); }
    }
}
