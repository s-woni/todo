package com.example.todo.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "schedule")
public class Schedule extends BaseEntity {

    // 고유 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제목
    @Column(nullable = false)
    private String title;

    // 내용
    private String contents;

    // 멤버와 연결
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 댓글과 연결
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Schedule() {

    }

    public Schedule(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void updateSchedule(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
