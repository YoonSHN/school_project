package com.auction.auction.product.controller;

import com.auction.auction.adm.service.AdmService;
import com.auction.auction.bid.service.BidService;
import com.auction.auction.member.entity.Member;
import com.auction.auction.member.service.MemberService;
import com.auction.auction.product.entity.Product;
import com.auction.auction.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import static java.time.LocalTime.now;


@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final MemberService memberService;
    private final BidService bidService;
    private final AdmService admService;

    @GetMapping("/product/list")
    public String list(Model model, @RequestParam(value ="page", defaultValue = "0")int page,
                       @RequestParam(value="kw", defaultValue = "") String kw, Principal principal){
        Page<Product> paging = productService.getList(page,kw);
        if(principal!= null) {
            String username = principal.getName();
            Member member = memberService.findByUserName(username);
            model.addAttribute("member" ,member);
        }
        model.addAttribute("paging",paging);
        model.addAttribute("kw",kw);

        return "/product/list";
    }
    @GetMapping("/product/detail/{id}")
    public String detail_list(@PathVariable("id")Long id,  Model model, Principal principal){
        Product product = productService.getProduct(id);

        if(product.getPrice() > product.getInstantPrice()){
            product.setOngoing(false);
            // 현재 LocalDateTime을 가져옵니다.
            LocalDateTime now = LocalDateTime.now();

            ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
            Date currentDate = Date.from(zonedDateTime.toInstant());

            product.setAuctionEndDate(currentDate);
        }
        String username = product.getSeller();
        Member member = memberService.findByUserName(username);
        model.addAttribute("member",member);
        model.addAttribute("product", product);

        return "/product/detail";
    }

    @GetMapping("/product/upload")
    public String uploadProduct(Model model){
        model.addAttribute("product", new Product());
        return "/product/upload";
    }
    @PostMapping("/product/upload")
    public String uploadProduct(@RequestParam("name") String name,
                                @RequestParam("startPrice") int startPrice,
                                @RequestParam("instantPrice") int instantPrice,
                                @RequestParam("bid_increment") String bid_increment,
                                @RequestParam("description") String description,
                                @RequestParam("auctionEndDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH") Date auctionEndDate,
                                @RequestParam("video") MultipartFile video,
                                Principal principal) {

        // 새로운 Product 객체 생성
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setAuctionEndDate(auctionEndDate);
        product.setInstantPrice(instantPrice);
        product.setStartPrice(startPrice);
        product.setApprovedStatus("PENDING");
        product.setSeller(principal.getName());

        // 경매 진행 여부 설정
        product.    setOngoing(product.getAuctionEndDate().after(new Date()));
        product.setPrice(startPrice);
        product.setBid_increment(Integer.parseInt(bid_increment));

        // 파일 저장 및 경로 설정
        String originalVideoPath = saveFile(video, "/Users/yunseunghyeon/Study/Videos"); // 사용자 지정 경로로 수정

        // Product 객체에 동영상 경로 저장
        if (originalVideoPath != null) {
            product.setVideoPath("/Videos/" + video.getOriginalFilename());
        } else {
            return "redirect:/error";
        }

        // Product 저장
        productService.saveProduct(product);
        admService.saveProduct(product);

        return "redirect:/";
    }


    private String saveFile(MultipartFile file, String directory) {
        if (file.isEmpty()) {
            return null; // 파일이 비어있으면 null 반환
        }

        // 동영상 파일인지 확인 (예: MP4만 허용)
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("video/")) {
            return null; // 동영상 파일이 아니면 null 반환
        }

        try {
            // 파일 저장 경로
            String fileName = file.getOriginalFilename();
            String filePath = directory + "/" + fileName;
            File destinationFile = new File(filePath);
            file.transferTo(destinationFile); // 파일 저장

            // 웹에서 접근 가능한 경로로 반환
            return filePath;
        } catch (IOException e) {
            return null; // 예외 발생 시 null 반환
        }
    }

    public void purchase(Product product, Principal principal) {


    }


}
