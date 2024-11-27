package com.example.finalProject.controller;

import com.example.finalProject.dto.UserForm;
import com.example.finalProject.entity.User;
import com.example.finalProject.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/signup")
    public String signUp() {
        return "/articles/signup";
    }

    @PostMapping("/register")
    public String register(UserForm userForm, Model model) {
        try {
            userService.register(userForm);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "/articles/signup";
        }
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
        try {
            User user = userService.login(username, password);
            session.setAttribute("user", user);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "/articles/login";
        }
        return "redirect:/main";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        userService.logout(session);
        return "redirect:/main";
    }

}
