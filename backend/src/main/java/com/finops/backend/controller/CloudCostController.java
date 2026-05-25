package com.finops.backend.controller;

import com.finops.backend.ai.GeminiService;
import com.finops.backend.aws.AwsCostService;
import com.finops.backend.email.EmailService;
import com.finops.backend.pdf.PdfReportService;
import com.finops.backend.repository.CloudCostRepository;
import com.finops.backend.service.PredictionService;
import com.finops.backend.service.RecommendationService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cloud-costs")
@CrossOrigin(origins = "*")
public class CloudCostController {

    private final CloudCostRepository cloudCostRepository;

    private final PredictionService predictionService;

    private final RecommendationService recommendationService;

    private final GeminiService geminiService;

    private final AwsCostService awsCostService;

    private final PdfReportService pdfReportService;

    private final EmailService emailService;

    public CloudCostController(
            CloudCostRepository cloudCostRepository,
            PredictionService predictionService,
            RecommendationService recommendationService,
            GeminiService geminiService,
            AwsCostService awsCostService,
            PdfReportService pdfReportService,
            EmailService emailService) {

        this.cloudCostRepository =
                cloudCostRepository;

        this.predictionService =
                predictionService;

        this.recommendationService =
                recommendationService;

        this.geminiService =
                geminiService;

        this.awsCostService =
                awsCostService;

        this.pdfReportService =
                pdfReportService;

        this.emailService =
                emailService;
    }

    @GetMapping("/aws-cost")
    public String getAwsCost() {

        return awsCostService
                .getMonthlyAwsCost();
    }

    @GetMapping("/chat")
    public String askAi(
            @RequestParam String question) {

        try {

            return geminiService
                    .generateRecommendation(
                            50000,
                            12000
                    )
                    + "\n\nQuestion: "
                    + question;

        } catch (Exception e) {

            return "Gemini error: "
                    + e.getMessage();
        }
    }

    @GetMapping("/report")
    public String generatePdfReport() {

        String awsCost =
                awsCostService
                        .getMonthlyAwsCost();

        String recommendation =
                geminiService
                        .generateRecommendation(
                                50000,
                                12000
                        );

        return pdfReportService
                .generateReport(
                        awsCost,
                        recommendation
                );
    }

    @GetMapping("/trend")
    public double[] getTrend() {

        return new double[]{
                4100,
                5300,
                4700,
                6200,
                5800,
                6900
        };
    }

    @GetMapping("/alert")
    public String getAlert() {

        double cost =
                Double.parseDouble(
                        awsCostService
                                .getMonthlyAwsCost()
                );

        if (cost >200) {

            emailService.sendAlert(
                    "yagavsakthivel@gmail.com",
                    "High AWS spending detected this month."
            );

            return "⚠️ High AWS spending detected";
        }

        return "✅ AWS spending normal";
    }
}