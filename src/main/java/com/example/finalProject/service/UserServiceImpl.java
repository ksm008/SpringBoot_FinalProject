package com.example.finalProject.service;

import com.example.finalProject.dto.UserForm;
import com.example.finalProject.entity.User;
import com.example.finalProject.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> index() {
        return userRepository.findAll();
    }

    @Override
    public User show(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User register(UserForm userForm) {
        // UserForm을 User로 변환
        User user = userForm.toEntity();

        // 중복 아이디 검사
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        User registered = userRepository.save(user);

        // 데이터베이스에 저장
        return registered;
    }

    @Override
    public User delete(Long id) {
        
        User target = userRepository.findById(id).orElse(null);
        
        if (target == null) {
            log.info("해당 유저가 존재하지 않습니다. User: {}", target.toString());
            return null;

        }
        
        userRepository.delete(target);
        return target;
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

    @Override
    public void logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }
}
