package com.auction.auction.bid.service;

import com.auction.auction.bid.entity.Bid;
import com.auction.auction.bid.repository.BidRepository;
import com.auction.auction.member.entity.Member;
import com.auction.auction.product.entity.Product;
import com.auction.auction.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BidService {
    private final BidRepository bidRepository;
    private final ProductRepository productRepository;

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

    public void save(Bid bid) {
        // 경매가 종료되었을 때 상태 업데이트
        if (bid.getProduct() != null && !bid.getProduct().isOngoing()) {
            bid.updateStatus();  // 상태 업데이트
        }

        // 다운로드 링크 설정 (경매가 종료되고 다운로드 링크가 필요할 경우)
        if (bid.getProduct() != null && !bid.getProduct().isOngoing() && bid.getProduct().getVideoPath() != null) {
            // 파일명만 추출
            String filePath = bid.getProduct().getVideoPath();  // 예: "/Users/yunseunghyeon/Study/Videos/Download1.mp4"
            int lastSlashIndex = filePath.lastIndexOf('/');
            String fileName = filePath.substring(lastSlashIndex + 1);  // 파일명 추출: "Download1.mp4"

            // 다운로드 링크 설정
            String downloadLink = "/download?filename=" + fileName;
            bid.setDownloadLink(downloadLink);
        }

        bidRepository.save(bid);
    }

    public boolean isBidSuccessful(Bid bid) {
        Product product = bid.getProduct();

        // 즉시 구매 조건: 입찰 금액이 즉시 구매 가격 이상일 때
        if (product.getPrice() == product.getInstantPrice() && bid.getBidPrice() >= product.getInstantPrice()) {
            return true;  // 즉시 구매가 완료되면 입찰 성공
        }
        // 경매 종료 조건: 경매가 종료되었고, 해당 입찰자가 가장 높은 입찰자일 때
        if (!product.isOngoing() && bid.getBidPrice() >= product.getHighestBidPrice()) {
            return true;  // 경매 종료 후, 가장 높은 입찰 금액이 입찰 성공
        }

        return false;  // 다른 경우는 실패
    }
    // 경매 종료 후 입찰 결과 업데이트
    @Scheduled(fixedDelay = 120000) // 1분마다 실행
    public void updateBidStatusForEndedAuctions() {
        List<Product> endedAuctions = productRepository.findByAuctionEndDateBeforeAndOngoingFalse(LocalDateTime.now());
        List<Product> readAuctions = productRepository.findByOngoingFalse();
        for (Product product : endedAuctions) {
            // 경매 종료된 상품에 대해 상태 업데이트
            List<Bid> bids = bidRepository.findByProduct(product);
            for (Bid bid : bids) {
                bid.updateStatus(); // Bid 상태 업데이트
            }
            product.setOngoing(false); // 경매 종료 처리
            productRepository.save(product);
        }
    }

}
