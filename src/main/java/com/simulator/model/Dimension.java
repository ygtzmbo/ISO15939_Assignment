package com.simulator.model;

import java.util.ArrayList;
import java.util.List;

public class Dimension {
    private String name;
    private double weight;
    private List<Metric> metrics;

    public Dimension(String name, double weight) {
        this.name = name;
        this.weight = weight;
        this.metrics = new ArrayList<>();
    }

    public void addMetric(Metric metric) {
        metrics.add(metric);
    }

    public String getName() { return name; }
    public double getWeight() { return weight; }
    public List<Metric> getMetrics() { return metrics; }

    public double getAverageScore() {
        if (metrics.isEmpty()) return 0;
        double sum = 0;
        for (Metric m : metrics) {
            sum += m.getNormalizedScore();
        }
        return sum / metrics.size();
    }
}
