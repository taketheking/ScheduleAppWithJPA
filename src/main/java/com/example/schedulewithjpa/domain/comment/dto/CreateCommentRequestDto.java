package com.example.schedulewithjpa.domain.comment.dto;

import lombok.Getter;

@Getter
public class CreateCommentRequestDto {

    private final String comment;

    private final String email;

    private final Long scheduleId;

    public CreateCommentRequestDto(String comment, String email, Long scheduleId) {
        this.comment = comment;
        this.email = email;
        this.scheduleId = scheduleId;
    }
}
