package com.auction.auction.product.entity;

import com.auction.auction.bid.entity.Bid;
import com.auction.auction.cart.entity.Cart;
import com.auction.auction.market.base.BaseEntity;
import com.auction.auction.market.entity.Market;
import com.auction.auction.inquiry.entity.Inquiry;
import com.auction.auction.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Product extends BaseEntity {
    private String name;

    private int price; //현재 경마가
    private int bid_increment; //입찰단가
    private boolean ongoing = false;//경매중인지?
    private String description;
    private String script;
    private int startPrice;
    private int instantPrice;
    private String buyer;

    @Temporal(TemporalType.TIMESTAMP)
    private Date auctionEndDate;

    private String imagePath;        // 이미지 파일 경로
    private String scriptFilePath;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="market_id")
    private Market market;

    @OneToMany(mappedBy="product", cascade = CascadeType.REMOVE)
    private List<Inquiry> inquiryList;

    @OneToMany(mappedBy="product" , cascade = CascadeType.REMOVE)
    private List<Cart> cartList = new ArrayList<>();

    @OneToMany(mappedBy= "product", cascade = CascadeType.REMOVE)
    private List<Bid> bidList = new ArrayList<>();
}
