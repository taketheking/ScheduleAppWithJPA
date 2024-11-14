package com.example.schedulewithjpa.domain.base.Valid;

import com.example.schedulewithjpa.global.exception.NotMatchUserException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AccessWrongValid {

    public void AccessMisMatchId(Long numberOne, Long numberTwo) {
        if(Objects.equals(numberOne, numberTwo)){
            throw new NotMatchUserException("다른 유저의 정보를 잘못 접근하고 있습니다.");
        }
    }
}
