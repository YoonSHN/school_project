package com.auction.auction.inquiry.entity;

import com.auction.auction.market.base.BaseEntity;
import com.auction.auction.member.entity.Member;
import com.auction.auction.product.entity.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Inquiry extends BaseEntity {
    private String title;
    private String body;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id")
    private Product product;

}
