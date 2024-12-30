package com.sparksInTheStep.webBoard.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String nickname;
    @Column(nullable = false)
    private UUID password;
    @Column(nullable = false)
    private boolean admin;

    private Member(String nickname, String password) {
        this.nickname = nickname;
        this.password = encodePassword(password);
        this.admin = false;
    }

    public static Member of(String nickname, String password) {
        return new Member(nickname, password);
    }

    public void granting() {
        this.admin = !this.admin;
    }

    public boolean passCheck(UUID password) {
        return this.password.equals(password);
    }

    public boolean adminCheck() {
        return this.admin;
    }

    private UUID encodePassword(String password) {
        long seed = password.hashCode();
        return new UUID(seed, ~seed);
    }
}
