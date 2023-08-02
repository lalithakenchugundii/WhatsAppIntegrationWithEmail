package com.whatsapp.email.repository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;
import com.whatsapp.email.entity.WhatsAppChat;
import com.whatsapp.email.service.EmailService;

import jakarta.mail.MessagingException;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;

@Repository
public class WhatsAppChatRepository {

    private final DynamoDbClient dynamoDbClient;

    @Autowired
    public WhatsAppChatRepository(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public void saveChatMessage(WhatsAppChat chat) {
        PutItemRequest request = PutItemRequest.builder()
                .tableName("WhatsAppGroupChatContent")
                .item(chat.toAttributeMap())
                .build();

        dynamoDbClient.putItem(request);
    }

    public void updateChatMessage(WhatsAppChat chat) {
        UpdateItemRequest request = UpdateItemRequest.builder()
                .tableName("WhatsAppGroupChatContent")
                .key(Map.of("id", AttributeValue.builder().s(chat.getId()).build()))
                .attributeUpdates(chatToUpdateAttributes(chat))
                .build();

        dynamoDbClient.updateItem(request);
    }

   /* public void sendChatToEmailDistribution(String email, WhatsAppChat chat) {
        // Send chat content to email distribution group
        try {
			EmailService.sendWhatsAppContent(email, "WhatsApp Chat", chat.getContent());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }*/

    private Map<String, AttributeValueUpdate> chatToUpdateAttributes(WhatsAppChat chat) {
        Map<String, AttributeValueUpdate> attributeUpdates = new HashMap<>();
        attributeUpdates.put("content", AttributeValueUpdate.builder().value(AttributeValue.builder().s(chat.getContent()).build()).build());
        // Add other attributes that need to be updated
        return attributeUpdates;
    }
}
