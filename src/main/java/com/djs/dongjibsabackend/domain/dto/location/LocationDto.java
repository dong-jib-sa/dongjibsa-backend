package com.djs.dongjibsabackend.domain.dto.location;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationDto {

    private Long id;
    private String si;
    private String gu;
    private String dong;

    @Builder
    public LocationDto(Long id, String si, String gu, String dong) {
        this.id = id;
        this.si = si;
        this.gu = gu;
        this.dong = dong;
    }
}
