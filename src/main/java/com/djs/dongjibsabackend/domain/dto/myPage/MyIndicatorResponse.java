package com.djs.dongjibsabackend.domain.dto.myPage;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class MyIndicatorResponse {

    private double calorieAvg;
    private Integer sumOfSharingAvailableQty;

    @Builder
    public MyIndicatorResponse(double calorieAvg, Integer sumOfSharingAvailableQty) {
        this.calorieAvg = calorieAvg;
        this.sumOfSharingAvailableQty = sumOfSharingAvailableQty;
    }
}
