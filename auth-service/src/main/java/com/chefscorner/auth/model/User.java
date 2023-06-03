package com.chefscorner.auth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credential")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String password;
    private boolean business;
    private boolean confirmed;
    private String data;

    public User(String name, String email, String password, boolean business) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.business = business;
        this.data = null;
        this.confirmed = false;
    }
}
