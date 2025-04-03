package com.example.todo.service;

import com.example.todo.dto.ScheduleResponseDto;
import com.example.todo.entity.Member;
import com.example.todo.entity.Schedule;
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
public class ScheduleService {

    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDto save(String title, String contents, String username, Long userId) {

        Member findMember = memberRepository.findByIdOrElseThrow(userId);

        Schedule schedule = new Schedule(title, contents);

        schedule.setMember(findMember);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule.getId(), savedSchedule.getTitle(), savedSchedule.getContents(), savedSchedule.getMember().getUsername());
    }

    public Page<ScheduleResponseDto> findAll(Pageable pageable) {

        Page<Schedule> schedules = scheduleRepository.findAll(pageable);

        return scheduleRepository.findAll(pageable).map(ScheduleResponseDto::toDto);
    }

    public Page<ScheduleResponseDto> findByMemberId(Long userId, Pageable pageable) {

        Page<Schedule> schedules = scheduleRepository.findByMemberId(userId, pageable);

        return scheduleRepository.findByMemberId(userId, pageable).map(ScheduleResponseDto::toDto);
    }

    public ScheduleResponseDto findByMemberIdAndScheduleId(Long userId, Long scheduleId) {

        Schedule schedule = scheduleRepository.findByMemberIdAndId(userId, scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정이 없습니다."));

        return new ScheduleResponseDto(schedule.getId(), schedule.getTitle(), schedule.getContents(), schedule.getMember().getUsername());
    }

    public void deleteByUserIdAndScheduleId(Long userId, Long scheduleId) {

        Schedule schedule = scheduleRepository.findByMemberIdAndId(userId, scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정이 없습니다."));

        scheduleRepository.delete(schedule);
    }

    @Transactional
    public void updateSchedule(Long userId, Long scheduleId, String newTitle, String newContents) {

        Schedule schedule = scheduleRepository.findByMemberIdAndId(userId, scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정이 없습니다."));

        schedule.updateSchedule(newTitle, newContents);
    }

}