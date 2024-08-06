package com.auction.auction.product.entity;

import com.auction.auction.market.base.BaseEntity;
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
public class Product extends BaseEntity {
    private String name;

    private int price; //현재 경마가
    private int bid_increment; //입찰단가
    private boolean ongoing = false; //경매중인지?


}
