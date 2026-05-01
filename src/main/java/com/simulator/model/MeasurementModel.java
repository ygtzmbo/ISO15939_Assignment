package com.simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MeasurementModel {
    private String userName;
    private String school;
    private String sessionName;
    private String qualityType;
    private String mode;
    private Scenario selectedScenario;
    private List<Scenario> availableScenarios;

    public MeasurementModel() {
        this.availableScenarios = new ArrayList<>();
        initializeScenarios();
    }

    private void initializeScenarios() {
        // Health Mode Scenarios
        Scenario s1 = new Scenario("Hospital Efficiency", "Health");
        Dimension d1 = new Dimension("Patient Care", 0.6);
        d1.addMetric(new Metric("Wait Time", "Average wait time in hours (Max 10)", 10));
        d1.addMetric(new Metric("Satisfaction", "Patient satisfaction score (Max 100)", 100));
        s1.addDimension(d1);
        Dimension d2 = new Dimension("Resource Utilization", 0.4);
        d2.addMetric(new Metric("Bed Occupancy", "Percentage of beds occupied (Max 100)", 100));
        d2.addMetric(new Metric("Staff Ratio", "Staff-to-patient ratio (Max 1.0)", 1.0));
        s1.addDimension(d2);
        availableScenarios.add(s1);

        Scenario s2 = new Scenario("Diagnostic Accuracy", "Health");
        Dimension d3 = new Dimension("Accuracy", 0.7);
        d3.addMetric(new Metric("Error Rate", "Percentage of errors (Lower is better, but here we assume raw/max)", 10));
        d3.addMetric(new Metric("Review Time", "Time for expert review (Max 48h)", 48));
        s2.addDimension(d3);
        availableScenarios.add(s2);

        // Education Mode Scenarios
        Scenario s3 = new Scenario("Student Performance", "Education");
        Dimension d4 = new Dimension("Academic", 0.8);
        d4.addMetric(new Metric("GPA", "Average GPA (Max 4.0)", 4.0));
        d4.addMetric(new Metric("Completion Rate", "Percentage of students finishing (Max 100)", 100));
        s3.addDimension(d4);
        Dimension d5 = new Dimension("Engagement", 0.2);
        d5.addMetric(new Metric("Attendance", "Average attendance rate (Max 100)", 100));
        s3.addDimension(d5);
        availableScenarios.add(s3);

        Scenario s4 = new Scenario("Institutional Quality", "Education");
        Dimension d6 = new Dimension("Facilities", 0.5);
        d6.addMetric(new Metric("Library Usage", "Average weekly visits per student (Max 20)", 20));
        d6.addMetric(new Metric("Lab Tech", "Investment in labs (Max 1M)", 1000000));
        s4.addDimension(d6);
        availableScenarios.add(s4);
    }

    public List<Scenario> getScenariosForMode(String mode) {
        List<Scenario> result = new ArrayList<>();
        for (Scenario s : availableScenarios) {
            if (s.getMode().equalsIgnoreCase(mode)) {
                result.add(s);
            }
        }
        return result;
    }

    // Getters and Setters
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }
    public String getSessionName() { return sessionName; }
    public void setSessionName(String sessionName) { this.sessionName = sessionName; }
    public String getQualityType() { return qualityType; }
    public void setQualityType(String qualityType) { this.qualityType = qualityType; }
    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }
    public Scenario getSelectedScenario() { return selectedScenario; }
    public void setSelectedScenario(Scenario selectedScenario) { this.selectedScenario = selectedScenario; }
}
