package com.auction.auction.inquiry.controller;

import com.auction.auction.inquiry.Form.InquiryForm;
import com.auction.auction.inquiry.entity.Inquiry;
import com.auction.auction.inquiry.service.InquiryService;
import com.auction.auction.member.entity.Member;
import com.auction.auction.member.service.MemberService;
import com.auction.auction.product.entity.Product;
import com.auction.auction.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;
    private final ProductService productService;
    private final MemberService memberService;

    @GetMapping("/create")
    public String inquiryCreate(@RequestParam("productId") Long productId, Model model){
        Product product = productService.getProduct(productId);
        model.addAttribute("product", product);
        return "/inquiry/create";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable("id")Long id, Model model, Principal principal){
        Inquiry inquiry = inquiryService.getInquiry(id);

        if(!inquiry.getMember().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        model.addAttribute("inquiry",inquiry);
        return "inquiry/modify";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")Long id, Principal principal){
        Inquiry inquiry = inquiryService.getInquiry(id);
        inquiryService.delete(inquiry);

        Long productId = inquiry.getProduct().getId();
        if(!inquiry.getMember().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        return String.format("redirect:/product/detail/%s", productId);
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String submit(@RequestParam("productId") Long productId,
                         Principal principal,
                         @RequestParam("title") String title,
                         @RequestParam("body") String body){

        Product product = productService.getProduct(productId);
        Member member = memberService.findByUserName(principal.getName());
        System.out.println(product.toString());
        inquiryService.create(product, member,title, body);
        return "redirect:/product/detail/" +productId;
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@PathVariable("id") Long id,
                         Principal principal,
                         @RequestParam("id") String content){
        Inquiry inquiry = inquiryService.getInquiry(id);
        inquiryService.modify(inquiry, content);
        Long productId = inquiry.getProduct().getId();
        if(!inquiry.getMember().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        return String.format("redirect:/product/detail/%s", productId);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/check/{id}")
    public String check(@PathVariable("id") Long id, Model model){
        Product product = productService.getProduct(id);
        model.addAttribute("product", product);
        model.addAttribute("productInquiryList", product.getInquiryList());
        return "/inquiry/check";  // 템플릿 파일명
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/check/{id}")
    public String checkPost(@PathVariable("id") Long id){

        return String.format("redirect:/inquiry/check/%s", id);
    }

}
