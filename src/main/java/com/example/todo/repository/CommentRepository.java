package com.example.todo.repository;

import com.example.todo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByScheduleId(Long scheduleId);

    Optional<Comment> findByScheduleIdAndId(Long scheduleId, Long commentId);
}
