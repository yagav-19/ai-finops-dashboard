package com.finops.backend.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;

import software.amazon.awssdk.services.costexplorer.CostExplorerClient;
import software.amazon.awssdk.services.costexplorer.model.*;

@Service
public class AwsCostService {

    @Value("${aws.access.key}")
    private String accessKey;

    @Value("${aws.secret.key}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    public String getMonthlyAwsCost() {

        AwsBasicCredentials credentials =
                AwsBasicCredentials.create(
                        accessKey,
                        secretKey
                );

        CostExplorerClient client =
                CostExplorerClient.builder()
                        .region(Region.of(region))
                        .credentialsProvider(
                                StaticCredentialsProvider.create(credentials)
                        )
                        .build();

        GetCostAndUsageRequest request =
                GetCostAndUsageRequest.builder()
                        .timePeriod(
                                DateInterval.builder()
                                        .start("2026-05-01")
                                        .end("2026-05-31")
                                        .build()
                        )
                        .granularity(Granularity.MONTHLY)
                        .metrics("UnblendedCost")
                        .build();

        GetCostAndUsageResponse response =
                client.getCostAndUsage(request);

        return response.resultsByTime()
                .get(0)
                .total()
                .get("UnblendedCost")
                .amount();
    }
}