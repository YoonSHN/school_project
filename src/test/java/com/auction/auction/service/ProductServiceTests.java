package com.auction.auction.service;

import com.auction.auction.member.entity.Member;
import com.auction.auction.member.service.MemberService;
import com.auction.auction.product.entity.Product;
import com.auction.auction.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@SpringBootTest
public class ProductServiceTests {
    @Autowired
    private ProductService productService;
    @Autowired
    private MemberService memberService;
    @Test
    @DisplayName("제품 생성")
    void test1(){
        Member member = new Member();
        memberService.join("판매자","0000","sample@email.com");
        for(int i = 1;i <= 8 ;++i){
            String name = String.format("테스트 상품 :[%03d]", i);
            Product product = new Product();
            product.setName(name);
            product.setPrice(10000);
            product.setBid_increment(5000);
            product.setStartPrice(10000);
            product.setInstantPrice(50000);
            product.setBuyer("구매자");
            product.setSeller("판매자");
            product.setApprovedStatus("PENDING");
            product.setVideoPath("/Videos/Download" + i  + ".mp4"); // 수정된 경로
            product.setDescription("테스트 상품입니다." + i); ;

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime auctionEndDateTime = now.plusDays(1); // 하루 추가

            ZonedDateTime zonedDateTime = auctionEndDateTime.atZone(ZoneId.systemDefault());

            Date auctionEndDate = Date.from(zonedDateTime.toInstant());

            System.out.println("Current Date: " + now);
            System.out.println("Auction End Date (after adding one day): " + auctionEndDate);

            product.setAuctionEndDate(auctionEndDate);
            product.setOngoing(true);
            productService.create(product);
        }
    }
}
