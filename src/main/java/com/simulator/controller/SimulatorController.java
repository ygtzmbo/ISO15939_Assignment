package com.simulator.controller;

import com.simulator.model.MeasurementModel;
import com.simulator.model.Scenario;
import com.simulator.view.MainFrame;
import com.simulator.view.panels.*;
import java.util.ArrayList;
import java.util.List;

public class SimulatorController {
    private MainFrame view;
    private MeasurementModel model;
    private List<StepPanel> steps;
    private int currentStep = 0;

    public SimulatorController(MainFrame view, MeasurementModel model) {
        this.view = view;
        this.model = model;
        this.steps = new ArrayList<>();

        initializeSteps();
        setupNavigation();
        updateView();
    }

    private void initializeSteps() {
        steps.add(new ProfilePanel());
        steps.add(new DefinePanel());
        steps.add(new PlanPanel());
        steps.add(new CollectPanel());
        steps.add(new AnalysePanel());

        for (int i = 0; i < steps.size(); i++) {
            view.addStepPanel(steps.get(i), "Step" + i);
        }

        // Initialize DefinePanel scenarios
        updateAvailableScenarios();
        
        // Add listener for mode change in Step 2
        DefinePanel dp = (DefinePanel) steps.get(1);
        dp.getCbMode().addActionListener(e -> updateAvailableScenarios());
    }

    private void updateAvailableScenarios() {
        DefinePanel dp = (DefinePanel) steps.get(1);
        String mode = dp.getSelectedMode();
        dp.setScenarios(model.getScenariosForMode(mode));
    }

    private void setupNavigation() {
        view.getBtnNext().addActionListener(e -> nextStep());
        view.getBtnBack().addActionListener(e -> backStep());
    }

    private void nextStep() {
        if (!steps.get(currentStep).validateInput()) return;

        if (currentStep < steps.size() - 1) {
            // Update model from current step if needed
            syncModel(currentStep);
            
            currentStep++;
            
            // Prepare next step view
            prepareStep(currentStep);
            
            updateView();
        } else {
            // Finish or reset?
            System.exit(0);
        }
    }

    private void backStep() {
        if (currentStep > 0) {
            currentStep--;
            updateView();
        }
    }

    private void syncModel(int stepIdx) {
        switch (stepIdx) {
            case 0: // Profile
                ProfilePanel pp = (ProfilePanel) steps.get(0);
                model.setUserName(pp.getUserName());
                model.setSchool(pp.getSchool());
                model.setSessionName(pp.getSessionName());
                break;
            case 1: // Define
                DefinePanel dp = (DefinePanel) steps.get(1);
                model.setQualityType(dp.getSelectedQualityType());
                model.setMode(dp.getSelectedMode());
                model.setSelectedScenario(dp.getSelectedScenario());
                break;
            case 3: // Collect
                // Data is already saved to model in validateInput of CollectPanel
                break;
        }
    }

    private void prepareStep(int stepIdx) {
        switch (stepIdx) {
            case 2: // Plan
                ((PlanPanel) steps.get(2)).updatePlan(model.getSelectedScenario());
                break;
            case 3: // Collect
                ((CollectPanel) steps.get(3)).updateScenario(model.getSelectedScenario());
                break;
            case 4: // Analyse
                ((AnalysePanel) steps.get(4)).updateAnalysis(model.getSelectedScenario());
                break;
        }
    }

    private void updateView() {
        view.showStep("Step" + currentStep);
        view.setStepTitle(steps.get(currentStep).getStepTitle());
        view.setStepIndicator(currentStep + 1, steps.size());
        
        view.getBtnBack().setEnabled(currentStep > 0);
        view.getBtnNext().setText(currentStep == steps.size() - 1 ? "Finish" : "Next");
    }
}
