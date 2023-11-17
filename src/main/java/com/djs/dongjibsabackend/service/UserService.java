package com.djs.dongjibsabackend.service;

import com.djs.dongjibsabackend.domain.dto.user.UserDto;
import com.djs.dongjibsabackend.domain.dto.user.UserRequest;
import com.djs.dongjibsabackend.domain.entity.UserEntity;
import com.djs.dongjibsabackend.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean verifyPhoneNumber(UserRequest userRequest) {

        String phoneNumber = userRequest.getPhoneNumber();

        Optional<UserEntity> userEntity = userRepository.findByPhoneNumber(phoneNumber);
        System.out.println(userEntity);

        if (userEntity.isPresent()) {
            log.info("이미 존재하는 회원입니다. ");
            return true;
        } else {
            log.info("해당 전화번호가 존재하지 않아 회원가입을 진행합니다.");
            UserDto userDto = UserDto.builder()
                                     .phoneNumber(userRequest.getPhoneNumber())
                                     .build();
            UserEntity newbie = UserDto.toEntity(userDto);
            userRepository.save(newbie);
            return false;
        }
    }
}