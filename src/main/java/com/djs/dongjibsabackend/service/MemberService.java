package com.djs.dongjibsabackend.service;

import com.djs.dongjibsabackend.domain.dto.member.MemberDto;
import com.djs.dongjibsabackend.domain.dto.member.OAuthMemberRequest;
import com.djs.dongjibsabackend.domain.dto.member.UserRequest;
import com.djs.dongjibsabackend.domain.entity.MemberEntity;
import com.djs.dongjibsabackend.domain.enums.SocialType;
import com.djs.dongjibsabackend.repository.MemberRepository;
import java.util.Optional;
import javax.swing.text.html.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public boolean verifyPhoneNumber(UserRequest userRequest) {

        String phoneNumber = userRequest.getPhoneNumber();

        Optional<MemberEntity> member = memberRepository.findByPhoneNumber(phoneNumber);
        System.out.println(member);

        if (member.isPresent()) {
            log.info("이미 존재하는 회원입니다. ");
            return true;
        } else {
            log.info("해당 전화번호가 존재하지 않아 회원가입을 진행합니다.");
            MemberDto memberDto = MemberDto.builder()
                                           .phoneNumber(userRequest.getPhoneNumber())
                                           .build();
            MemberEntity newbie = MemberDto.toEntity(memberDto);
            memberRepository.save(newbie);
            return false;
        }
    }

    public boolean verifySocialIdAndEmail(OAuthMemberRequest oAuthMemberRequest) {
        SocialType socialType = oAuthMemberRequest.getSocialType();
        String socialId = oAuthMemberRequest.getSocialId();
        String email = oAuthMemberRequest.getEmail();

        if (socialType.equals(SocialType.KAKAO)) {
            Optional<MemberEntity> kakaoMember = memberRepository.findBySocialTypeAndSocialId(socialType, socialId);
            if (kakaoMember.isPresent()) {
                log.info("이미 존재하는 Kakao 회원입니다.");
                return true;
            } else {
                log.info("신규 Kakao 유저입니다. 회원가입을 진행합니다.");
                return false;
            }
        } else {
            Optional<MemberEntity> appleMemeber = memberRepository.findBySocialTypeAndEmail(socialType, email);
            if (appleMemeber.isPresent()) {
                log.info("이미 존재하는 Apple 회원입니다.");
                return true;
            } else {
                log.info("신규 Apple 유저입니다. 회원가입을 진행합니다.");
                return false;
            }
        }
    }

    public MemberDto saveOAuthUser(OAuthMemberRequest oAuthMemberRequest) {
        String nickName = oAuthMemberRequest.getNickName();
        SocialType socialType = oAuthMemberRequest.getSocialType();
        String socialId = oAuthMemberRequest.getSocialId();
        String email = oAuthMemberRequest.getEmail();

        if (socialType.equals(SocialType.KAKAO)) {
            MemberDto memberDto = MemberDto.builder()
                                           .nickName(nickName)
                                           .email(email)
                                           .socialType(socialType)
                                           .socialId(socialId)
                                           .build();
            MemberEntity savedMember = memberRepository.save(MemberDto.toEntity(memberDto));
            return MemberDto.toDto(savedMember);
        } else {
            MemberDto memberDto = MemberDto.builder()
                                           .nickName(nickName)
                                           .email(email)
                                           .socialType(socialType)
                                           .build();
            MemberEntity savedMember = memberRepository.save(MemberDto.toEntity(memberDto));
            return MemberDto.toDto(savedMember);
        }
    }
}