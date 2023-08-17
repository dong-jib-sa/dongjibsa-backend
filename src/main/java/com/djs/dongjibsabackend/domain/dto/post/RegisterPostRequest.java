package com.djs.dongjibsabackend.domain.dto.post;

import com.djs.dongjibsabackend.domain.dto.post_ingredient.PostIngredientDto;
import com.djs.dongjibsabackend.domain.dto.post_ingredient.PostIngredientRequest;
import com.djs.dongjibsabackend.domain.dto.recipe_calorie.RecipeCalorieDto;
import com.djs.dongjibsabackend.domain.entity.LocationEntity;
import com.djs.dongjibsabackend.domain.entity.UserEntity;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
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

    private String title; // 글 제목
    private String userName; // 작성자 이름
    private String recipeName; // 레시피 이름
    private Integer expectingPrice; // 예상 가격
    private Integer pricePerOne; // 1인 당 예상 가격
    private Integer peopleCount; // 파티원 수
    private String dong; // 위치 dto
    private List<PostIngredientRequest> ingredients; // 게시글 재료 목록
    private String content; // 내용
    private MultipartFile image; // 레시피 사진

}
