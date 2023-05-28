package com.chefcornerbe.mail.service;

import com.chefcornerbe.mail.dto.ConfirmationBodyDto;
import com.chefcornerbe.mail.dto.RequestAccessBodyDto;
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

    @Value("${redirect.confirm.register}")
    private String redirectConfirmRegister;

    @Value("${redirect.confirm.permission}")
    private String redirectConfirmPermission;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendRequestPermission(RequestAccessBodyDto body){
        Context context = new Context();
        context.setVariable("link", redirectConfirmPermission+body.getToken());
        context.setVariable("nameTo", body.getOwnerName());
        context.setVariable("nameRequester", body.getRequesterName());
        context.setVariable("emailRequester", body.getRequesterMail());
        context.setVariable("recipeName", body.getRecipeName());
        String process = templateEngine.process("request-permission", context);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setText(process, true);
            helper.setTo(body.getOwnerMail());
            helper.setSubject("Request permission");
            helper.setFrom(sender);

            mailSender.send(mimeMessage);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void sendConfirmationMail(ConfirmationBodyDto body)  {
        Context context = new Context();
        context.setVariable("name", body.getName());
        context.setVariable("link", redirectConfirmRegister+body.getToken());
        String process = templateEngine.process("confirm", context);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setText(process, true);
            helper.setTo(body.getTo());
            helper.setSubject("Confirm your account");
            helper.setFrom(sender);

            mailSender.send(mimeMessage);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
