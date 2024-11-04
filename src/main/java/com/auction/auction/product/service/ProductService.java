package com.auction.auction.product.service;

import com.auction.auction.product.entity.Product;
import com.auction.auction.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<Product> getList(int page, String kw){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 8, Sort.by(sorts));
        return productRepository.findAllByKeyWord(kw, pageable);
    }

    public void create(String name, int price) {
        Product p = new Product();
        p.setName(name);
        p.setPrice(price);
        p.setCreateDate(LocalDateTime.now());
        productRepository.save(p);
    }
    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void updateAuctionStatus() {
        List<Product> products = productRepository.findAll();
        Date now = new Date();

        for (Product product : products) {
            if (product.getAuctionEndDate().after(now)) {
                product.setOngoing(true);
            } else {
                product.setOngoing(false);
            }
        }

        productRepository.saveAll(products); // 상태 업데이트 후 저장
    }

    public Product getProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()) {
            return product.get();
        }else{
            throw new RuntimeException("product not found");
        }
    }
    public void saveProduct(Product product) {
        productRepository.save(product);
    }
    public List<Product> setProductApproved(){
        List<Product> productList = productRepository.findAll();
        return productList;
    }

    public void remove(Product product) {
        productRepository.delete(product);
    }
}
