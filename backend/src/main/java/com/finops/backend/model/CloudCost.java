package com.finops.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CloudCost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double monthlyCost;
    private double predictedCost;
    private double savingsOpportunity;
    private String recommendation;

    public CloudCost() {
    }

    public CloudCost(double monthlyCost,
                     double predictedCost,
                     double savingsOpportunity,
                     String recommendation) {

        this.monthlyCost = monthlyCost;
        this.predictedCost = predictedCost;
        this.savingsOpportunity = savingsOpportunity;
        this.recommendation = recommendation;
    }

    public Long getId() {
        return id;
    }

    public double getMonthlyCost() {
        return monthlyCost;
    }

    public double getPredictedCost() {
        return predictedCost;
    }

    public double getSavingsOpportunity() {
        return savingsOpportunity;
    }

    public String getRecommendation() {
        return recommendation;
    }
}