package com.example.finalProject.dto;

import com.example.finalProject.entity.User;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Slf4j
public class UserForm {

    private Long id;
    private String username;
    private String password;

    public User toEntity() {
        return new User(id, username, password);
    }

    public void logInfo() {
        log.info("title: {}, content: {}", username, password);
    }
}
