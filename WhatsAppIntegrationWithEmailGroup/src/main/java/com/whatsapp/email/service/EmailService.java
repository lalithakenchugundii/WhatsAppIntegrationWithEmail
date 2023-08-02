package com.whatsapp.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    private static JavaMailSender mailSender = null;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public static void sendWhatsAppContent(String to, String subject, String content) throws jakarta.mail.MessagingException {
        jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(message, true);
		helper.setTo("lallikenchu@gmail.com");
		helper.setSubject("whatsappGroupChatContent");
		helper.setText(content, true);

		mailSender.send(message);
    }
}

           