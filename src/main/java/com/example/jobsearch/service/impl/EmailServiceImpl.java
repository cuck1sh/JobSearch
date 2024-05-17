package com.example.jobsearch.service.impl;

import com.example.jobsearch.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String EMAIL_FROM;

    @Override
    public void sendEmail(String toEmail, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(EMAIL_FROM, "JobSearch Support");
        helper.setTo(toEmail);

        String subject = "Here's the link to reset your password";
        String content = "<p>Hello, </p>\n" +
                "<p>You have requested the link to reset you password</p>\n" +
                "<p>Click the link below to change your password</p>\n" +
                "<p><a href=\"" + link + "\">Change my password</a></p>\n" +
                "<br>\n" +
                "<p>Ignore this email if you do remember password, or you have not made the request.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);

    }
}
