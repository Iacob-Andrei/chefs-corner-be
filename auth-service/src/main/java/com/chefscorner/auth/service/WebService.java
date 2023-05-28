package com.chefscorner.auth.service;

import com.chefscorner.auth.dto.ConfirmationBodyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;



@Component
@RequiredArgsConstructor
public class WebService {

    private final WebClient.Builder webClientBuilder;

    public void sendMailConfirmAccount(ConfirmationBodyDto mailBody){
        webClientBuilder.build()
                .post()
                .uri("http://mail-service/api/mail/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(mailBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
