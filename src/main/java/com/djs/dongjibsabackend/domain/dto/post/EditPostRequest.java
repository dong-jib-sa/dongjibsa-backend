package com.djs.dongjibsabackend.domain.dto.post;

import com.djs.dongjibsabackend.domain.dto.post_ingredient.PostIngredientRequest;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
// @AllArgsConstructor
// @Builder
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EditPostRequest {

    private Long memberId;

    private String title; // 글 제목
    private String content; // 내용
    private String recipeName; // 레시피명
    private Integer expectingPrice; // 예상 가격
    private Integer pricePerOne; // 1인 당 예상 가격
    private Integer peopleCount; // 파티원 수
    private List<PostIngredientRequest> ingredients; // 게시글 재료 목록
    private List<MultipartFile> images; // 레시피 사진

}
