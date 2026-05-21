package com.finops.backend.controller;

import com.finops.backend.ai.GeminiService;
import com.finops.backend.model.CloudCost;
import com.finops.backend.repository.CloudCostRepository;
import com.finops.backend.service.PredictionService;
import com.finops.backend.service.RecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/cloud-costs")
public class CloudCostController {

    private final CloudCostRepository cloudCostRepository;
    private final PredictionService predictionService;
    private final RecommendationService recommendationService;
    private final GeminiService geminiService;

    public CloudCostController(
            CloudCostRepository cloudCostRepository,
            PredictionService predictionService,
            RecommendationService recommendationService,
            GeminiService geminiService) {

        this.cloudCostRepository = cloudCostRepository;
        this.predictionService = predictionService;
        this.recommendationService = recommendationService;
        this.geminiService = geminiService;
    }

    @PostMapping
    public CloudCost saveCloudCost(@RequestBody CloudCost cloudCost) {

        return cloudCostRepository.save(cloudCost);
    }

    @GetMapping
    public List<CloudCost> getAllCloudCosts() {

        return cloudCostRepository.findAll();
    }

    @GetMapping("/predict")
    public double predictCost(@RequestParam double currentCost,
                              @RequestParam double growth) {

        return predictionService
                .predictNextMonthCost(currentCost, growth);
    }

    @GetMapping("/recommend")
    public String getRecommendation(@RequestParam double monthlyCost,
                                    @RequestParam double savings) {

        return recommendationService
                .generateRecommendation(monthlyCost, savings);
    }

    @GetMapping("/ai-recommend")
    public String getAiRecommendation(
            @RequestParam double monthlyCost,
            @RequestParam double savings) {

        return geminiService.generateRecommendation(
                monthlyCost,
                savings
        );
    }
}