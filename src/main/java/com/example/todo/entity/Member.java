package com.example.todo.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "member")
public class Member extends BaseEntity {

    // 고유 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 유저 이름
    @Column(nullable = false)
    private String username;

    // 비밀번호
    @Column(nullable = false)
    private String password;

    // 이메일
    @Column(nullable = false, unique = true)
    private String email;

    // 일정과 연결
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();

    public Member() {
    }

    public Member(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateUserName(String name) {
        this.username = name;
    }

    public void removeSchedule(Schedule schedule) {
        schedules.remove(schedule);
        schedule.setMember(null);
    }
}
