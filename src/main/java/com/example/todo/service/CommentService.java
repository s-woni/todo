package com.example.todo.service;

import com.example.todo.dto.CommentResponseDto;
import com.example.todo.entity.Comment;
import com.example.todo.entity.Member;
import com.example.todo.entity.Schedule;
import com.example.todo.repository.CommentRepository;
import com.example.todo.repository.MemberRepository;
import com.example.todo.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    // 댓글 저장
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

    // 댓글 목록 조회
    public Page<CommentResponseDto> findByScheduleId(Long scheduleId, Pageable pageable) {

        Page<Comment> comments = commentRepository.findByScheduleId(scheduleId, pageable);

        return commentRepository.findByScheduleId(scheduleId, pageable).map(CommentResponseDto::toDto);
    }

    // 댓글 수정
    @Transactional
    public void updateComment(Long userId, Long scheduleId, Long commentId, String newComment) {

        Comment comment = commentRepository.findByScheduleIdAndId(scheduleId, commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글이 없습니다."));

        // 댓글 작성자인지 확인
        if (!comment.getMember().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인이 작성한 댓글이 아닙니다");
        }

        // 댓글 수정
        comment.updateComment(newComment);
    }

    // 댓글 삭제
    public void deleteComment(Long userId, Long scheduleId, Long commentId) {

        Comment comment = commentRepository.findByScheduleIdAndId(scheduleId, commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글이 없습니다."));

        // 댓글 작성자인지 확인
        if (!comment.getMember().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인이 작성한 댓글이 아닙니다");
        }

        // 댓글 삭제
        commentRepository.delete(comment);
    }
}
