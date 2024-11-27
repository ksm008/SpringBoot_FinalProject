package com.example.finalProject.service;

import com.example.finalProject.dto.UserForm;
import com.example.finalProject.entity.User;
import com.example.finalProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User register(UserForm userForm) {
        // UserForm을 User로 변환
        User user = userForm.toEntity();

        // 중복 아이디 검사
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 데이터베이스에 저장
        return userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        // 사용자 조회
        User user = userRepository.findByUsername(username);

        // 아이디 확인
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 아이디입니다.");
        }

        // 비밀번호 확인
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        return user;
    }

}
