package com.simulator.model;

import java.util.ArrayList;
import java.util.List;

public class Scenario {
    private String name;
    private String mode;
    private List<Dimension> dimensions;

    public Scenario(String name, String mode) {
        this.name = name;
        this.mode = mode;
        this.dimensions = new ArrayList<>();
    }

    public void addDimension(Dimension dimension) {
        dimensions.add(dimension);
    }

    public String getName() { return name; }
    public String getMode() { return mode; }
    public List<Dimension> getDimensions() { return dimensions; }

    public double calculateFinalScore() {
        double finalScore = 0;
        double totalWeight = 0;
        for (Dimension d : dimensions) {
            finalScore += d.getAverageScore() * d.getWeight();
            totalWeight += d.getWeight();
        }
        return totalWeight == 0 ? 0 : finalScore / totalWeight;
    }
}
