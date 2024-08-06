package com.auction.auction.product.entity;

import com.auction.auction.market.base.BaseEntity;
import com.auction.auction.market.entity.Market;
import com.auction.auction.question.entity.Inquiry;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Product extends BaseEntity {
    private String name;

    private int price; //현재 경마가
    private int bid_increment; //입찰단가
    private boolean ongoing = false; //경매중인지?
    private String script;
    private int hitCount;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="market_id")
    private Market market;

    @OneToMany(mappedBy="product", cascade = CascadeType.REMOVE)
    private List<Inquiry> inquiryList;

}
