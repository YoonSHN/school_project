package com.auction.auction.bid.service;

import com.auction.auction.bid.entity.Bid;
import com.auction.auction.bid.repository.BidRepository;
import com.auction.auction.member.entity.Member;
import com.auction.auction.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BidService {
    private final BidRepository bidRepository;

    public void bid(Bid bid, Member member, Product product){
        bid.setProduct(product);
        bid.setMember(member);

        bidRepository.save(bid);
    }
    public Long findMaxBidPriceByProductId(Long productId) {
        return bidRepository.findMaxBidPriceByProductId(productId);
    }
    public List<Bid> findAllBids() {
        return bidRepository.findAll(); // 또는 필요한 로직으로 모든 입찰 기록을 가져옵니다.
    }
    public List<Bid> findProductAllBids(Product product){
        return bidRepository.findByProduct(product);
    }
    public List<Bid> findAllByMember(Member member){
        return bidRepository.findByMember(member);
    }
}
