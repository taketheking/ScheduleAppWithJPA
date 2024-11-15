package com.example.schedulewithjpa.domain.User.service;

import com.example.schedulewithjpa.domain.User.Entity.User;
import com.example.schedulewithjpa.domain.User.dto.UserResponseDto;
import com.example.schedulewithjpa.domain.User.repository.UserRepository;
import com.example.schedulewithjpa.domain.base.Valid.AccessWrongValid;
import com.example.schedulewithjpa.global.Config.PasswordEncoder;
import com.example.schedulewithjpa.global.exception.NotMatchPwException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AccessWrongValid accessWrongValid;

    public UserResponseDto signUp(String username, String password, String email) {

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(username, encodedPassword, email);

        User savedUser = userRepository.save(user);

        return UserResponseDto.toDto(savedUser);
    }

    public UserResponseDto findById(Long id) {

        User user = userRepository.findByIdOrElseThrow(id);

        return UserResponseDto.toDto(user);
    }

    @Transactional
    public void delete(Long id, String password, Long userId) {
        accessWrongValid.AccessMisMatchId(id, userId);

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
