package com.example.todo.service;

import com.example.todo.dto.CommentResponseDto;
import com.example.todo.entity.Comment;
import com.example.todo.entity.Member;
import com.example.todo.entity.Schedule;
import com.example.todo.repository.CommentRepository;
import com.example.todo.repository.MemberRepository;
import com.example.todo.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    public CommentResponseDto save(String comments, String username, Long id, Long scheduleId) {

        Member findMember = memberRepository.findByIdOrElseThrow(id);
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        String writer = findMember.getUsername();

        Comment comment = new Comment(comments);
        comment.setMember(findMember);
        comment.setSchedule(findSchedule);

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment.getId(), savedComment.getComments(), writer);
    }

    public List<CommentResponseDto> findByScheduleId(Long scheduleId) {

        List<Comment> comments = commentRepository.findByScheduleId(scheduleId);

        return comments.stream().map(CommentResponseDto::toDto).collect(Collectors.toList());
    }
}
