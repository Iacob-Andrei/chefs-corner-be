package com.chefscorner.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "permission_request")
public class PermissionRequest {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String token;
    private LocalDateTime created;
    private LocalDateTime expires;
    private LocalDateTime confirmed;
    @Column(name = "id_recipe")
    private Integer idRecipe;
    private String owner;
    private String requester;

    public PermissionRequest(String token, LocalDateTime created, Integer idRecipe, String owner, String requester) {
        this.token = token;
        this.created = created;
        this.expires = created.plusMinutes(30);
        this.idRecipe = idRecipe;
        this.owner = owner;
        this.requester = requester;
    }
}
