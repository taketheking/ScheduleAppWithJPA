package com.example.schedulewithjpa.domain.Schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {

    @NotBlank
    @Size(min = 1, max = 10)
    private final String title;

    @NotNull
    @Size(max = 200)
    private final String contents;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$")
    private final String email;

    public CreateScheduleRequestDto(String title, String contents, String email) {
        this.title = title;
        this.contents = contents;
        this.email = email;
    }
}
