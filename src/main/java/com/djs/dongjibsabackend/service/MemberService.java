package com.djs.dongjibsabackend.service;

import com.djs.dongjibsabackend.domain.dto.member.MemberDto;
import com.djs.dongjibsabackend.domain.dto.member.MemberRequest;
import com.djs.dongjibsabackend.domain.entity.MemberEntity;
import com.djs.dongjibsabackend.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public boolean verifyPhoneNumber(MemberRequest memberRequest) {

        String phoneNumber = memberRequest.getPhoneNumber();
        Optional<MemberEntity> member = memberRepository.findByPhoneNumber(phoneNumber);

        if (member.isPresent()) {
            log.info("이미 존재하는 회원입니다. ");
            return true;
        } else {
            log.info ("해당 전화번호가 존재하지 않아 회원가입을 진행합니다.");
            MemberDto memberDto = MemberDto.builder()
                                           .phoneNumber(memberRequest.getPhoneNumber())
                                           .build();
            MemberEntity newbie = MemberDto.toEntity(memberDto);
            memberRepository.save(newbie);
            return false;
        }
    }
}