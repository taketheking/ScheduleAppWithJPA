package com.example.schedulewithjpa.domain.User.service;

import com.example.schedulewithjpa.domain.User.Entity.User;
import com.example.schedulewithjpa.domain.User.dto.SignUpResponseDto;
import com.example.schedulewithjpa.domain.User.dto.UserResponseDto;
import com.example.schedulewithjpa.domain.User.repository.UserRepository;
import com.example.schedulewithjpa.global.Config.PasswordEncoder;
import com.example.schedulewithjpa.global.exception.NotMatchPwException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    public final UserRepository userRepository;

    public final PasswordEncoder passwordEncoder;

    public SignUpResponseDto signUp(String username, String password, String email) {

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(username, encodedPassword, email);

        User savedUser = userRepository.save(user);

        return new SignUpResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }

    public UserResponseDto findById(Long id) {

        User user = userRepository.findByIdOrElseThrow(id);

        return new UserResponseDto(user.getUsername(), user.getEmail());
    }

    @Transactional
    public void delete(Long id, String password) {

        User user = userRepository.findByIdOrElseThrow(id);

        if(passwordEncoder.matches(password, user.getPassword())){
            throw new NotMatchPwException("비밀번호가 일치하지 않습니다.");
        }

        userRepository.delete(user);
    }

    public User login(String email, String password) {

        User user = userRepository.findUserByEmailOrElseThrow(email);

        if(passwordEncoder.matches(password, user.getPassword())){
            throw new NotMatchPwException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }
}
