package com.auction.auction.member.controller;

import com.auction.auction.member.form.MemberForm;
import com.auction.auction.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    @GetMapping("/login")
    public String login(){
        return "/member/login";
    }


    @GetMapping("/join")
    public String showjoin(){
        return "/member/join";
    }
    @PostMapping("/join")
    public String join(@Valid MemberForm memberForm){
        memberService.join(memberForm.getUsername(), memberForm.getPassword(),memberForm.getEmail());

        return "redirect:/member/login";
    }

}
