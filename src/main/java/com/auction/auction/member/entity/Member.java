package com.auction.auction.member.entity;

import com.auction.auction.bid.entity.Bid;
import com.auction.auction.cart.controller.CartController;
import com.auction.auction.cart.entity.Cart;
import com.auction.auction.market.base.BaseEntity;
import com.auction.auction.inquiry.entity.Inquiry;
import com.auction.auction.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member extends BaseEntity {
    private String username;
    private String password;
    private String email;
    private Long balance;
    @OneToMany(cascade= CascadeType.REMOVE)
    private List<Product> productList = new ArrayList<>();

    @OneToMany(mappedBy="member",cascade= CascadeType.REMOVE)
    private List<Inquiry> inquiryList = new ArrayList<>();

    @OneToMany(mappedBy="member" , cascade = CascadeType.REMOVE)
    private List<Cart> cartList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Bid> bidList = new ArrayList<>(); // 하나의 회원이 여러 개의 입찰을 가질 수 있음
}
