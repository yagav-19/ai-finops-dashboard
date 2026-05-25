package com.finops.backend.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

@Service
public class PdfReportService {

    public String generateReport(
            String awsCost,
            String recommendation) {

        try {

            Document document =
                    new Document();

            String fileName =
                    "cloud-report.pdf";

            PdfWriter.getInstance(
                    document,
                    new FileOutputStream(fileName)
            );

            document.open();

            document.add(
                    new Paragraph(
                            "GenAI FinOps Cloud Report"
                    )
            );

            document.add(
                    new Paragraph(
                            "AWS Monthly Cost: $" + awsCost
                    )
            );

            document.add(
                    new Paragraph(
                            "AI Recommendation:"
                    )
            );

            document.add(
                    new Paragraph(
                            recommendation
                    )
            );

            document.close();

            return "PDF generated successfully";

        } catch (Exception e) {

            return e.getMessage();
        }
    }
}