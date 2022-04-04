package com.example.codefellow.Repos;

import com.example.codefellow.models.AppUser;
import com.example.codefellow.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {
    List<Post> findByUser(AppUser user);
}
