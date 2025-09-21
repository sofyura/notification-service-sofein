package com.example.notificationserviceSofein.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String websiteUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        this.websiteUrl = "https://yoursite.com"; // можно вынести в properties
    }

    public void sendAccountCreatedEmail(String email) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject("Добро пожаловать!");
        helper.setText("""
            <h3>Здравствуйте!</h3>
            <p>Ваш аккаунт на сайте <b>%s</b> был успешно создан.</p>
            <p>Спасибо за регистрацию!</p>
            """.formatted(websiteUrl), true);

        mailSender.send(message);
    }

    public void sendAccountDeletedEmail(String email) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject("Аккаунт удалён");
        helper.setText("""
            <h3>Здравствуйте!</h3>
            <p>Ваш аккаунт был удалён.</p>
            <p>Если это были не вы — свяжитесь с нами.</p>
            """, true);

        mailSender.send(message);
    }
}
