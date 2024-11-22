package org.example.task_07_train.service;

import org.example.task_07_train.domain.CreditDTO;
import org.example.task_07_train.repository.DefaultCreditRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class DefaultCreditService implements CreditService {
    private final DefaultCreditRepository creditRepository;

    public DefaultCreditService(DefaultCreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    public Flux<CreditDTO> findCreditCountByBorrowerId(Long borrower_id) {
        return creditRepository.findCreditCountByBorrowerId(borrower_id);
    }

    public Mono<Integer> countOfCreditsInSelectedStatus(String status) {
        return creditRepository.countCreditsByStatus(status);
    }

    public Mono<Integer> countCreditsByFinanceTypeAndDateRange(String financeType, LocalDate
            lowerLimit, LocalDate upperLimit) {
        return creditRepository
                .countCreditsByFinanceTypeAndDate(financeType, lowerLimit, upperLimit);
    }

    public Flux<CreditDTO> findCreditsByProductTypeAndFinanceType(String productType, String financeType) {
        return creditRepository.findCreditsByProductAndFinanceType(productType, financeType);
    }
}
