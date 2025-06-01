package com.tap.com.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tap.com.model.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Integer userId;
    private String username;
    private String email;
    private String phone;
    private String password;
    private Integer pin;
    private String role;
    private LocalDateTime createdDate;
    private LocalDateTime lastLoginDate;

    public UserDTO(UserEntity user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.role = user.getRole();
        this.password = user.getPassword();
        this.pin = user.getPin();
        this.createdDate = user.getCreatedDate();
        this.lastLoginDate = user.getLastLoginDate();
    }
}
