package com.auction.auction.inquiry.service;

import com.auction.auction.inquiry.entity.Inquiry;
import com.auction.auction.inquiry.repository.InquiryRepository;
import com.auction.auction.member.entity.Member;
import com.auction.auction.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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

    public Inquiry getInquiry(Long id) {
        Optional<Inquiry> inquiry = inquiryRepository.findById(id);
        if(inquiry.isPresent()){
            return inquiry.get();
        }
        else{
            throw new RuntimeException("question not found");
        }
    }

    public void modify(Inquiry inquiry, String content) {
        inquiry.setTitle(content);
        inquiry.setModifyDate(LocalDateTime.now());

        inquiryRepository.save(inquiry);
    }

    public void delete(Inquiry inquiry) {
        inquiryRepository.delete(inquiry);
    }
}
