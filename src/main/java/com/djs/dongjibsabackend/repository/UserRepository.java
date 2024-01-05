package com.djs.dongjibsabackend.repository;

import com.djs.dongjibsabackend.domain.entity.UserEntity;
import com.djs.dongjibsabackend.domain.enums.SocialType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findById(Long id);
    UserEntity findByUserName(String userName);
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
    Optional<UserEntity> findByRefreshToken(String refreshToken);
    // Social Login
    Optional<UserEntity> findBySocialTypeAndSocialId (SocialType socialType, String socialId);
    Optional<UserEntity> findByEmail (String email);

}