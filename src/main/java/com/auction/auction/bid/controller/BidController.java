package com.auction.auction.bid.controller;

import com.auction.auction.bid.entity.Bid;
import com.auction.auction.bid.service.BidService;
import com.auction.auction.member.entity.Member;
import com.auction.auction.member.service.MemberService;
import com.auction.auction.product.entity.Product;
import com.auction.auction.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bid")
public class BidController {
    private final BidService bidService;
    private final MemberService memberService;
    private final ProductService productService;

    @GetMapping("/instant")
    public String instantBuy() {
        return "/bid/instant";
    }

    @PostMapping("/instant")
    public String instantBuyPost(@RequestParam("productId") Long productId, Model model, Principal principal) {
        String username = principal.getName();
        Member member = memberService.findByUserName(username);
        model.addAttribute("member", member);

        Product product = productService.getProduct(productId);
        model.addAttribute("product", product);

        return "/bid/instant";
    }

    @GetMapping("/bidding")
    public String showRecord(@RequestParam("productId") Long productId, Model model) {
        Product product = productService.getProduct(productId);
        List<Bid> bidList = bidService.findProductAllBids(product); // 모든 입찰 기록을 가져옵니다.
        model.addAttribute("bidList", bidList); // 모델에 추가합니다.
        return "/bid/bidding"; // 뷰를 반환합니다.
    }


    @PostMapping("/bidding")
    public String bidding(@RequestParam("productId") Long productId,
                          @RequestParam("bidAmount") Long bidAmount,
                          Model model, Principal principal) {
        String username = principal.getName();
        Member member = memberService.findByUserName(username);

        // 상품 조회
        Product product = productService.getProduct(productId);

        if (product == null) {
            model.addAttribute("errorMessage", "해당 상품을 찾을 수 없습니다.");
            return "redirect:/product/list";
        }

        // 현재 상품의 최대 입찰가 조회
        Long maxBidPrice = bidService.findMaxBidPriceByProductId(productId);

        // 입찰가 검증
        if (maxBidPrice != null && bidAmount <= maxBidPrice) {
            model.addAttribute("errorMessage", "입찰 금액은 현재 최고 입찰가보다 높아야 합니다.");
            model.addAttribute("product", product);
            model.addAttribute("member", member);
            return "/product/detail";
        }
        else if((bidAmount < product.getStartPrice())) {
            model.addAttribute("errorMessage", "입찰 금액은 시작가보다 높아야 합니다.");
            model.addAttribute("product", product);
            model.addAttribute("member", member);
            return "/product/detail";
        }
        else if ((bidAmount - product.getStartPrice()) % product.getBid_increment() != 0){
            model.addAttribute("errorMessage", "입찰 단가에 맞춰 입찰하셔야 합니다.");
            model.addAttribute("product", product);
            model.addAttribute("member", member);
            return "/product/detail";
        }
        else if (bidAmount > member.getBalance()) {
            model.addAttribute("errorMessage", "잔액이 부족합니다.");
            model.addAttribute("product",product);
            model.addAttribute("member", member);
            return "/product/detail"; // 잔액 부족 시 실패 페이지로 리다이렉트
        }

        if (bidAmount > product.getInstantPrice()) {
            member.setBalance(member.getBalance() - bidAmount);
            memberService.save(member);  // 회원 정보 업데이트

            // 상품 구매 완료 처리 로직 추가
            product.setOngoing(false);
            productService.saveProduct(product);

            // 즉시 구매한 상품을 입찰 기록에 추가
            Bid bid = new Bid();
            bid.setProduct(product);
            bid.setMember(member);
            bid.setBidPrice(bidAmount);

            bidService.save(bid);

            member.getBidList().add(bid); // 사용자의 입찰 기록에 추가
            memberService.save(member);

            return "redirect:/bid/success";  // 결제 성공 페이지로 리다이렉트
        }

        // 일반 입찰 처리 (즉시 구매가 아닌 경우)
        product.setPrice(bidAmount.intValue());

        // 입찰 처리
        Bid bid = new Bid();
        bid.setBidPrice(bidAmount);
        bid.setBidTime(LocalDateTime.now());
        bidService.bid(bid, member, product);

        model.addAttribute("product", product); // 상품 정보 추가
        model.addAttribute("member", member);

        return "redirect:/product/detail/" + productId; // 성공 후 리다이렉트
    }



    @PostMapping("/purchase")  // 즉시구매
    public String purchaseProduct(@RequestParam("productId") Long productId,
                                  @RequestParam("purchaseAmount") Long purchaseAmount,
                                  Principal principal, Model model) {
        String username = principal.getName();
        Member member = memberService.findByUserName(username);

        // 상품 조회
        Product product = productService.getProduct(productId);

        // 결제 로직: 잔액에서 차감
        if (member.getBalance() >= purchaseAmount) {
            member.setBalance(member.getBalance() - purchaseAmount);
            memberService.save(member);  // 회원 정보 업데이트

            // 상품 구매 완료 처리 로직 추가 (필요 시)
            product.setOngoing(false);
            productService.saveProduct(product);

            // 즉시 구매한 상품을 입찰 기록에 추가
            Bid bid = new Bid();
            bid.setProduct(product);
            bid.setMember(member);
            bid.setBidPrice(purchaseAmount);// 즉시 구매 금액으로 설정

            bidService.save(bid);

            member.getBidList().add(bid); // 사용자의 입찰 기록에 추가
            memberService.save(member);

            return "redirect:/bid/success";  // 결제 성공 페이지로 리다이렉트
        } else {
            model.addAttribute("errorMessage", "잔액이 부족합니다.");
            return "redirect:/bid/failure";  // 결제 실패 처리
        }
    }

    @GetMapping("/success")
    public String success(){
        return "/bid/success";
    }
    @GetMapping("/failure")
    public String failure(Model model){
        return "/bid/failure";
    }
    @GetMapping("/list")
    public String showCart(Principal principal, Model model) {
        String username = principal.getName();
        Member member = memberService.findByUserName(username);

        // 전체 입찰 기록을 가져오기
        List<Bid> bids = bidService.findAllByMember(member);

        model.addAttribute("member", member);
        model.addAttribute("bidList", bids != null ? bids : new ArrayList<>());
        return "bid/list";  // 입찰 기록 페이지 템플릿 경로
    }


}
