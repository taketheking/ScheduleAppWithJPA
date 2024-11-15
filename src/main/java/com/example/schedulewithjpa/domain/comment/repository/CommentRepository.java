package com.example.schedulewithjpa.domain.comment.repository;

import com.example.schedulewithjpa.domain.comment.Entity.Comment;
import com.example.schedulewithjpa.global.exception.NotExistIdException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Objects;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    default Comment findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(()-> new NotExistIdException("[id = " + id + "] 에 해당하는 댓글이 존재하지 않습니다."));
    }

    default List<Comment> findAllByUserId(Long userId) {
        return findAll().stream().filter(comment -> Objects.equals(comment.getUser().getId(), userId)).toList();
    }

    default List<Comment> findAllByScheduleId(Long scheduleId) {
        return findAll().stream().filter(comment -> Objects.equals(comment.getSchedule().getId(), scheduleId)).toList();
    }

}
