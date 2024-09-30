package com.auction.auction.home;

import com.auction.auction.member.entity.Member;
import com.auction.auction.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    @GetMapping("/")
    public String index() {
        return "/home/main";

    }

}
