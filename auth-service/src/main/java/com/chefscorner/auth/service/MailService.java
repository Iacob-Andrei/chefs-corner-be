package com.chefscorner.auth.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class MailService {

    private final TemplateEngine templateEngine;
    private final JavaMailSender mailSender;
    @Value("${redirect.confirm}")
    private String redirect;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendConfirmationMail(String to, String token, String name)  {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("link", redirect+token);
        String process = templateEngine.process("confirm", context);

       try {
           MimeMessage mimeMessage = mailSender.createMimeMessage();
           MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

           helper.setText(process, true);
           helper.setTo(to);
           helper.setSubject("Confirm your account");
           helper.setFrom(sender);

           mailSender.send(mimeMessage);

       } catch (Exception e) {
           System.err.println(e.getMessage());
       }
    }
}
