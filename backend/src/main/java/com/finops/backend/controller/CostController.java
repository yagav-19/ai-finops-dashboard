package com.finops.backend.controller;

import com.finops.backend.model.CloudCost;
import com.finops.backend.service.CostAnalysisService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CostController {

    private final CostAnalysisService costAnalysisService;

    public CostController(CostAnalysisService costAnalysisService) {
        this.costAnalysisService = costAnalysisService;
    }

    @GetMapping("/api/costs")
    public CloudCost getCosts() {
        return costAnalysisService.analyzeCosts();
    }
}