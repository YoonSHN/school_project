package com.auction.auction.answer.entity;

import com.auction.auction.market.base.BaseEntity;
import com.auction.auction.member.entity.Member;
import com.auction.auction.question.entity.Question;
import jakarta.persistence.Entity;

@Entity
public class Answer extends BaseEntity {
    private String comment;
    private Member member;
    private Question question;
}
