package com.auction.auction.cart.repository;

import com.auction.auction.cart.entity.Cart;
import com.auction.auction.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByMember(Member member);
}
