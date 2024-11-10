package com.auction.auction.bid.entity;

import com.auction.auction.market.base.BaseEntity;
import com.auction.auction.member.entity.Member;
import com.auction.auction.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Bid extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String status; // 경매가 종료 되었을 때 입찰 결과 최고가이거나 즉시구매했거나 ("Y" 또는 "N")

    private String downloadLink; // 다운로드 링크
    private Long bidPrice; // 입찰 가격

    private LocalDateTime bidTime; // 입찰 시간

    public void updateStatus() {
        if (product != null && !product.isOngoing()) {
            // 경매가 종료되었을 때 입찰 성공 여부에 따른 상태 업데이트
            if (this.getBidPrice() > product.getHighestBidPrice()) {
                this.status = "Y";  // 입찰 성공
            } else {
                this.status = "N";  // 입찰 실패
            }
        }
    }
}
