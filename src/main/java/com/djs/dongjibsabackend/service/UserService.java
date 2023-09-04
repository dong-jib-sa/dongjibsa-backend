package com.djs.dongjibsabackend.service;

import com.djs.dongjibsabackend.domain.dto.user.UserDto;
import com.djs.dongjibsabackend.domain.entity.UserEntity;
import com.djs.dongjibsabackend.repository.UserRepository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Optional<UserEntity> findUserByPhoneNumber(String phoneNumber) {
        Optional<UserEntity> user = userRepository.findByPhoneNumber((phoneNumber));

        return user;
    }

    public String join(String phoneNumber) {
        UserEntity user = UserEntity.builder()
                                    .phoneNumber(phoneNumber)
                                    .build();
        UserEntity savedUser = userRepository.save(user);
        UserDto userDto = UserDto.toDto(savedUser);
        log.info("회원가입 성공");

        return userDto.getPhoneNumber();
    }
}