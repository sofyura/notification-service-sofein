package com.example.notificationserviceSofein.controller;

import com.example.notificationserviceSofein.service.EmailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/created")
    public String sendCreated(@RequestParam String email) {
        try {
            emailService.sendAccountCreatedEmail(email);
            return "Письмо о создании отправлено на " + email;
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }

    @PostMapping("/deleted")
    public String sendDeleted(@RequestParam String email) {
        try {
            emailService.sendAccountDeletedEmail(email);
            return "Письмо об удалении отправлено на " + email;
        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }
}
