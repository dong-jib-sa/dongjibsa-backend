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
    private String dong; // 동 이름
    private String date; // 작성일
    private String time; // 작성 시간
    private String content; // 글 내용
    private Integer expectingPrice; // 총 예상 가격
    private Integer pricePerOne; // 1인당 예상 가격
    private Integer peopleCount; // 파티원 수
    private List<PostIngredientResponse> ingredients; // 재료 목록

    public static PostDetailResponse of (PostDto postDto) {
        // String dong = postDto.getLocation().getDong();
        LocalDateTime createdTime = postDto.getCreatedAt();
        String formattedDateTime = createdTime.format(CUSTOM_FORMATTER);
        String[] formattedDateTimeArr =formattedDateTime.split("\\s");

        String date = formattedDateTimeArr[0];
        String time = formattedDateTimeArr[1];

        List<PostIngredientResponse> ingredientDtos = PostIngredientResponse.of(postDto.getRecipeIngredients());

        return PostDetailResponse.builder()
                                 .title(postDto.getTitle())
                                 //.dong(dong)
                                 .date(date)
                                 .time(time)
                                 .content(postDto.getContent())
                                 .expectingPrice(postDto.getExpectingPrice())
                                 .pricePerOne(postDto.getPricePerOne())
                                 .peopleCount(postDto.getPeopleCount())
                                 .ingredients(ingredientDtos)
                                 .build();

    }
}
