package com.example.schedulewithjpa.domain.User.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {

    private final String username;

    private final String email;

}
