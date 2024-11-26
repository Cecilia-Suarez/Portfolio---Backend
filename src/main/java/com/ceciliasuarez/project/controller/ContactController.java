package com.ceciliasuarez.project.controller;
import com.ceciliasuarez.project.model.ContactForm;
import com.ceciliasuarez.project.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173/")
public class ContactController {

    @Autowired
    private EmailService emailService;

    @Value("${EMAIL_USERNAME}")
    String email;

    @PostMapping("/contact")
    public ResponseEntity<String> handleFormSubmission(@RequestBody ContactForm form) {
        String subject = "Contact from the portfolio: " + form.getSubject();
        String body = "Name: " + form.getName() + "\nEmail: " + form.getEmail() + "\nMessage: " + form.getMessage();

        try {
            emailService.sendEmail(email, subject, body);
            return ResponseEntity.ok("Submitted form.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending email.");
        }
    }
}


