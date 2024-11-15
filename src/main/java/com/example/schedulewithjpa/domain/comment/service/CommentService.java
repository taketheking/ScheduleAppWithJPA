package com.example.schedulewithjpa.domain.comment.service;

import com.example.schedulewithjpa.domain.Schedule.Entity.Schedule;
import com.example.schedulewithjpa.domain.Schedule.repository.ScheduleRepository;
import com.example.schedulewithjpa.domain.User.Entity.User;
import com.example.schedulewithjpa.domain.User.repository.UserRepository;
import com.example.schedulewithjpa.domain.base.Valid.AccessWrongValid;
import com.example.schedulewithjpa.domain.comment.Entity.Comment;
import com.example.schedulewithjpa.domain.comment.dto.CommentResponseDto;
import com.example.schedulewithjpa.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final AccessWrongValid accessWrongValid;

    public CommentResponseDto save(String comments, String email, Long scheduleId) {
        User user = userRepository.findUserByEmailOrElseThrow(email);

        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        Comment commentObj = new Comment(comments, user, schedule);

        Comment savedComment = commentRepository.save(commentObj);

        return CommentResponseDto.toDto(savedComment);
    }

    public List<CommentResponseDto> findAllByScheduleId(Long scheduleId) {

        return commentRepository.findAllByScheduleId(scheduleId).stream().map(CommentResponseDto::toDto).toList();
    }

    public List<CommentResponseDto> findAllByUserId(Long userId) {

        return commentRepository.findAllByUserId(userId).stream().map(CommentResponseDto::toDto).toList();
    }

    public CommentResponseDto findById(Long id) {
        Comment comment = commentRepository.findByIdOrElseThrow(id);

        return CommentResponseDto.toDto(comment);
    }

    @Transactional
    public CommentResponseDto updateById(Long id, String comments, Long userId) {

        Comment comment = commentRepository.findByIdOrElseThrow(id);

        accessWrongValid.AccessMisMatchId(comment.getUser().getId(), userId);

        if (comments != null) {
            comment.updateComment(comments);
        }

        return CommentResponseDto.toDto(comment);

    }

    public void delete(Long id, Long userId) {
        Comment comment = commentRepository.findByIdOrElseThrow(id);

        accessWrongValid.AccessMisMatchId(comment.getUser().getId(), userId);

        commentRepository.delete(comment);
    }

}
