package com.finops.backend.service;

import org.springframework.stereotype.Service;

@Service
public class PredictionService {

    public double predictNextMonthCost(double currentCost,
                                       double usageGrowthPercentage) {

        double predictedCost =
                currentCost + (currentCost * usageGrowthPercentage / 100);

        return predictedCost;
    }
}