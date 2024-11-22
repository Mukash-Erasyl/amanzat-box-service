package org.example.task_07_train.service;

import org.example.task_07_train.domain.PaymentsDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface PaymentService {
    Flux<PaymentsDTO> countPaymentByGoal();

    Flux<PaymentsDTO> findCreditAndPaymentIdsByReceivedDateRange(LocalDateTime startDate, LocalDateTime endDate);

    Mono<Integer> countOfPaymentsInSelectedSource(String paymentSource);

    Flux<PaymentsDTO> countPaymentsGroupedBySourceAndGoal();
}
