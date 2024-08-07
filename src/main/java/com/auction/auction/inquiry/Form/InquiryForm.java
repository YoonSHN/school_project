package com.auction.auction.inquiry.Form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class InquiryForm {
    @Size(min = 2, max = 20)
    @NotBlank
    private String title;
    @NotBlank
    private String body;
}
