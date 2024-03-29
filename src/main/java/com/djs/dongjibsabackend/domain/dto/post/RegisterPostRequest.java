package com.djs.dongjibsabackend.domain.dto.post;

import com.djs.dongjibsabackend.domain.dto.post_ingredient.PostIngredientRequest;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * 게시글 등록 시 요청 객체
 */

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterPostRequest {

    private Long memberId; // 유저 id값

    private String title; // 글 제목
    private String content; // 내용
    private String recipeName; // 레시피명
    private Integer expectingPrice; // 예상 가격
    private Integer pricePerOne; // 1인 당 예상 가격
    private Integer peopleCount; // 파티원 수
    private List<PostIngredientRequest> ingredients; // 게시글 재료 목록
//    private MultipartFile image; // 레시피 사진
    private List<MultipartFile> images; // 레시피 사진

}
