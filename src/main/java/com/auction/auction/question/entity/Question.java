package com.auction.auction.question.entity;

import com.auction.auction.market.base.BaseEntity;
import com.auction.auction.member.entity.Member;
import com.auction.auction.product.entity.Product;
import jakarta.persistence.Entity;

@Entity
public class Question extends BaseEntity {
    private String title;
    private String body;
    private Member member;
    private Product product;

}
