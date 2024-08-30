package com.auction.auction.product.repository;

import com.auction.auction.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);

    @Query("""
            select distinct p
            from Product p
            where p.name LIKE %:kw%
            or p.script LIKE %:kw%
            """)
    Page<Product> findAllByKeyWord(@Param("kw")String kw, Pageable pageable);
}
