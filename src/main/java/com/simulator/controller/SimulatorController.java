package com.simulator.controller;

import com.simulator.model.MeasurementModel;
import com.simulator.model.Scenario;
import com.simulator.view.MainFrame;
import com.simulator.view.panels.*;
import java.util.ArrayList;
import java.util.List;

public class SimulatorController {
    private final MainFrame mainView;
    private final MeasurementModel coreModel;
    private final List<StepPanel> workflowSteps;
    private int activeIndex = 0;

    public SimulatorController(MainFrame mainView, MeasurementModel coreModel) {
        this.mainView = mainView;
        this.coreModel = coreModel;
        this.workflowSteps = new ArrayList<>();

        buildWorkflow();
        attachNavHandlers();
        refreshDisplay();
    }

    private void buildWorkflow() {
        workflowSteps.add(new ProfilePanel());
        workflowSteps.add(new DefinePanel());
        workflowSteps.add(new PlanPanel());
        workflowSteps.add(new CollectPanel());
        workflowSteps.add(new AnalysePanel());

        for (int i = 0; i < workflowSteps.size(); i++) {
            mainView.addStepPanel(workflowSteps.get(i), "WorkflowStep" + i);
        }

        refreshScenarioRegistry();
        
        // Mode switch listener
        DefinePanel selectionPanel = (DefinePanel) workflowSteps.get(1);
        selectionPanel.getCbMode().addActionListener(e -> refreshScenarioRegistry());
    }

    private void refreshScenarioRegistry() {
        DefinePanel dp = (DefinePanel) workflowSteps.get(1);
        String category = dp.getSelectedMode();
        dp.setScenarios(coreModel.filterScenariosByMode(category));
    }

    private void attachNavHandlers() {
        mainView.getBtnNext().addActionListener(e -> moveToNext());
        mainView.getBtnBack().addActionListener(e -> moveToPrevious());
    }

    private void moveToNext() {
        if (!workflowSteps.get(activeIndex).validateInput()) return;

        if (activeIndex < workflowSteps.size() - 1) {
            persistState(activeIndex);
            activeIndex++;
            configureStep(activeIndex);
            refreshDisplay();
        } else {
            System.exit(0);
        }
    }

    private void moveToPrevious() {
        if (activeIndex > 0) {
            activeIndex--;
            refreshDisplay();
        }
    }

    private void persistState(int index) {
        switch (index) {
            case 0:
                ProfilePanel pp = (ProfilePanel) workflowSteps.get(0);
                coreModel.setOperator(pp.getUserName());
                coreModel.setInstitution(pp.getSchool());
                coreModel.setTrackingId(pp.getSessionName());
                break;
            case 1:
                DefinePanel dp = (DefinePanel) workflowSteps.get(1);
                coreModel.setAssessmentType(dp.getSelectedQualityType());
                coreModel.setOperationalMode(dp.getSelectedMode());
                coreModel.setActiveScenario(dp.getSelectedScenario());
                break;
        }
    }

    private void configureStep(int index) {
        switch (index) {
            case 2:
                ((PlanPanel) workflowSteps.get(2)).refreshSchema(coreModel.getActiveScenario());
                break;
            case 3:
                ((CollectPanel) workflowSteps.get(3)).setupScenario(coreModel.getActiveScenario());
                break;
            case 4:
                ((AnalysePanel) workflowSteps.get(4)).processAnalytics(coreModel.getActiveScenario());
                break;
        }
    }

    private void refreshDisplay() {
        mainView.showStep("WorkflowStep" + activeIndex);
        mainView.setStepTitle(workflowSteps.get(activeIndex).getStepTitle());
        mainView.setStepIndicator(activeIndex + 1, workflowSteps.size());
        
        mainView.getBtnBack().setEnabled(activeIndex > 0);
        mainView.getBtnNext().setText(activeIndex == workflowSteps.size() - 1 ? "Complete" : "Proceed");
    }
}
