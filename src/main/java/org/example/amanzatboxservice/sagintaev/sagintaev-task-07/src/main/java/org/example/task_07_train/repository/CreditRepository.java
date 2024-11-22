package org.example.task_07_train.repository;

import org.example.task_07_train.domain.CreditDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface CreditRepository {
    Flux<CreditDTO> findCreditCountByBorrowerId(Long borrowerId);
    Mono<Integer> countCreditsByStatus(String status);
    Mono<Integer> countCreditsByFinanceTypeAndDate(String financeType, LocalDate lowerLimit, LocalDate upperLimit);
    Flux<CreditDTO> findCreditsByProductAndFinanceType(String productType, String financeType);
}
