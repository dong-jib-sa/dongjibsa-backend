package com.djs.dongjibsabackend.service;

import static java.lang.System.getenv;

import com.djs.dongjibsabackend.domain.dto.member.MemberDto;
import com.djs.dongjibsabackend.domain.entity.MemberEntity;
import com.djs.dongjibsabackend.domain.enums.SocialType;
import com.djs.dongjibsabackend.repository.MemberRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuthService {

    private final MemberRepository memberRepository;
    Map<String, String> env = getenv();
    private String client_id = env.get("SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_KAKAO_CLIENTID");
    private String redirect_uri = env.get("KAKAO_REDIRECT_URI");

    public String getKakaoAccessToken (String code) {
        String accessToken = "";
        String refreshToken = "";
        String postRequestURL = "https://kauth.kakao.com/oauth/token"; // 토큰 받기

        try {
            URL url = new URL(postRequestURL);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id="); // TODO REST_API_KEY 입력
            sb.append(client_id);
            sb.append("&redirect_uri="); // TODO 인가코드 받은 redirect_uri 입력
            sb.append(redirect_uri);
            sb.append("&code=" + code);
            System.out.println(sb);
            bw.write(sb.toString());
            bw.flush();

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            accessToken = element.getAsJsonObject().get("access_token").getAsString();
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("Access-Token: " + accessToken);
            System.out.println("Refresh-Token: " + refreshToken);

            br.close();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return accessToken;
    }

    public MemberDto createKakaoUser(String accessToken) throws Exception{
        String requestURL = "https://kapi.kakao.com/v2/user/me";
        String userId, email;

        URL url = new URL(requestURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);

        int responseCode = connection.getResponseCode();
        // System.out.println("Response Code: " + responseCode);
        log.info("Response Code: {}", responseCode);

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }
        // System.out.println("response body : " + result);
        log.info("Response body: {}", result);

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(result);

        userId = element.getAsJsonObject().get("id").getAsString();
        boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();

        email = "";
        if (hasEmail) {
            email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
        }

        log.info("user_id: {}", userId);
        System.out.println("user_id: " + userId);
        log.info("email: {}", email);

        MemberDto memberDto = MemberDto.builder().socialType(SocialType.KAKAO).socialId(userId).email(email)
                                       .build();

        MemberEntity member = MemberDto.toEntity(memberDto);
        MemberEntity savedMember = memberRepository.save(member);
        MemberDto savedMemberDto = MemberDto.builder()
                                            .memberId(savedMember.getId())
                                            .email(savedMember.getEmail())
                                            .socialId(savedMember.getSocialId())
                                            .socialType(savedMember.getSocialType())
                                            .build();
        br.close();

        return savedMemberDto;
    }
}