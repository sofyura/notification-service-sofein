package com.example.notificationserviceSofein.listener;

import com.example.notificationserviceSofein.dto.UserEventDto;
import com.example.notificationserviceSofein.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {

    private final EmailService emailService;

    public UserEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void handleUserEvent(UserEventDto event) {
        try {
            if ("CREATED".equals(event.getOperation())) {
                emailService.sendAccountCreatedEmail(event.getEmail());
                System.out.println("Письмо о создании отправлено на " + event.getEmail());
            } else if ("DELETED".equals(event.getOperation())) {
                emailService.sendAccountDeletedEmail(event.getEmail());
                System.out.println("Письмо об удалении отправлено на " + event.getEmail());
            }
        } catch (Exception e) {
            System.err.println("Ошибка отправки email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
