package com.auction.auction.service;

import com.auction.auction.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTests {
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원가입")
    public void join(){
        String username = "user1";
        String password = "user1";
        String email = "user1@test.com";
        memberService.join(username, password, email);
    }
}
