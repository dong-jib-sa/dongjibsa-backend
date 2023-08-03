package com.djs.dongjibsabackend.domain.dto.recipe;

public class RecipeResponse {

    private Long title;
    private String userName; // 작성자 이름
    private Long content;
    private Integer expectingPrice;
    private Integer pricePerOne;
    private Integer calorie;
    private Integer peopleCount;
    private Integer locationName;
    private String imgUrl;

    // - of (레시피 재료 dto를 받아서 response 객체로 만드는 메소드 작성하기)
}
