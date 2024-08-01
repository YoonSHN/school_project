package com.auction.auction.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private int price; //현재 경마가
    private int bid_increment; //입찰단가
    private boolean ongoing = false; //경매중인지?
    @CreatedDate
    private LocalDateTime createDate; //경매시작일
    @LastModifiedDate
    private LocalDateTime endDate;  //경매종료일

    public void startAuction() {
        this.ongoing = true;
    }

    public void endAuction() {
        this.ongoing = false;
    }
}
