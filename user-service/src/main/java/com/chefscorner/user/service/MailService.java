package com.chefscorner.user.service;

import com.chefscorner.user.model.User;
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

    public void sendRequestPermission(String token, User owner, User requester, String recipeName){
        Context context = new Context();
        context.setVariable("link", redirect+token);
        context.setVariable("nameTo", owner.getName());
        context.setVariable("nameRequester", requester.getName());
        context.setVariable("emailRequester", requester.getEmail());
        context.setVariable("recipeName", recipeName);
        String process = templateEngine.process("request-permission", context);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setText(process, true);
            helper.setTo(owner.getEmail());
            helper.setSubject("Request permission");
            helper.setFrom(sender);

            mailSender.send(mimeMessage);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
