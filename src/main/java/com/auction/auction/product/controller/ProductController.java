package com.auction.auction.product.controller;

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
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final MemberService memberService;

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
        // Product에서 scriptFilePath 가져오기
        String scriptFilePath = product.getScriptFilePath();

        String username = principal.getName();
        Member member = memberService.findByUserName(username);
        model.addAttribute("member",member);
        model.addAttribute("product", product);
        model.addAttribute("scriptFilePath", scriptFilePath);

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
                                @RequestParam("auctionEndDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date auctionEndDate,
                                @RequestParam("image") MultipartFile image,
                                @RequestParam("scriptFile") MultipartFile scriptFile) {

        // 새로운 Product 객체 생성
        Product product = new Product();
        product.setName(name);

        product.setDescription(description);
        product.setAuctionEndDate(auctionEndDate);
        product.setInstantPrice(instantPrice);
        product.setStartPrice(startPrice);
        product.setPrice(startPrice);
        product.setBid_increment(Integer.parseInt(bid_increment));
        // 파일 저장 및 경로 설정
        String imagePath = saveFile(image,"/yunseunghyeon/Study/pictures"); // 이미지 저장 메서드
        String scriptFilePath = saveFile(scriptFile, "/yunseunghyeon/Study/pictures"); // 스크립트 파일 저장 메서드

        // Product 객체에 경로 저장
        product.setImagePath(imagePath);
        product.setScriptFilePath(scriptFilePath);

        // Product 저장
        productService.saveProduct(product);

        return "redirect:/product/list"; // 등록 후 목록 페이지로 리다이렉트
    }

    private String saveFile(MultipartFile file, String directory) {
        if (file.isEmpty()) {
            return null; // 파일이 비어있으면 null 반환
        }

        try {
            // 파일 저장 경로
            String filePath = directory + "/" + file.getOriginalFilename();
            File destinationFile = new File(filePath);
            file.transferTo(destinationFile); // 파일 저장

            return filePath; // 저장된 파일 경로 반환
        } catch (IOException e) {
            return null; // 예외 발생 시 null 반환
        }
    }





}
