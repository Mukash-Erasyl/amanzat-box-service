package org.example.task_07_train.service;

import org.example.task_07_train.domain.PaymentsDTO;
import org.example.task_07_train.repository.DefaultPaymentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class DefaultPaymentService implements PaymentService {
    private final DefaultPaymentRepository paymentRepository;

    public DefaultPaymentService(DefaultPaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Flux<PaymentsDTO> countPaymentByGoal() {
        return paymentRepository.countPaymentsByGoal();
    }

    public Flux<PaymentsDTO> findCreditAndPaymentIdsByReceivedDateRange(LocalDateTime startDate, LocalDateTime endDate){
        return paymentRepository.findCreditAndPaymentIdsByDateRange(startDate, endDate);
    }

    public Mono<Integer> countOfPaymentsInSelectedSource(String paymentSource) {
        return paymentRepository.countPaymentsBySource(paymentSource);
    }

    public Flux<PaymentsDTO> countPaymentsGroupedBySourceAndGoal(){
        return paymentRepository.countPaymentsGroupedBySourceAndGoal();
    }


}
