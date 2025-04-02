package com.example.todo.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

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
