package com.auction.auction.inquiry.service;

import com.auction.auction.inquiry.entity.Inquiry;
import com.auction.auction.inquiry.repository.InquiryRepository;
import com.auction.auction.member.entity.Member;
import com.auction.auction.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    public void create(Product product, Member member, String title, String body) {
        Inquiry inquiry = new Inquiry();
        inquiry.setProduct(product);
        inquiry.setMember(member);
        inquiry.setBody(body);
        inquiry.setTitle(title);
        inquiry.setCreateDate(LocalDateTime.now());

        inquiryRepository.save(inquiry);
    }
}
