package com.sparksInTheStep.webBoard.member.domain;

import com.sparksInTheStep.webBoard.member.application.dto.MemberCommand;
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
    private String email;
    @Column(unique = true)
    private String nickname;
    @Column(nullable = false)
    private UUID password;
    @Column(nullable = false)
    private boolean admin;
    @Column(nullable = false)
    private boolean employed;
    @Column
    private String gitLink;
    @Column
    private String resumeLink;

    private Member(
            String email,
            String nickname,
            String password,
            Boolean employed,
            String gitLink,
            String resumeLink
    ) {
        this.email = email;
        this.nickname = nickname;
        this.password = encodePassword(password);
        this.employed = employed;
        this.admin = false;
        this.gitLink = gitLink;
        this.resumeLink = resumeLink;
    }

    public static Member of(MemberCommand memberCommand) {
        return new Member(
                memberCommand.email(),
                memberCommand.nickname(),
                memberCommand.password(),
                memberCommand.employed(),
                memberCommand.gitLink(),
                memberCommand.resumeLink()
        );
    }

    public void granting() {
        this.admin = !this.admin;
    }

    public void employing() {
        this.employed = !this.employed;
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
