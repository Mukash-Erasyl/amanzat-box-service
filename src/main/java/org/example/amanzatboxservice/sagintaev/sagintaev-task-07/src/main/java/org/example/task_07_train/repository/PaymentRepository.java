package org.example.task_07_train.repository;

import org.example.task_07_train.domain.PaymentsDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface PaymentRepository {
    Flux<PaymentsDTO> countPaymentsByGoal();
    Flux<PaymentsDTO> findCreditAndPaymentIdsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    Mono<Integer> countPaymentsBySource(String paymentSource);
    Flux<PaymentsDTO> countPaymentsGroupedBySourceAndGoal();
}
