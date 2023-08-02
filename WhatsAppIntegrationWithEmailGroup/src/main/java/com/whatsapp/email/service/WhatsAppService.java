package com.whatsapp.email.service;

//import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.whatsapp.email.processor.BackupFileProcessor;
import com.whatsapp.email.repository.WhatsAppChatRepository;

//import jakarta.mail.MessagingException;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;

@Service
public class WhatsAppService {

    private final DynamoDbClient dynamoDbClient;
    private final EmailService emailService;
    private final BackupFileProcessor backupFileProcessor;
    private final WhatsAppChatRepository chatRepository;

    @Autowired
    public WhatsAppService(DynamoDbClient dynamoDbClient,WhatsAppChatRepository chatRepository, EmailService emailService, BackupFileProcessor backupFileProcessor) {
        this.dynamoDbClient = dynamoDbClient;
        this.chatRepository = chatRepository;
        this.emailService = emailService;
        this.backupFileProcessor = backupFileProcessor;
    }

    public ResponseEntity<String> processWhatsAppBackupFile(MultipartFile backupFile) {
        try {
            // Save the backup file to a temporary location
            Path tempFile = Files.createTempFile("WhatsApp Chat with Info services HYD lunch", ".txt");
            Files.copy(((File) backupFile).toPath(), tempFile, StandardCopyOption.REPLACE_EXISTING);

            // Process the backup file content
            String backupContent = backupFileProcessor.processBackupFile(tempFile);

           /* // Insert or update the extracted data into DynamoDB
            PutItemRequest request = PutItemRequest.builder()
                    .tableName("WhatsAppChatContent")
                    .item(Map.of(
                            "id", AttributeValue.builder().s("uniqueId").build(),
                            "content", AttributeValue.builder().s(backupContent).build()
                            // Add other relevant attributes
                    ))
                    .build();
            PutItemResponse response = dynamoDbClient.putItem(request);

            if (response.sdkHttpResponse().isSuccessful()) {
                // Send WhatsApp content to email distribution group
                try {
					emailService.sendWhatsAppContent("lallikenchu@gmail.com", "WhatsApp Backup", backupContent);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

                return ResponseEntity.ok("WhatsApp chat content processed and stored in DynamoDB.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process WhatsApp chat content.");
            }*/
            return  ResponseEntity.ok("WhatsApp chat content processed");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing WhatsApp backup file.");
          }
		
    }

    public ResponseEntity<String> sendWhatsAppContentByEmail(String content) {
        try {
            // Send WhatsApp content to email distribution group
            EmailService.sendWhatsAppContent("lallikenchu@gmail.com", "WhatsappGroupChatContent", content);
            return ResponseEntity.ok("WhatsApp content sent to email distribution group.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send WhatsApp content by email.");
          }
    }

    public ResponseEntity<String> saveWhatsAppContentToDynamoDB(String content) {
        try {
            // Insert or update the WhatsApp content in DynamoDB
            PutItemRequest request = PutItemRequest.builder()
                    .tableName("WhatsAppChatContent")
                    .item(Map.of(
                            "id", AttributeValue.builder().s("uniqueId").build(),
                            "content", AttributeValue.builder().s(content).build()
                            // Add other relevant attributes
                    ))
                    .build();
            PutItemResponse response = dynamoDbClient.putItem(request);

            if (response.sdkHttpResponse().isSuccessful()) {
                return ResponseEntity.ok("WhatsApp chat content saved in DynamoDB.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save WhatsApp chat content in DynamoDB.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving WhatsApp chat content in DynamoDB.");
          }
    }

    /*public ResponseEntity<byte[]> downloadBackupFile(String filename) {
        try {
            // Load the backup file
            File backupFile = new File("/path/to/backups/" + filename);
            byte[] fileContent = FileUtils.readFileToByteArray(backupFile);

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                    .body(fileContent);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
          }
    }*/
}
