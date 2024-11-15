package com.example.schedulewithjpa.domain.comment.dto;

import com.example.schedulewithjpa.domain.comment.Entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private final Long id;

    private final String comment;

    private final String username;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public CommentResponseDto(Long id, String comment, String username, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.comment = comment;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CommentResponseDto toDto(Comment comment) {

        return new CommentResponseDto(comment.getId(), comment.getComment(), comment.getUser().getUsername(), comment.getCreatedAt(), comment.getModifiedAt());
    }
}
