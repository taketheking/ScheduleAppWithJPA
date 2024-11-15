package com.example.schedulewithjpa.domain.Schedule.service;

import com.example.schedulewithjpa.domain.Schedule.Entity.Schedule;
import com.example.schedulewithjpa.domain.Schedule.dto.ScheduleResponseDto;
import com.example.schedulewithjpa.domain.Schedule.repository.ScheduleRepository;
import com.example.schedulewithjpa.domain.User.Entity.User;
import com.example.schedulewithjpa.domain.User.repository.UserRepository;
import com.example.schedulewithjpa.domain.base.Valid.AccessWrongValid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

        return ScheduleResponseDto.toDto(savedSchedule);
    }

    public List<ScheduleResponseDto> findAll(Pageable pageable) {

        return scheduleRepository.findAllByOrderByModifiedAtDesc(pageable).stream().map(ScheduleResponseDto::toDto).toList();
    }

    public ScheduleResponseDto findById(Long id) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);

        return ScheduleResponseDto.toDto(schedule);
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

        return ScheduleResponseDto.toDto(schedule);

    }

    public void delete(Long id, Long userId) {
        accessWrongValid.AccessMisMatchId(id, userId);

        scheduleRepository.deleteById(id);
    }

}
