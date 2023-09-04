package com.djs.dongjibsabackend.service;

import static java.lang.System.getenv;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SmsService {

    private String accountSid = getenv().get("TWILIO_ACCOUNT_SID");
    private String authToken = getenv().get("TWILIO_AUTH_TOKEN");

    // 휴대폰 번호로 인증번호 발송
    public String generateOTP(String phoneNumber) {

        Twilio.init(accountSid, authToken);

        Verification verification = Verification.creator(
                                                    "VA520076665ae173cacdf579a0b7671754", // this is your verification sid
                                                    phoneNumber, //this is your Twilio verified recipient phone number
                                                    "sms") // this is your channel type
                                                .create();
        log.info("OTP has been successfully generated, and awaits your verification {}", LocalDateTime.now());
        return "";
    }

    // check and validate the verification code
    public String verifyUserOTP(String phoneNumber, String inputCode) throws Exception {

        Twilio.init(accountSid, authToken);

        try {
            VerificationCheck verificationCheck = VerificationCheck.creator(("VA520076665ae173cacdf579a0b7671754"))
                .setTo(phoneNumber)
                .setCode(inputCode)
                .create();
        } catch (Exception e) {
            return "Verification failed.";
        }
        return "This user's verification has been completed successfully";
    }

}
