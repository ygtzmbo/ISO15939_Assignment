package com.simulator.model;

/**
 * Represents a single measurement metric within a dimension.
 */
public class Metric {
    private final String label;
    private final String info;
    private final double upperBound;
    private double inputData;
    private double indexedResult;

    public Metric(String label, String info, double upperBound) {
        this.label = label;
        this.info = info;
        this.upperBound = upperBound;
    }

    public String getLabel() { return label; }
    public String getInfo() { return info; }
    public double getUpperBound() { return upperBound; }
    public double getInputData() { return inputData; }

    /**
     * Updates the raw input value and computes the normalized score.
     */
    public void recordData(double value) {
        this.inputData = value;
        this.indexedResult = calculateIndexedScore(value);
    }

    private double calculateIndexedScore(double raw) {
        double ratio = raw / upperBound;
        double scaled = ratio * 5.0;
        return Math.min(5.0, Math.max(0.0, scaled));
    }

    public double getIndexedResult() { return indexedResult; }
}
