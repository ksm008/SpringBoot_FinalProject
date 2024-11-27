package com.example.finalProject.service;

import com.example.finalProject.dto.UserForm;
import com.example.finalProject.entity.User;

public interface UserService {
    User register(UserForm userForm); // 회원가입
    User login(String username, String password);
}
