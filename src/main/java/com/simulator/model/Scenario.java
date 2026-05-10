package com.simulator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates a measurement scenario with multiple quality aspects.
 */
public class Scenario {
    private final String title;
    private final String category;
    private final List<Dimension> aspects;

    public Scenario(String title, String category) {
        this.title = title;
        this.category = category;
        this.aspects = new ArrayList<>();
    }

    public void registerAspect(Dimension aspect) {
        aspects.add(aspect);
    }

    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public List<Dimension> getAspects() { return aspects; }

    /**
     * Computes the final weighted score for the entire scenario.
     */
    public double computeOverallResult() {
        double totalWeightedScore = aspects.stream()
                .mapToDouble(a -> a.calculateMeanScore() * a.getImportanceFactor())
                .sum();
        
        double cumulativeImportance = aspects.stream()
                .mapToDouble(Dimension::getImportanceFactor)
                .sum();

        return cumulativeImportance == 0 ? 0 : totalWeightedScore / cumulativeImportance;
    }
}
