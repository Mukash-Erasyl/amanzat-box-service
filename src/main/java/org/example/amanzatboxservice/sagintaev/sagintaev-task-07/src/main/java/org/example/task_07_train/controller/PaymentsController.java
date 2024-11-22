package org.example.task_07_train.controller;

import org.example.task_07_train.domain.PaymentsDTO;
import org.example.task_07_train.service.DefaultPaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/payments")
public class PaymentsController {
    private final DefaultPaymentService paymentService;

    public PaymentsController(DefaultPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/goal/count")
    public ResponseEntity<Flux<PaymentsDTO>> countPaymentByGoal() {
        return ResponseEntity.ok(paymentService.countPaymentByGoal());
    }

    @GetMapping("/by-credit-and-date/{startDate}/{endDate}")
    public ResponseEntity<Flux<PaymentsDTO>> findCreditAndPaymentIdsByReceivedDateRange(@PathVariable LocalDateTime startDate, @PathVariable LocalDateTime endDate){
        return ResponseEntity.ok(paymentService.findCreditAndPaymentIdsByReceivedDateRange(startDate, endDate));
    }

    @GetMapping("/count/source/{paymentSource}")
    public ResponseEntity<Mono<Integer>> countOfPaymentsInSelectedSource(@PathVariable String paymentSource) {
        return ResponseEntity.ok(paymentService.countOfPaymentsInSelectedSource(paymentSource));
    }

    @GetMapping("/source-goal/count")
    public ResponseEntity<Flux<PaymentsDTO>> countPaymentsGroupedBySourceAndGoal(){
        return ResponseEntity.ok(paymentService.countPaymentsGroupedBySourceAndGoal());
    }
}
