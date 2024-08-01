package com.auction.auction.product.controller;

import com.auction.auction.product.entity.Product;
import com.auction.auction.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    public String list(Model model){
        List<Product> productList = productService.getList();
        model.addAttribute("productList",productList);
        return "product/list";
    }
    @GetMapping("/detail/{id}")
    public String list(@PathVariable("id")Long id,  Model model){

        return "product/detail";
    }


}
