package com.simulator.model;

public class Metric {
    private String name;
    private String description;
    private double maxValue;
    private double rawValue;
    private double normalizedScore;

    public Metric(String name, String description, double maxValue) {
        this.name = name;
        this.description = description;
        this.maxValue = maxValue;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getMaxValue() { return maxValue; }
    public double getRawValue() { return rawValue; }
    public void setRawValue(double rawValue) {
        this.rawValue = rawValue;
        this.normalizedScore = Math.min(5.0, (rawValue / maxValue) * 5.0);
    }
    public double getNormalizedScore() { return normalizedScore; }
}
