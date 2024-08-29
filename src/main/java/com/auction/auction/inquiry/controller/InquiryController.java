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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable("id")Long id, Model model){
        Inquiry inquiry = inquiryService.getInquiry(id);
        model.addAttribute("inquiry",inquiry);
        return "inquiry/modify";
    }

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
    @PostMapping("/modify/{id}")
    public String modify(@PathVariable("id") Long id,
                         Principal principal,
                         @RequestParam("id") String content){
        Inquiry inquiry = inquiryService.getInquiry(id);
        inquiryService.modify(inquiry, content);
        Long productId = inquiry.getProduct().getId();

        return String.format("redirect:/product/detail/%s", productId);


    }
}
