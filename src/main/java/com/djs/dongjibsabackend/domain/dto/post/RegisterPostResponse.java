package com.djs.dongjibsabackend.domain.dto.post;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPostResponse {

    private PostDto postDto;
    private Boolean includeCalorie;

    public static List<RegisterPostResponse> of(List<PostDto> postDtoList) {
        List<RegisterPostResponse> responseList = new ArrayList<>();
        for (PostDto dto: postDtoList) {
            boolean includeCalorie = false;
            if (dto.getRecipeCalorie().getCalorie() > 0.0) {
                includeCalorie = true;
            }
            RegisterPostResponse response = RegisterPostResponse.builder().postDto(dto).includeCalorie(includeCalorie).build();
            responseList.add(response);
        }
        return responseList;
    }
}
