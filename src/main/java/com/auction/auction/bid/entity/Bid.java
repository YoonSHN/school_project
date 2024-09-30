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

    private Long bidPrice; // 입찰 가격

    private LocalDateTime bidTime; // 입찰 시간
}
