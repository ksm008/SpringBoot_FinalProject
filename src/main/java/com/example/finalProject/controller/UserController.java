package com.example.finalProject.controller;

import com.example.finalProject.dto.UserForm;
import com.example.finalProject.entity.Article;
import com.example.finalProject.entity.User;
import com.example.finalProject.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/signup")
    public String signUp(Model model) {
        List<String> usernames = userRepository.findAll()
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toList());

        model.addAttribute("usernames", usernames);

        return "/articles/signup";
    }


    @PostMapping("/register")
    public String register(UserForm userForm) {
        userForm.logInfo();

        User user = userForm.toEntity();
        user.logInfo();

        User saved = userRepository.save(user);
        saved.logInfo();
        return "redirect:/main";
    }

    @GetMapping("/login")
    public String login() {
        return "/articles/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session, Model model) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            model.addAttribute("error", "존재하지 않는 아이디입니다.");
            return "/articles/login"; // 로그인 페이지로 다시 이동
        }

        if (!user.getPassword().equals(password)) {
            model.addAttribute("error", "비밀번호가 틀렸습니다.");
            return "/articles/login"; // 로그인 페이지로 다시 이동
        }
        session.setAttribute("user", user);

        // 로그인 성공 시 메인 페이지로 리다이렉트
        return "redirect:/main";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/main"; // 메인 페이지로 이동
    }

}
