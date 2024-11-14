package com.example.schedulewithjpa.domain.comment.controller;

import com.example.schedulewithjpa.domain.User.Entity.User;
import com.example.schedulewithjpa.domain.comment.dto.CommentResponseDto;
import com.example.schedulewithjpa.domain.comment.dto.CreateCommentRequestDto;
import com.example.schedulewithjpa.domain.comment.dto.UpdateCommentRequestDto;
import com.example.schedulewithjpa.domain.comment.service.CommentService;
import com.example.schedulewithjpa.global.Const.Const;
import com.example.schedulewithjpa.global.exception.BadValueException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> save(@RequestBody @Valid CreateCommentRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadValueException(String.valueOf(bindingResult.getFieldError().getDefaultMessage()));
        }

        CommentResponseDto commentResponseDto = commentService.save(requestDto.getComment(), requestDto.getEmail(), requestDto.getScheduleId());

        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<List<CommentResponseDto>> findAllByScheduleId(@PathVariable Long scheduleId) {

        List<CommentResponseDto> commentResponseDtoList = commentService.findAllByScheduleId(scheduleId);

        return new ResponseEntity<>(commentResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<CommentResponseDto>> findAllByUserId(@PathVariable Long userId) {

        List<CommentResponseDto> commentResponseDtoList = commentService.findAllByUserId(userId);

        return new ResponseEntity<>(commentResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> findById(@PathVariable Long id) {

        CommentResponseDto commentResponseDto = commentService.findById(id);

        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateById(@PathVariable Long id, @RequestBody @Valid UpdateCommentRequestDto requestDto, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new BadValueException(String.valueOf(bindingResult.getFieldError().getDefaultMessage()));
        }

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(Const.LOGIN_USER);

        CommentResponseDto commentResponseDto = commentService.updateById(id, requestDto.getComment(), user.getId());

        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(Const.LOGIN_USER);

        commentService.delete(id, user.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
