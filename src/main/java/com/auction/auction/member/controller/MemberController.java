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

    // 로그인 요청을 처리하는 메서드 추가
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model) {

        try {
            // 사용자 이름을 이용해 회원 조회
            Member member = memberService.findByUserName(username);

            // 비밀번호 비교
            if (passwordEncoder.matches(password, member.getPassword())) {
                // 비밀번호가 일치하면 로그인 처리 (예: 세션에 사용자 정보 저장)
                // 세션에 회원 정보 저장 등 로그인 처리 로직을 추가하세요.
                return "redirect:/"; // 로그인 성공 후 이동할 페이지
            } else {
                model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
                return "/member/login"; // 로그인 실패 시 로그인 페이지로 돌아가도록
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "사용자를 찾을 수 없습니다.");
            return "/member/login"; // 사용자 없을 시 로그인 페이지로 돌아가도록
        }
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

        existingMember.setEmail(member.getEmail());
        existingMember.setBalance(member.getBalance());

        memberService.save(existingMember);

        return "redirect:/";
    }




}
