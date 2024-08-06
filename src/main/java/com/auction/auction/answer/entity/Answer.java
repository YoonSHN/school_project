package com.auction.auction.answer.entity;

import com.auction.auction.market.base.BaseEntity;
import com.auction.auction.member.entity.Member;
import com.auction.auction.question.entity.Inquiry;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Answer extends BaseEntity {
    private String comment;

    @OneToOne
    @JoinColumn(name="item_id")
    private Member member;

    @OneToOne
    @JoinColumn(name="inquiry_id")
    private Inquiry inquiry;
}
