package com.whatsapp.email.processor;

import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
public class BackupFileProcessor {

    public String processBackupFile(Path filePath) {
        try {
            // Read the backup file content
            String backupContent = Files.readString(filePath, StandardCharsets.UTF_8);

            // Extract the necessary information from the backup content
            List<String> extractedData = extractData(backupContent);

            // Convert the extracted data to a formatted string
            StringBuilder formattedData = new StringBuilder();
            for (String data : extractedData) {
                formattedData.append(data).append("\n");
            }

            return formattedData.toString();
        } catch (IOException e) {
            throw new RuntimeException("Error processing WhatsApp backup file", e);
        }
    }

    private List<String> extractData(String backupContent) {
        
        // Example: Extracting chat messages
        List<String> extractedData = new ArrayList<>();
        String[] lines = backupContent.split("\n");
        for (String line : lines) {
            if (line.startsWith("[")) {
                extractedData.add(line);
            }
        }

        return extractedData;
    }
}
