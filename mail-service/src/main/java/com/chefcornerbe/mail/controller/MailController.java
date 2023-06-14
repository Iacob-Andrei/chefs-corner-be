package com.chefcornerbe.mail.controller;


import com.chefcornerbe.mail.dto.ConfirmationBodyDto;
import com.chefcornerbe.mail.dto.RequestAccessBodyDto;
import com.chefcornerbe.mail.service.MailService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/mail")
public class MailController {

    private final MailService mailService;

    @Operation(summary = "Send mail for confirmation account.")
    @PostMapping("/confirm")
    public ResponseEntity<?> sendMailConfirmationAccount(@RequestBody ConfirmationBodyDto body){
        mailService.sendConfirmationMail(body);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "Send mail for requesting access.")
    @PostMapping("/request")
    public ResponseEntity<?> sendMailRequestAccess(@RequestBody RequestAccessBodyDto body){
        mailService.sendRequestPermission(body);
        return ResponseEntity.ok().body("ok");
    }
}
