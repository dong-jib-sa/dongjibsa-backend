package com.djs.dongjibsabackend.domain.dto.myPage;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class MyIndicatorResponse {

    private double calorieAvg;
    private double sumOfSharingAvailableQty;

    @Builder
    public MyIndicatorResponse(double calorieAvg, double sumOfSharingAvailableQty) {
        this.calorieAvg = calorieAvg;
        this.sumOfSharingAvailableQty = sumOfSharingAvailableQty;
    }
}
