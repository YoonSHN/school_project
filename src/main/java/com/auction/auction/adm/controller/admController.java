package com.auction.auction.adm.controller;

import com.auction.auction.member.entity.Member;
import com.auction.auction.member.service.MemberService;
import com.auction.auction.product.entity.Product;
import com.auction.auction.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/adm")
@RequiredArgsConstructor
public class admController {

    private final MemberService memberService;
    private final ProductService productService;


    @GetMapping("/admin")
    public String admPage(){
        return "adm/admin";
    }
    @GetMapping("/members") //회원목록
    public String membersPage(Model model){

        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "adm/members";
    }
    @GetMapping("/pending")
    public String pendingPage(Model model){
        List<Product> products = productService.setProductApproved();
        model.addAttribute("products", products);
        return "adm/pending";
    }
    @PostMapping("/pending")
    public String approveOrReject(@RequestParam("productId") Long productId,
                                  @RequestParam("action") String action) {
        // 상품 가져오기
        Product product = productService.getProduct(productId);

        // null 체크
        if (product == null) {
            // 적절한 예외 처리 또는 리다이렉트 처리
            return "redirect:/adm/pending"; // 예시: 적절한 오류 메시지 페이지로 리다이렉트
        }

        // 승인 대기 상태일 때만 처리
        if ("PENDING".equals(product.getApprovedStatus())) {
            if ("approve".equals(action)) {
                product.setApprovedStatus("APPROVED");
            } else if ("reject".equals(action)) {
                product.setApprovedStatus("REJECTED");
            }

            // 상태 변경 후 저장
            productService.saveProduct(product);
        }

        // 처리 후 적절한 리다이렉트
        return "redirect:/adm/pending"; // 승인 대기 페이지로 리다이렉트
    }

}
