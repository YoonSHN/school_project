package com.auction.auction.product.repository;

import com.auction.auction.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);

    @Query("""
        select distinct p
        from Product p
        where (p.approvedStatus = 'APPROVED') and (p.name LIKE %:kw% or p.script LIKE %:kw%) and (p.ongoing = true)
        """)
    Page<Product> findAllByKeyWord(@Param("kw") String kw, Pageable pageable);


    List<Product> findByAuctionEndDateBeforeAndOngoingFalse(LocalDateTime now);
    List<Product> findByOngoingFalse();
}
