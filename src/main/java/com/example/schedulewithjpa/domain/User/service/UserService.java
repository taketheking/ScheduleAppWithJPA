package com.example.schedulewithjpa.domain.User.service;

import com.example.schedulewithjpa.domain.User.Entity.User;
import com.example.schedulewithjpa.domain.User.dto.SignUpResponseDto;
import com.example.schedulewithjpa.domain.User.dto.UserResponseDto;
import com.example.schedulewithjpa.domain.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    public final UserRepository userRepository;

    public SignUpResponseDto signUP(String username, String password, String email) {

        User user = new User(username, password, email);

        User savedUser = userRepository.save(user);

        return new SignUpResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }

    public UserResponseDto findById(Long id) {

        User findUser = userRepository.findByIdOrElseThrow(id);

        return new UserResponseDto(findUser.getUsername(), findUser.getEmail());
    }

    public void delete(Long id) {

        User user = userRepository.findByIdOrElseThrow(id);

        userRepository.delete(user);
    }

    public User login(String email, String password) {

        return userRepository.findUserByEmailAndPasswordOrElseThrow(email, password);

    }
}
