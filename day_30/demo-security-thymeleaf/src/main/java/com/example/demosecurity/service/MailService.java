package com.example.demosecurity.service;


import com.example.demosecurity.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender sender;
    private final SpringTemplateEngine templateEngine;

    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        // Send Message!
        sender.send(message);
    }

    public void sendMail2(User user, String subject, String link) {
        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(user.getEmail());
            helper.setSubject(subject);

            final Context context = new Context();
            context.setVariable("user", user);
            context.setVariable("link", link);

            final String htmlContent = templateEngine.process("mail-template/confirm-registration", context);
            message.setContent(htmlContent, "text/html");

            // Send Message!
            sender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
