package com.auction.auction.bid.repository;

import com.auction.auction.bid.entity.Bid;
import com.auction.auction.member.entity.Member;
import com.auction.auction.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByMember(Member member);

    @Query("SELECT MAX(b.bidPrice) FROM Bid b WHERE b.product.id = :productId")
    Long findMaxBidPriceByProductId(@Param("productId") Long productId);

    List<Bid> findByProduct(Product product);
}
