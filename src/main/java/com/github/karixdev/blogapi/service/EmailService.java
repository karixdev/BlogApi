package com.github.karixdev.blogapi.service;

import com.github.karixdev.blogapi.entity.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender sender;

    @Async
    public void sendEmailConfirmation(ConfirmationToken token) {
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            helper.setText(getEmailConfirmationTemplate(token), true);
            helper.setTo(token.getUser().getEmail());
            helper.setSubject("Confirm your email");
            helper.setFrom("no-reply@blogapi.com");

            sender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Failed to send an email confirmation");
        }

    }

    private String getEmailConfirmationTemplate(ConfirmationToken token) {
        String template = """
                <div>
                <p>Hello %s</p>
                <p>Here's email confirmation <a href="http://localhost:8080/api/auth/confirm?token=%s">link</a></p>
                </div>
                """;

        return String.format(template, token.getUser().getFirstName(), token.getToken());
    }

}
