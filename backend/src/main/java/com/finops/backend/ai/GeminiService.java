package com.finops.backend.ai;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    public String generateRecommendation(double monthlyCost,
                                         double savingsOpportunity) {

        Client client = Client.builder()
                .apiKey(apiKey)
                .build();

        String prompt =
                "You are a cloud FinOps expert. " +
                        "Analyze this cloud infrastructure spending:\n" +
                        "Monthly Cost: ₹" + monthlyCost + "\n" +
                        "Savings Opportunity: ₹" + savingsOpportunity + "\n\n" +
                        "Give professional cloud optimization recommendations.";

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.5-flash",
                        prompt,
                        null
                );

        return response.text();
    }
}