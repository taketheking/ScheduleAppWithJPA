package com.example.schedulewithjpa.domain.Schedule.controller;

import com.example.schedulewithjpa.domain.Schedule.dto.CreateScheduleRequestDto;
import com.example.schedulewithjpa.domain.Schedule.dto.ScheduleResponseDto;
import com.example.schedulewithjpa.domain.Schedule.dto.UpdateScheduleRequestDto;
import com.example.schedulewithjpa.domain.Schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("schedules")
@RequiredArgsConstructor
public class ScheduleController {

    ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> save(@RequestBody CreateScheduleRequestDto requestDto) {

        ScheduleResponseDto scheduleResponseDto = scheduleService.save(requestDto.getTitle(), requestDto.getContents(), requestDto.getUsername());

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAll() {

        List<ScheduleResponseDto> scheduleResponseDtoList = scheduleService.findAll();

        return new ResponseEntity<>(scheduleResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findById(@PathVariable Long id) {

        ScheduleResponseDto scheduleResponseDto = scheduleService.findById(id);

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateById(@PathVariable Long id, @RequestBody UpdateScheduleRequestDto requestDto) {

        ScheduleResponseDto scheduleResponseDto = scheduleService.updateById(id, requestDto.getTitle(), requestDto.getContents());

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        scheduleService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
