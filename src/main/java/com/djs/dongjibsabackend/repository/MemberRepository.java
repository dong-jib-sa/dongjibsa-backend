package com.djs.dongjibsabackend.repository;

import com.djs.dongjibsabackend.domain.entity.MemberEntity;
import com.djs.dongjibsabackend.domain.enums.SocialType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findById(Long id);
    MemberEntity findByNickName(String nickName);
    Optional<MemberEntity> findByPhoneNumber(String phoneNumber);
    Optional<MemberEntity> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
    Optional<MemberEntity> findBySocialTypeAndEmail(SocialType socialType, String email);
}
