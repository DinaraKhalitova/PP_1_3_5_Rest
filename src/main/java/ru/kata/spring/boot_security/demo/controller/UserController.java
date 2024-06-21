package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;

@Controller
public class UserController {
    private final UserServiceImp userServiceImp;

    @Autowired
    public UserController( UserServiceImp userServiceImp ) {
        this.userServiceImp=userServiceImp;
    }

    @GetMapping("/user")
    public String showUserInfo(Model model, Principal principal) {
        User user = userServiceImp.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

}
