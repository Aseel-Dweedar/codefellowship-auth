package com.example.codefellow.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Post {

    @Setter(value = AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String body;
    private String createdAt;

    @ManyToOne()
    AppUser user;

    public Post(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Post{" +
                "body='" + body + '\'' +
                '}';
    }
}
