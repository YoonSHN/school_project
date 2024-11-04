package com.auction.auction.member.service;

import com.auction.auction.member.entity.Member;
import com.auction.auction.member.repository.MemberRepository;
import com.auction.auction.product.entity.Product;
import com.auction.auction.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member join(String username, String password, String email) {
        Member member = new Member();
        member.setUsername(username);
        member.setPassword(passwordEncoder.encode(password));
        member.setEmail(email);
        member.setBalance(0L);
        member.setCreateDate(LocalDateTime.now());

        memberRepository.save(member);

        return member;
    }

    public Member findByUserName(String name) {

        Optional<Member> member = memberRepository.findByUsername(name);
        if(!member.isEmpty()){
            return member.get();
        }else{
            throw new RuntimeException("member not found");
        }
    }
    public Member findById(Long id){
        Optional<Member> member = memberRepository.findById(id);
        if(!member.isEmpty()){
            return member.get();
        }
        else{
            throw new RuntimeException("member not found");
        }
    }
    public List<Member> findAll(){
        return memberRepository.findAll();
    }
    public void save(Member member){
        memberRepository.save(member);
    }
}
