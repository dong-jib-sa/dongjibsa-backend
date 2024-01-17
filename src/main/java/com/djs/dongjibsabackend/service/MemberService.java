package com.djs.dongjibsabackend.service;

import com.djs.dongjibsabackend.domain.dto.member.MemberDto;
import com.djs.dongjibsabackend.domain.dto.member.OAuthMemberRequest;
import com.djs.dongjibsabackend.domain.dto.member.PhoneNumberMemberRequest;
import com.djs.dongjibsabackend.domain.entity.MemberEntity;
import com.djs.dongjibsabackend.domain.enums.SocialType;
import com.djs.dongjibsabackend.exception.AppException;
import com.djs.dongjibsabackend.exception.ErrorCode;
import com.djs.dongjibsabackend.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public boolean verifyPhoneNumber(PhoneNumberMemberRequest phoneNumberMemberRequest) {

        String phoneNumber = phoneNumberMemberRequest.getPhoneNumber();

        Optional<MemberEntity> member = memberRepository.findByPhoneNumber(phoneNumber);

        if (member.isPresent()) {
            log.info("member: {}", member);
            log.info("이미 존재하는 회원입니다. ");
            return true;
        } else {
            log.info("신규 유저입니다. 약관 동의 후 회원 가입을 진행합니다.");
            return false;
        }
    }

    public MemberDto savePhoneNumberUser (PhoneNumberMemberRequest phoneNumberMemberRequest) {

        String nickName = phoneNumberMemberRequest.getNickName(); /* 닉네임 */
        String phoneNumber = phoneNumberMemberRequest.getPhoneNumber(); /* 전화번호 */
        log.info("nickName: {}", nickName);
        log.info("phoneNumber: {}", phoneNumber);

        memberRepository.findByPhoneNumber(phoneNumber)
                        .ifPresent( member -> {
                            throw new AppException(ErrorCode.DUPLICATE_PHONE_NUMBER, String.format("%s는 이미 존재하는 전화번호입니다.", phoneNumber));
        });

        MemberDto memberDto = MemberDto.builder()
                                       .nickName(nickName)
                                       .phoneNumber(phoneNumber)
                                       .build();

        System.out.println("memberDto: " + memberDto.getNickName());
        System.out.println("memberDto: " + memberDto.getPhoneNumber());

        MemberEntity memberEntity = MemberDto.toEntity(memberDto);
        MemberEntity savedMember = memberRepository.save(memberEntity);
        return MemberDto.toDto(savedMember);
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
                log.info("신규 Kakao 유저입니다. 약관 동의 후 회원 가입을 진행합니다.");
                return false;
            }
        } else {
            Optional<MemberEntity> appleMemeber = memberRepository.findBySocialTypeAndEmail(socialType, email);
            if (appleMemeber.isPresent()) {
                log.info("이미 존재하는 Apple 회원입니다.");
                return true;
            } else {
                log.info("신규 Apple 유저입니다. 약관 동의 후 회원 가입을 진행합니다.");
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

            memberRepository.findBySocialTypeAndSocialId(socialType, socialId)
                            .ifPresent( member -> {
                                throw new AppException(ErrorCode.DUPLICATE_USER, String.format("%s는 이미 존재하는 카카오 사용자입니다.", socialId));
                            });

            MemberDto memberDto = MemberDto.builder()
                                           .nickName(nickName)
                                           .email(email)
                                           .socialType(socialType)
                                           .socialId(socialId)
                                           .build();
            MemberEntity savedMember = memberRepository.save(MemberDto.toEntity(memberDto));
            return MemberDto.toDto(savedMember);
        } else {

            memberRepository.findBySocialTypeAndEmail(socialType, email)
                            .ifPresent( member -> {
                                throw new AppException(ErrorCode.DUPLICATE_USER, String.format("%s는 이미 존재하는 애플 사용자입니다.", email));
                            });

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