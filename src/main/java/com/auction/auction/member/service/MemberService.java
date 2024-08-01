package com.auction.auction.member.service;

import com.auction.auction.member.entity.Member;
import com.auction.auction.product.entity.Product;
import com.auction.auction.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final ProductRepository productRepository;

    public List<Product> getList(){
        return productRepository.findAll();
    }

    public void create(String name, int price) {
        Member m = new Member();
        m.setUsername("윤승현");
        m.setPassword("1234");
    }
}
