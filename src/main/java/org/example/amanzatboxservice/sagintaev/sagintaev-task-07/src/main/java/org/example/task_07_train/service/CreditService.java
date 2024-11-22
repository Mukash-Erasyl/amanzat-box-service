package org.example.task_07_train.service;

import org.example.task_07_train.domain.CreditDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface CreditService {
    Flux<CreditDTO> findCreditCountByBorrowerId(Long borrowerId);

    Mono<Integer> countOfCreditsInSelectedStatus(String status);

    Mono<Integer> countCreditsByFinanceTypeAndDateRange(String financeType, LocalDate lowerLimit, LocalDate upperLimit);

    Flux<CreditDTO> findCreditsByProductTypeAndFinanceType(String productType, String financeType);
}
