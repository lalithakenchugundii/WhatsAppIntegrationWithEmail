/*package com.whatsapp.email.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.entity.WhatsAppChat;
import com.repository.WhatsAppChatRepository;
import com.service.EmailService;

import jakarta.mail.MessagingException;

@Component
public class ChatProcessor {

    private final WhatsAppChatRepository chatRepository;
    private final EmailService emailService;

    @Autowired
    public ChatProcessor(WhatsAppChatRepository chatRepository, EmailService emailService) {
        this.chatRepository = chatRepository;
        this.emailService = emailService;
    }

    public void processWhatsAppGroupChat(String chatContent) {
        // 1. Save chat message in DynamoDB
        WhatsAppChat chat = new WhatsAppChat();
        chat.setContent(chatContent);
        chatRepository.saveChatMessage(chat);

        // 2. Send chat to email distribution group
        try {
			emailService.sendWhatsAppContent("email@example.com", "WhatsApp Group Chat", chatContent);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}*/
