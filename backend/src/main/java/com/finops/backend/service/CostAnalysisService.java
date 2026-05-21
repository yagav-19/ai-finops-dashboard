package com.finops.backend.service;

import com.finops.backend.model.CloudCost;
import org.springframework.stereotype.Service;

@Service
public class CostAnalysisService {

    public CloudCost analyzeCosts() {

        double monthlyCost = 14500;
        double predictedCost = 18200;
        double savingsOpportunity = 6200;

        String recommendation;

        if (savingsOpportunity > 5000) {
            recommendation =
                    "Development EC2 server is underutilized. Consider downsizing or scheduling auto-shutdown.";
        } else {
            recommendation =
                    "Infrastructure utilization looks healthy.";
        }

        return new CloudCost(
                monthlyCost,
                predictedCost,
                savingsOpportunity,
                recommendation
        );
    }
}