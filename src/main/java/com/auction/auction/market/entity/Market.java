package com.auction.auction.market.entity;

import com.auction.auction.market.base.BaseEntity;
import com.auction.auction.member.entity.Member;
import com.auction.auction.product.entity.Product;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Market extends BaseEntity {
    private String email;
    private String body;
    private String info;
    private Member member;

    private List<Product> productList;

}
