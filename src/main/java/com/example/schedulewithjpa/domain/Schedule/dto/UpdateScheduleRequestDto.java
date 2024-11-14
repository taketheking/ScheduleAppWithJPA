package com.example.schedulewithjpa.domain.Schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateScheduleRequestDto {

    @NotBlank
    @Size(min = 1, max = 10)
    private final String title;

    @NotNull
    @Size(max = 200)
    private final String contents;

    public UpdateScheduleRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
