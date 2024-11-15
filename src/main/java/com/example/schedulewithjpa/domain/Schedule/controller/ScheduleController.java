package com.example.schedulewithjpa.domain.Schedule.controller;

import com.example.schedulewithjpa.domain.Schedule.dto.CreateScheduleRequestDto;
import com.example.schedulewithjpa.domain.Schedule.dto.ScheduleResponseDto;
import com.example.schedulewithjpa.domain.Schedule.dto.UpdateScheduleRequestDto;
import com.example.schedulewithjpa.domain.Schedule.service.ScheduleService;
import com.example.schedulewithjpa.domain.User.Entity.User;
import com.example.schedulewithjpa.global.Const.Const;
import com.example.schedulewithjpa.global.exception.BadValueException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> save(@RequestBody @Valid CreateScheduleRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadValueException(String.valueOf(bindingResult.getFieldError().getDefaultMessage()));
        }

        ScheduleResponseDto scheduleResponseDto = scheduleService.save(requestDto.getTitle(), requestDto.getContents(), requestDto.getEmail());

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAll(@PageableDefault() Pageable pageable) {

        List<ScheduleResponseDto> scheduleResponseDtoList = scheduleService.findAll(pageable);

        return new ResponseEntity<>(scheduleResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findById(@PathVariable Long id) {

        ScheduleResponseDto scheduleResponseDto = scheduleService.findById(id);

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateById(@PathVariable Long id, @RequestBody @Valid UpdateScheduleRequestDto requestDto, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new BadValueException(String.valueOf(bindingResult.getFieldError().getDefaultMessage()));
        }

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(Const.LOGIN_USER);

        ScheduleResponseDto scheduleResponseDto = scheduleService.updateById(id, requestDto.getTitle(), requestDto.getContents(), user.getId());

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(Const.LOGIN_USER);

        scheduleService.delete(id, user.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
