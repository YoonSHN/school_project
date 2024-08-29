package com.auction.auction.product.controller;

import com.auction.auction.product.entity.Product;
import com.auction.auction.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/product/list")
    public String list(Model model, @RequestParam(value ="page", defaultValue = "0")int page){
        Page<Product> paging = productService.getList(page);

        model.addAttribute("paging",paging);

        return "/product/list";
    }
    @GetMapping("/product/detail/{id}")
    public String detail_list(@PathVariable("id")Long id,  Model model){
        Product product = productService.getProduct(id);
        model.addAttribute("product", product);

        return "/product/detail";
    }




}
