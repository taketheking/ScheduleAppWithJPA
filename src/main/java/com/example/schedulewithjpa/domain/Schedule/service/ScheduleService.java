package com.example.schedulewithjpa.domain.Schedule.service;

import com.example.schedulewithjpa.domain.Schedule.Entity.Schedule;
import com.example.schedulewithjpa.domain.Schedule.dto.ScheduleResponseDto;
import com.example.schedulewithjpa.domain.Schedule.repository.ScheduleRepository;
import com.example.schedulewithjpa.domain.User.Entity.User;
import com.example.schedulewithjpa.domain.User.repository.UserRepository;
import com.example.schedulewithjpa.domain.base.Valid.AccessWrongValid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    private final AccessWrongValid accessWrongValid;

    public ScheduleResponseDto save(String title, String contents, String email) {
        User user = userRepository.findUserByEmailOrElseThrow(email);

        Schedule schedule = new Schedule(title, contents, user);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule.getId(), savedSchedule.getTitle(), savedSchedule.getContents(), savedSchedule.getUser().getUsername());
    }

    public List<ScheduleResponseDto> findAll() {

        return scheduleRepository.findAll().stream().map(ScheduleResponseDto::toDto).toList();
    }

    public ScheduleResponseDto findById(Long id) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule.getId(), schedule.getTitle(), schedule.getContents(), schedule.getUser().getUsername());
    }

    @Transactional
    public ScheduleResponseDto updateById(Long id, String title, String contents, Long userId) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);

        accessWrongValid.AccessMisMatchId(schedule.getUser().getId(), userId);

        if (title != null) {
            schedule.updateTitle(title);
        }

        if (contents != null) {
            schedule.updateContents(contents);
        }

        return new ScheduleResponseDto(schedule.getId(), schedule.getTitle(), schedule.getContents(), schedule.getUser().getUsername());

    }

    public void delete(Long id, Long userId) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);

        accessWrongValid.AccessMisMatchId(schedule.getUser().getId(), userId);

        scheduleRepository.delete(schedule);
    }

}
