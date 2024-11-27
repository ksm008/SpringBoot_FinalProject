package com.example.finalProject.service;

import com.example.finalProject.dto.UserForm;
import com.example.finalProject.entity.User;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface UserService {
    List<User> index();
    User show(Long id);
    User register(UserForm userForm);
    User delete(Long id);
    User login(String username, String password);
    void logout(HttpSession session);
}
