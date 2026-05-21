package com.finops.backend.service;

import org.springframework.stereotype.Service;

@Service
public class RecommendationService {

    public String generateRecommendation(double monthlyCost,
                                         double savingsOpportunity) {

        if (savingsOpportunity > 10000) {

            return "Critical savings opportunity detected. " +
                    "Consider downsizing EC2 instances and enabling auto-scaling.";

        } else if (savingsOpportunity > 5000) {

            return "Moderate savings opportunity detected. " +
                    "Schedule idle resources shutdown during non-working hours.";

        } else {

            return "Infrastructure utilization is healthy. " +
                    "No major optimization required.";
        }
    }
}