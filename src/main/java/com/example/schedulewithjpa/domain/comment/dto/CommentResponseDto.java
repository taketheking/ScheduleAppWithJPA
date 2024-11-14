package com.example.schedulewithjpa.domain.comment.dto;

import com.example.schedulewithjpa.domain.comment.Entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private final Long id;

    private final String comment;

    private final String username;

    public CommentResponseDto(Long id, String comment, String username) {
        this.id = id;
        this.comment = comment;
        this.username = username;
    }

    public static CommentResponseDto toDto(Comment comment) {

        return new CommentResponseDto(comment.getId(), comment.getComment(), comment.getUser().getUsername());
    }
}
