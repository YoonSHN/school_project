package com.auction.auction.member.service;

import com.auction.auction.member.entity.Member;
import com.auction.auction.member.repository.MemberRepository;
import com.auction.auction.product.entity.Product;
import com.auction.auction.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member join(String username, String password, String email) {
        Member member = new Member();
        member.setUsername(username);
        member.setPassword(password);
        member.setEmail(email);
        member.setCreateDate(LocalDateTime.now());

        memberRepository.save(member);

        return member;
    }
}
