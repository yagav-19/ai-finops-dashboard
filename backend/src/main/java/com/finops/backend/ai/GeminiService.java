package com.finops.backend.ai;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    public String generateRecommendation(
            double monthlyCost,
            double savings) {

        try {

            Client client =
                    Client.builder()
                            .apiKey(apiKey)
                            .build();

            String prompt =
                    "AWS monthly cost is "
                            + monthlyCost
                            + " INR and possible savings is "
                            + savings
                            + " INR. Give cloud cost optimization advice.";

            GenerateContentResponse response =
                    client.models.generateContent(
                            "gemini-2.5-flash",
                            prompt,
                            null
                    );

            return response.text();

        } catch (Exception e) {

            String message =
                    e.getMessage();

            if (message != null &&
                    message.contains("429")) {

                return """
Gemini free-tier limit reached.

Please wait 1 minute and try again.

Cloud cost optimization tips:
• Right-size EC2 instances
• Enable auto scaling
• Delete unused storage
• Use Reserved Instances
• Monitor CloudWatch regularly
""";
            }

            return "Gemini error: "
                    + message;
        }
    }
}