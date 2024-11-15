package com.example.schedulewithjpa.domain.Schedule.dto;

import com.example.schedulewithjpa.domain.Schedule.Entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {

    private final String title;

    private final String contents;

    private final Integer commentCount;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    private final  String username;


    public ScheduleResponseDto(String title, String contents, Integer commentCount, LocalDateTime createdAt, LocalDateTime updatedAt, String username) {
        this.title = title;
        this.contents = contents;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.username = username;
    }


    public static ScheduleResponseDto toDto(Schedule schedule) {

        return new ScheduleResponseDto(schedule.getTitle(), schedule.getContents(), schedule.getComments().size(), schedule.getCreatedAt(), schedule.getModifiedAt(), schedule.getUser().getUsername());

    }
}
