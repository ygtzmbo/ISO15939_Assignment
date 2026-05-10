package com.simulator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main model class holding the application state and scenario data.
 */
public class MeasurementModel {
    private String operator;
    private String institution;
    private String trackingId;
    private String assessmentType;
    private String operationalMode;
    private Scenario activeScenario;
    private final List<Scenario> registry;

    public MeasurementModel() {
        this.registry = new ArrayList<>();
        populateScenarios();
    }

    private void populateScenarios() {
        // Health Mode Scenarios
        Scenario s1 = new Scenario("Hospital Efficiency", "Health");
        Dimension d1 = new Dimension("Patient Care", 0.6);
        d1.registerMetric(new Metric("Wait Time", "Average wait time in hours (Max 10)", 10));
        d1.registerMetric(new Metric("Satisfaction", "Patient satisfaction score (Max 100)", 100));
        s1.registerAspect(d1);
        Dimension d2 = new Dimension("Resource Utilization", 0.4);
        d2.registerMetric(new Metric("Bed Occupancy", "Percentage of beds occupied (Max 100)", 100));
        d2.registerMetric(new Metric("Staff Ratio", "Staff-to-patient ratio (Max 1.0)", 1.0));
        s1.registerAspect(d2);
        registry.add(s1);

        Scenario s2 = new Scenario("Diagnostic Accuracy", "Health");
        Dimension d3 = new Dimension("Accuracy", 0.7);
        d3.registerMetric(new Metric("Error Rate", "Percentage of errors", 10));
        d3.registerMetric(new Metric("Review Time", "Time for expert review (Max 48h)", 48));
        s2.registerAspect(d3);
        registry.add(s2);

        // Education Mode Scenarios
        Scenario s3 = new Scenario("Student Performance", "Education");
        Dimension d4 = new Dimension("Academic", 0.8);
        d4.registerMetric(new Metric("GPA", "Average GPA (Max 4.0)", 4.0));
        d4.registerMetric(new Metric("Completion Rate", "Percentage of students finishing (Max 100)", 100));
        s3.registerAspect(d4);
        Dimension d5 = new Dimension("Engagement", 0.2);
        d5.registerMetric(new Metric("Attendance", "Average attendance rate (Max 100)", 100));
        s3.registerAspect(d5);
        registry.add(s3);

        Scenario s4 = new Scenario("Institutional Quality", "Education");
        Dimension d6 = new Dimension("Facilities", 0.5);
        d6.registerMetric(new Metric("Library Usage", "Average weekly visits per student (Max 20)", 20));
        d6.registerMetric(new Metric("Lab Tech", "Investment in labs (Max 1M)", 1000000));
        s4.registerAspect(d6);
        registry.add(s4);
    }

    public List<Scenario> filterScenariosByMode(String mode) {
        return registry.stream()
                .filter(s -> s.getCategory().equalsIgnoreCase(mode))
                .collect(Collectors.toList());
    }

    // Getters and Setters
    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }
    public String getInstitution() { return institution; }
    public void setInstitution(String institution) { this.institution = institution; }
    public String getTrackingId() { return trackingId; }
    public void setTrackingId(String trackingId) { this.trackingId = trackingId; }
    public String getAssessmentType() { return assessmentType; }
    public void setAssessmentType(String assessmentType) { this.assessmentType = assessmentType; }
    public String getOperationalMode() { return operationalMode; }
    public void setOperationalMode(String operationalMode) { this.operationalMode = operationalMode; }
    public Scenario getActiveScenario() { return activeScenario; }
    public void setActiveScenario(Scenario activeScenario) { this.activeScenario = activeScenario; }
}
