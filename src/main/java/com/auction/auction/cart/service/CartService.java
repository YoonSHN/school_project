package com.auction.auction.cart.service;

import com.auction.auction.cart.entity.Cart;
import com.auction.auction.cart.repository.CartRepository;
import com.auction.auction.member.entity.Member;
import com.auction.auction.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;


    public void add(Product product, Member member) {
        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setMember(member);

        cart.setCreateDate(LocalDateTime.now());

        cartRepository.save(cart);
    }

    public List<Cart> getList(Member member) {
        return cartRepository.findByMember(member);
    }
}
