package com.example.schedulewithjpa.domain.Schedule.repository;

import com.example.schedulewithjpa.domain.Schedule.Entity.Schedule;
import com.example.schedulewithjpa.global.exception.NotExistIdException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository  extends JpaRepository<Schedule, Long> {
    default Schedule findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(()-> new NotExistIdException("[id = " + id + "] 에 해당하는 일정이 존재하지 않습니다."));
    }
}
