package com.chefscorner.user.repository;

import com.chefscorner.user.model.PermissionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRequestRepository extends JpaRepository<PermissionRequest, Integer> {

    Optional<PermissionRequest> findPermissionRequestByToken(String token);
}
