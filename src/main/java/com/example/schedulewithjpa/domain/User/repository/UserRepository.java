package com.example.schedulewithjpa.domain.User.repository;

import com.example.schedulewithjpa.domain.User.Entity.User;
import com.example.schedulewithjpa.global.exception.NotExistIdException;
import com.example.schedulewithjpa.global.exception.NotMatchUserException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    default User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(()-> new NotExistIdException("[id = " + id + "] 에 해당하는 유저가 존재하지 않습니다."));
    }

    Optional<User> findUserByEmail(String email);

    default User findUserByEmailOrElseThrow(String email) {
        return findUserByEmail(email).orElseThrow(()-> new NotMatchUserException(email+ "과 일치하는 유저가 존재하지 않습니다."));
    }

}

