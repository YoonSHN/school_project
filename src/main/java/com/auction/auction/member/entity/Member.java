package com.auction.auction.member.entity;

import com.auction.auction.cart.controller.CartController;
import com.auction.auction.cart.entity.Cart;
import com.auction.auction.market.base.BaseEntity;
import com.auction.auction.inquiry.entity.Inquiry;
import com.auction.auction.product.entity.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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

    @OneToMany(cascade= CascadeType.REMOVE)
    private List<Product> productList;

    @OneToMany(mappedBy="member",cascade= CascadeType.REMOVE)
    private List<Inquiry> inquiryList;

    @OneToMany(mappedBy="member" , cascade = CascadeType.REMOVE)
    private List<Cart> cartList = new ArrayList<>();


}
