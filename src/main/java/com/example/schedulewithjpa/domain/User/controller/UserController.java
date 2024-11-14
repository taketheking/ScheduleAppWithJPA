package com.example.schedulewithjpa.domain.User.controller;

import com.example.schedulewithjpa.domain.User.Entity.User;
import com.example.schedulewithjpa.domain.User.dto.*;
import com.example.schedulewithjpa.domain.User.service.UserService;
import com.example.schedulewithjpa.global.Const.Const;
import com.example.schedulewithjpa.global.exception.BadValueException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@Valid @RequestBody SignUpRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadValueException(String.valueOf(bindingResult.getFieldError().getDefaultMessage()));
        }

        SignUpResponseDto signUpResponseDto =
                userService.signUp(
                        requestDto.getUsername(),
                        requestDto.getPassword(),
                        requestDto.getEmail()
                );

        return new ResponseEntity<>(signUpResponseDto, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {

        UserResponseDto userResponseDto = userService.findById(id);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @Valid @RequestBody DeleteRequestDto requestDto, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new BadValueException(String.valueOf(bindingResult.getFieldError().getDefaultMessage()));
        }
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(Const.LOGIN_USER);

        userService.delete(id, requestDto.getPassword(), user.getId());
        session.invalidate();

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDto requestDto, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new BadValueException(String.valueOf(bindingResult.getFieldError().getDefaultMessage()));
        }

        User user = userService.login(requestDto.getEmail(), requestDto.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute(Const.LOGIN_USER, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
