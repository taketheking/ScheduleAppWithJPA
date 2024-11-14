package com.example.schedulewithjpa.domain.User.repository;

import com.example.schedulewithjpa.domain.User.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    default User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    Optional<User> findUserByEmail(String username);

    default User findUserByEmailOrElseThrow(String username) {
        return findUserByEmail(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist username = " + username));
    }

    Optional<User> findUserByEmailAndPassword(String email, String password);

    default User findUserByEmailAndPasswordOrElseThrow(String email, String password) {
        return findUserByEmailAndPassword(email, password).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인에 실패하였습니다"));
    }

}
