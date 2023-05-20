package com.chefscorner.user.service;

import com.chefscorner.user.exception.RequestNotFoundException;
import com.chefscorner.user.model.PermissionRequest;
import com.chefscorner.user.model.User;
import com.chefscorner.user.repository.PermissionRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermissionRequestService {

    private final PermissionRequestRepository permissionRequestRepository;
    private final MailService mailService;

    public void savePermissionRequest(User owner, User requester, String nameRecipe, Integer idRecipe){
        String token = UUID.randomUUID().toString();

        PermissionRequest permissionRequest = new PermissionRequest(
                token,
                LocalDateTime.now(),
                idRecipe,
                owner.getEmail(),
                requester.getEmail()
        );
        permissionRequestRepository.save(permissionRequest);
        
        mailService.sendRequestPermission(token, owner, requester, nameRecipe);
    }

    public PermissionRequest getToken(String token){
        Optional<PermissionRequest> optional = permissionRequestRepository.findPermissionRequestByToken(token);
        if(optional.isEmpty()) throw new RequestNotFoundException();

        return optional.get();
    }

    public void deleteToken(String token) {
        permissionRequestRepository.deletePermissionRequestByToken(token);
    }
}
