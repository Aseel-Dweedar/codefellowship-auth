package com.example.codefellow.controllers;

import com.example.codefellow.Repos.AppUserRepo;
import com.example.codefellow.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class Controllers {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AppUserRepo appUserRepo;

    @GetMapping("/")
    String home() {
        return "home";
    }

    @GetMapping("/post")
    String post() {
        return "post";
    }

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/signup")
    String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    RedirectView addUser(@RequestParam String username, @RequestParam String password) {
        AppUser user = new AppUser( username , encoder.encode(password)  );
        appUserRepo.save(user);
        return new RedirectView("login") ;
    }

}
