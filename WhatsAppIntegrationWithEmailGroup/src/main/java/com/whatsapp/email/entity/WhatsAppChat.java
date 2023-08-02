package com.whatsapp.email.entity;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class WhatsAppChat {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   

    private String id;
    private String content;

    public WhatsAppChat() {
    }

    public WhatsAppChat(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public static WhatsAppChat fromMap(Map<String, AttributeValue> attributeMap) {
    	WhatsAppChat chat = new WhatsAppChat();
        chat.setId(attributeMap.get("id").s());
        chat.setContent(attributeMap.get("content").s());
        return chat;
    }

    public Map<String, AttributeValue> toAttributeMap() {
        return Map.of(
                "id", AttributeValue.builder().s(id).build(),
                "content", AttributeValue.builder().s(content).build()
                // Add other relevant attributes
        );
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
