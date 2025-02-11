package com.ceciliasuarez.project.service;

import jakarta.mail.internet.MimeMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    @Value("${EMAIL_USERNAME}")
    String email;

    private static final Logger logger = Logger.getLogger(EmailService.class);

    public void sendEmail(String to, String subject, String body) throws MailException {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(email);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);
            emailSender.send(message);
            logger.info("Email sent");
        } catch (Exception e) {
            logger.info("Error sending email");
            e.printStackTrace();
        }
    }
}


