package com.example.todo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {

    // 고유 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 댓글 내용
    @NotBlank(message = "댓글 내용이 비어있습니다")
    private String comments;

    // 멤버와 연결
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 일정과 연결
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public Comment() {

    }

    public Comment(String comments) {
        this.comments = comments;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void updateComment(String comments) {
        this.comments = comments;
    }
}
