package com.example.todo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "댓글 내용이 비어있습니다")
    private String comments;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

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
