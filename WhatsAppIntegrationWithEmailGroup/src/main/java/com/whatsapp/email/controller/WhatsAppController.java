package com.whatsapp.email.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.whatsapp.email.service.WhatsAppService;

/*import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

import java.io.IOException;
import java.util.Map;*/

@RestController
@RequestMapping("/whatsapp")

public class WhatsAppController {

    private final WhatsAppService whatsappService;

    @Autowired
    public WhatsAppController(WhatsAppService whatsappService) {
        this.whatsappService = whatsappService;
    }

    @PostMapping("/backup")
    public ResponseEntity<String> processWhatsAppBackupFile(@RequestParam("WhatsApp Chat with Info services HYD lunch.txt") MultipartFile backupFile) {
        whatsappService.processWhatsAppBackupFile(backupFile);
		return ResponseEntity.ok("WhatsApp backup file processed successfully.");
    }

    @PostMapping("/email")
    public ResponseEntity<String> sendWhatsAppContentByEmail(@RequestParam("content") String content) {
        try {
            whatsappService.sendWhatsAppContentByEmail(content);
            return ResponseEntity.ok("WhatsApp content sent by email successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send WhatsApp content by email.");
        }
    }

    @PostMapping("/dynamodb")
    public ResponseEntity<String> saveWhatsAppContentToDynamoDB(@RequestParam("content") String content) {
        try {
            whatsappService.saveWhatsAppContentToDynamoDB(content);
            return ResponseEntity.ok("WhatsApp content saved in DynamoDB successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save WhatsApp content in DynamoDB.");
        }
    }
}

   /* @GetMapping("/backup/{filename}")
    public ResponseEntity<byte[]> downloadBackupFile(@PathVariable String filename) {
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

