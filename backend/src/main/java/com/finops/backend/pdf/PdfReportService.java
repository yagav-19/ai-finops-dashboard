package com.finops.backend.pdf;

import org.springframework.stereotype.Service;

@Service
public class PdfReportService {

    public String generateReport(
            String awsCost,
            String recommendation) {

        return
                "PDF Report Generated\n\n"
                        + "AWS Cost: "
                        + awsCost
                        + "\n\nRecommendation:\n"
                        + recommendation;
    }
}