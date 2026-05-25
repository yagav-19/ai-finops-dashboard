package com.finops.backend.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(
            JavaMailSender mailSender) {

        this.mailSender = mailSender;
    }

    public void sendAlert(
            String to,
            String message) {

        SimpleMailMessage mail =
                new SimpleMailMessage();

        mail.setTo(to);

        mail.setSubject(
                "AWS Cost Alert 🚨"
        );

        mail.setText(message);

        mailSender.send(mail);
    }
}