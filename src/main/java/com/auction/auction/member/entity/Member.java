package com.auction.auction.member.entity;

import com.auction.auction.market.base.BaseEntity;
import com.auction.auction.question.entity.Inquiry;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Member extends BaseEntity {
    private String username;
    private String password;
    private String email;

    @OneToMany(mappedBy="member",cascade= CascadeType.REMOVE)
    private List<Inquiry> inquiryList;
}
