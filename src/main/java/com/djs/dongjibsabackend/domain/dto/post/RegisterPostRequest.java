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

    // 기본 필드
    WritePostRequest writePostRequest;
    MultipartFile image; // 레시피 사진

}
