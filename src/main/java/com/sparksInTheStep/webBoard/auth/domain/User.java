package com.sparksInTheStep.webBoard.auth.domain;

import com.sparksInTheStep.webBoard.content.domain.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String nickname;
    @Column(nullable = false)
    private UUID password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    private User(String nickname, String password){
        this.nickname = nickname;
        this.password = encodePassword(password);
    }

    public static User of(String nickname, String password){
        return new User(nickname, password);
    }

    private UUID encodePassword(String password){
        long seed = password.hashCode();
        return new UUID(seed, ~seed);
    }

    public boolean passCheck(UUID password){
        return this.password.equals(password);
    }

    public void addPost(Post post){
        this.posts.add(post);
    }
}
