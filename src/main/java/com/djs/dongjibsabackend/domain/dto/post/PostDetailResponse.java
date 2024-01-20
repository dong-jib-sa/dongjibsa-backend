package com.djs.dongjibsabackend.domain.dto.post;

import com.djs.dongjibsabackend.domain.dto.post_ingredient.PostIngredientDto;
import com.djs.dongjibsabackend.domain.dto.post_ingredient.PostIngredientResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailResponse {

    final static DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    private String title; // 글 제목
    private String content; // 글 내용
    private Integer expectingPrice; // 총 예상 가격
    private Integer pricePerOne; // 1인당 예상 가격
    private String nickName; // 작성자 닉네임
    private double calorie; // 칼로리
    private Integer peopleCount; // 파티원 수
    // private String dong; // (삭제) 동 이름
    private List<PostIngredientResponse> ingredients; // 재료 목록
    private List<String> imgUrls;
    private String date; // 작성일
    private String time; // 작성 시간
    private boolean includeCalorie;

    public static PostDetailResponse of (PostDto postDto) {
        // String dong = postDto.getLocation().getDong();
        LocalDateTime createdTime = postDto.getCreatedAt();
        String formattedDateTime = createdTime.format(CUSTOM_FORMATTER);
        String[] formattedDateTimeArr =formattedDateTime.split("\\s");

        String date = formattedDateTimeArr[0];
        String time = formattedDateTimeArr[1];

        List<PostIngredientResponse> ingredientDtos = PostIngredientResponse.of(postDto.getRecipeIngredients());
        boolean includeCalorie = false;

        if (postDto.getRecipeCalorie().getCalorie() > 0.0) {
            includeCalorie = true;
        }

        return PostDetailResponse.builder()
                                 .title(postDto.getTitle())
                                 .content(postDto.getContent())
                                 .expectingPrice(postDto.getExpectingPrice())
                                 .pricePerOne(postDto.getPricePerOne())
                                 .nickName(postDto.getMember().getNickName())
                                 .calorie(postDto.getRecipeCalorie().getCalorie())
                                 .peopleCount(postDto.getPeopleCount())
                                 .ingredients(ingredientDtos)
                                 .imgUrls(postDto.getImgUrls())
                                 .date(date) // 작성일
                                 .time(time) // 작성 시간
                                 .includeCalorie(includeCalorie) // 칼로리 유무
                                 .build();
    }
}
