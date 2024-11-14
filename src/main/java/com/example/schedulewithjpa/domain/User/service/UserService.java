package com.example.schedulewithjpa.domain.User.service;

import com.example.schedulewithjpa.domain.User.Entity.User;
import com.example.schedulewithjpa.domain.User.dto.SignUpResponseDto;
import com.example.schedulewithjpa.domain.User.dto.UserResponseDto;
import com.example.schedulewithjpa.domain.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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

        Optional<User> optionalUser = userRepository.findById(id);

        // NPE 방지
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        User findUser = optionalUser.get();

        return new UserResponseDto(findUser.getUsername(), findUser.getEmail());
    }

}
