package com.auction.auction.market.entity;

import com.auction.auction.market.base.BaseEntity;
import com.auction.auction.member.entity.Member;
import com.auction.auction.product.entity.Product;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Market extends BaseEntity {
    private String body;
    private String info;
    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy="market", cascade = CascadeType.REMOVE)
    private List<Product> productList;

}
