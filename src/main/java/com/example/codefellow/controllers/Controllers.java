package com.example.codefellow.controllers;

import com.example.codefellow.Repos.AppUserRepo;
import com.example.codefellow.Repos.PostRepo;
import com.example.codefellow.models.AppUser;
import com.example.codefellow.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class Controllers {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AppUserRepo appUserRepo;

    @Autowired
    PostRepo postRepo;

    @GetMapping("/")
    String home(Model model, Principal p) {

        AppUser currentUser = appUserRepo.findByUsername(p.getName());
        List<AppUser> users = appUserRepo.findAll();
        users.remove(currentUser);
        for (AppUser user : currentUser.getFollowing()) {
            users.remove(user);
        }
        model.addAttribute("users" , users);
        return "home";
    }

    @GetMapping("/profile")
    String profile(@RequestParam Integer id , Model model) {
        AppUser user = appUserRepo.findById(id).get();
        model.addAttribute("user" , user);
        return "profile";
    }

    @GetMapping("/myprofile")
    String myProfile(Principal p, Model m) {
        AppUser user = appUserRepo.findByUsername(p.getName());
        m.addAttribute("me" , user);
        return "myprofile";
    }

    @GetMapping("/post")
    String post() {
        return "post";
    }

    @GetMapping("/feed")
    String allPosts(Model model){
        List<Post> posts = postRepo.findAll();
        model.addAttribute("posts" , posts);
        return "feed";
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

    @PostMapping("/addpost")
    RedirectView addPost(Principal p , Post post){

        AppUser user = appUserRepo.findByUsername(p.getName());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        post.setCreatedAt(dtf.format(now));

        post.setUser(user);

        postRepo.save(post);

        return new RedirectView("/");
    }

    @PostMapping("/follow")
    RedirectView followUser(Principal p , @RequestParam Integer id ){

        AppUser userToFollow = appUserRepo.findById(id).get();

        AppUser currentUser = appUserRepo.findByUsername(p.getName());

        userToFollow.getFollowers().add(currentUser);

        currentUser.getFollowing().add(userToFollow);

        appUserRepo.save(currentUser);

        appUserRepo.save(userToFollow);

        return new RedirectView("/");

    }

}
