package com.example.todo.service;

import com.example.todo.dto.ScheduleResponseDto;
import com.example.todo.entity.Member;
import com.example.todo.entity.Schedule;
import com.example.todo.repository.MemberRepository;
import com.example.todo.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

        return new ScheduleResponseDto(savedSchedule.getId(), savedSchedule.getTitle(), savedSchedule.getContents());
    }

    public List<ScheduleResponseDto> findByMemberId(Long userId) {

        List<Schedule> schedules = scheduleRepository.findByMemberId(userId);

        return schedules.stream().map(ScheduleResponseDto::toDto).collect(Collectors.toList());
    }
}