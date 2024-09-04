package com.auction.auction.cart.controller;

import com.auction.auction.cart.entity.Cart;
import com.auction.auction.cart.service.CartService;
import com.auction.auction.member.entity.Member;
import com.auction.auction.member.service.MemberService;
import com.auction.auction.product.entity.Product;
import com.auction.auction.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final MemberService memberService;
    private final ProductService productService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public String list(Principal principal, Model model)
    {
        Member member = memberService.findByUserName(principal.getName());
        List<Cart> cartList = cartService.getList(member);

        model.addAttribute("cartList", cartList);
        return "cart/list";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add/{id}")
    public String add(@PathVariable("id") Long id, Principal principal){
        Product product = productService.getProduct(id);
        Member member = memberService.findByUserName(principal.getName());

        cartService.add(product, member);
        return "redirect:/cart/list";
    }


}
