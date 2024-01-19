package com.djs.dongjibsabackend.domain.dto.post;

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

}
