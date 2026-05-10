package com.simulator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a specific quality dimension containing multiple metrics.
 */
public class Dimension {
    private final String identifier;
    private final double importanceFactor;
    private final List<Metric> dataPoints;

    public Dimension(String identifier, double importanceFactor) {
        this.identifier = identifier;
        this.importanceFactor = importanceFactor;
        this.dataPoints = new ArrayList<>();
    }

    public void registerMetric(Metric point) {
        dataPoints.add(point);
    }

    public String getIdentifier() { return identifier; }
    public double getImportanceFactor() { return importanceFactor; }
    public List<Metric> getDataPoints() { return dataPoints; }

    /**
     * Calculates the mean normalized score across all associated metrics.
     */
    public double calculateMeanScore() {
        return dataPoints.stream()
                .mapToDouble(Metric::getIndexedResult)
                .average()
                .orElse(0.0);
    }
}
