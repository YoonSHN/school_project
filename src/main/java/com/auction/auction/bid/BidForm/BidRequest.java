package com.auction.auction.bid.BidForm;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

public class BidRequest {
    @NotNull(message="상품 ID는 필수 입니다.")
    private Long productId;

    @NotNull(message = "입찰 금액은 필수입니다.")
    @Min(value = 1, message = "입찰 금액은 1 이상이어야 합니다.")
    private Long bidAmount;

}
