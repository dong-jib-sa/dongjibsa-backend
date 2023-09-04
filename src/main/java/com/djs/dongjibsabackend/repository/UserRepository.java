package com.djs.dongjibsabackend.repository;

import com.djs.dongjibsabackend.domain.entity.UserEntity;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findById(Long id);
    UserEntity findByUserName(String userName);
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
}
