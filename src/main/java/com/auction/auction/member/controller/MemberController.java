package com.auction.auction.member.controller;

import com.auction.auction.member.entity.Member;
import com.auction.auction.member.form.MemberForm;
import com.auction.auction.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(){
        return "/member/login";
    }
    @GetMapping("/join")
    public String showJoin(){
        return "/member/join";
    }
    @PostMapping("/join")
    public String join(@Valid MemberForm memberForm){
        memberService.join(memberForm.getUsername(), memberForm.getPassword(),memberForm.getEmail());
        return "redirect:/member/login";
    }

    /* 마이페이지 */
    @GetMapping("/myPage")
    public String myPage(Principal principal, Model model){
        String username = principal.getName();
        Member member = memberService.findByUserName(username);
        model.addAttribute("member", member);

        return "/member/myPage";
    }
    @PostMapping("/myPage")
    public String myPageModify(@ModelAttribute Member member, Principal principal) {
        String username = principal.getName();
        Member existingMember = memberService.findByUserName(username);

        existingMember.setPassword(passwordEncoder.encode(member.getPassword()));
        existingMember.setEmail(member.getEmail());
        existingMember.setBalance(member.getBalance());

        memberService.save(existingMember);

        return "redirect:/";
    }




}
