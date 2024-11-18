package com.auction.auction.kakao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final KakaoPayService kakaoPayService;

    // 카카오페이 결제 준비
    @PostMapping("/pay/ready")
    public @ResponseBody ReadyResponse payReady(@RequestBody Map<String, Object> paymentInfo) {
        String name = (String) paymentInfo.get("name");
        Integer totalPrice = (Integer) paymentInfo.get("totalPrice");

        // 결제 준비 요청
        ReadyResponse readyResponse = kakaoPayService.payReady(name, totalPrice);

        // 세션에 결제 고유번호(tid) 저장
        SessionUtils.addAttribute("tid", readyResponse.getTid());
        log.info("결제 고유번호: " + readyResponse.getTid());

        return readyResponse;
    }

    // 결제 승인 완료 처리
    @GetMapping("/pay/completed")
    public String payCompleted(@RequestParam("pg_token") String pgToken) {
        String tid = (String) SessionUtils.getAttribute("tid");

        // 카카오페이 결제 승인 요청
        ApproveResponse approvalResponse = kakaoPayService.payApprove(tid, pgToken);


        // 결제 완료 후 처리 (예: 주문 생성)
        // 주문 처리 로직을 여기에 추가

        return "결제 완료";
    }
}