package com.auction.auction.inquiry.controller;

import com.auction.auction.inquiry.Form.InquiryForm;
import com.auction.auction.inquiry.entity.Inquiry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    private InquiryService inquiryService;

    @GetMapping("/create")
    public String create(Model model){
        return "/inquiry/create";
    }
    @PostMapping("/create")
    public String create_post(@Valid InquiryForm form){
        Inquiry inquiry = new Inquiry();

        return "redirect:/inquiry/check";
    }
}
