package com.example.finalProject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "users")
@ToString
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String username;
    private String password;
    public void logInfo() {
        log.info("id: {}, username: {}, password: {} ",
                id, username, password);
    }
}
