package com.whatsapp.email.configuration;

import org.springframework.beans.factory.annotation.Value;

//import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration

public class DynamoDBConfiguration {
	
	@Value("${AKIA422MIMOO7DBZN5QD}")
    private String awsAccessKey;

    @Value("${iy75VjiNCqfjO0d43TIO1kDHJqSApvZ7XgUtihDr}")
    private String awsSecretKey;

    @Value("${AP_SOUTH_1}")
    private String awsRegion;
    
    @Value("${dynamodb.ap-south-1.amazonaws.com}")
    private String endpoint;
    
    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.AP_SOUTH_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }
}
