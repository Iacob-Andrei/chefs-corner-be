package com.chefscorner.user.service;

import com.chefscorner.user.dto.PermissionDto;
import com.chefscorner.user.dto.RequestPermissionDto;
import com.chefscorner.user.dto.ResponseDto;
import com.chefscorner.user.exception.EmailNotFoundException;
import com.chefscorner.user.exception.RequestNotFoundException;
import com.chefscorner.user.model.Permission;
import com.chefscorner.user.model.PermissionRequest;
import com.chefscorner.user.model.User;
import com.chefscorner.user.repository.PermissionRepository;
import com.chefscorner.user.repository.UserRepository;
import com.chefscorner.user.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository repository;
    private final UserRepository userRepository;
    private final WebService webService;
    private final PermissionRequestService permissionRequestService;

    public void addUserPermission(PermissionDto permissionDto) {
        Optional<Permission> optional = repository.findPermissionByEmailAndIdRecipe(permissionDto.getEmail(), permissionDto.getIdRecipe());
        if(optional.isPresent()) return;

        Permission permission = new Permission(permissionDto.getIdRecipe(), permissionDto.getEmail());
        repository.save(permission);
    }

    public List<String> getUsersWithPermission(Integer idRecipe) {
        return repository.findPermissionsByIdRecipe(idRecipe).stream().map(Permission::getEmail).collect(Collectors.toList());
    }

    @Transactional
    public void removeUserPermission(PermissionDto permissionDto) {
        repository.deletePermissionByIdRecipeAndEmail(permissionDto.getIdRecipe(), permissionDto.getEmail());
    }

    public List<Integer> getRecipesWithPermission(String email) {
        return repository.findPermissionsByEmail(email).stream().map(Permission::getIdRecipe).collect(Collectors.toList());
    }

    @Transactional
    public ResponseDto askPermissionForRecipe(String bearerToken, Integer idRecipe) throws JSONException {
        String requesterMail = JwtUtil.getSubjectFromToken(bearerToken);
        Optional<User> requesterOptional = userRepository.findByEmail(requesterMail);

        if(requesterOptional.isEmpty()) throw new EmailNotFoundException(requesterMail);

        List<String> data = webService.getRecipeOwnerEmail(idRecipe);
        String ownerEmail = data.get(0);
        String nameRecipe = data.get(1);

        Optional<User> ownerOptional = userRepository.findByEmail(ownerEmail);

        if(ownerOptional.isEmpty()) throw new EmailNotFoundException(ownerEmail);

        User owner = ownerOptional.get();
        User requester = requesterOptional.get();

        permissionRequestService.savePermissionRequest(owner, requester, nameRecipe, idRecipe);
        return ResponseDto.builder().data(true).build();
    }

    @Transactional
    public ResponseDto confirmPermissionForRecipe(String bearerToken, String token) throws JSONException {
        PermissionRequest request = permissionRequestService.getToken(token);
        String requesterMail = JwtUtil.getSubjectFromToken(bearerToken);

        if(!request.getOwner().equals(requesterMail) || request.getConfirmed() != null){
            throw new RequestNotFoundException();
        }

        if(request.getExpires().isBefore(LocalDateTime.now())){
            permissionRequestService.deleteToken(token);
            throw new RequestNotFoundException();
        }

        Permission permission = new Permission(request.getIdRecipe(), request.getRequester());
        repository.save(permission);

        permissionRequestService.deleteToken(token);

        return ResponseDto.builder().data(true).build();
    }

    @Transactional
    public RequestPermissionDto getRequestPermissionInfo(String bearerToken, String token) throws JSONException {
        PermissionRequest request = permissionRequestService.getToken(token);
        String requesterMail = JwtUtil.getSubjectFromToken(bearerToken);

        if(!request.getOwner().equals(requesterMail) || request.getConfirmed() != null){
            throw new RequestNotFoundException();
        }

        if(request.getExpires().isBefore(LocalDateTime.now())){
            permissionRequestService.deleteToken(token);
            throw new RequestNotFoundException();
        }

        Optional<User> optionalRequester = userRepository.findByEmail(request.getRequester());
        if(optionalRequester.isEmpty()) throw new EmailNotFoundException(requesterMail);
        User requester = optionalRequester.get();

        List<String> data = webService.getRecipeOwnerEmail(request.getIdRecipe());
        String nameRecipe = data.get(1);

        return RequestPermissionDto.builder()
                .requesterEmail(requester.getEmail())
                .requesterName(requester.getName())
                .recipeName(nameRecipe)
                .build();
    }

    @Transactional
    public ResponseDto deletePermissionRequestForRecipe(String bearerToken, String token) throws JSONException {
        PermissionRequest request = permissionRequestService.getToken(token);
        String requesterMail = JwtUtil.getSubjectFromToken(bearerToken);

        if(!request.getOwner().equals(requesterMail) || request.getConfirmed() != null){
            throw new RequestNotFoundException();
        }
        permissionRequestService.deleteToken(token);

        return ResponseDto.builder().data(true).build();
    }
}
