package org.example.task_07_train.controller;

import org.example.task_07_train.domain.CreditDTO;
import org.example.task_07_train.service.DefaultCreditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping("/credit")
public class CreditController {
    private final DefaultCreditService creditService;

    public CreditController(DefaultCreditService creditService) {
        this.creditService = creditService;
    }

    @GetMapping("/{borrower_id}/count")
    public ResponseEntity<Flux<CreditDTO>> findCountOfBorrowers(@PathVariable Long borrower_id) {
        return ResponseEntity.ok(creditService.findCreditCountByBorrowerId(borrower_id));
    }

    @GetMapping("/countByStatus/{selectedStatus}")
    public ResponseEntity<Mono<Integer>> countOfCreditsInSelectedStatus(@PathVariable String selectedStatus) {
        return ResponseEntity.ok(creditService.countOfCreditsInSelectedStatus(selectedStatus));
    }

    @GetMapping("/byFinanceType/{financeType}/{lowerLimit}/{upperLimit}")
    public ResponseEntity<Mono<Integer>> countCreditsByFinanceTypeAndDateRange(@PathVariable String financeType,@PathVariable LocalDate
            lowerLimit, @PathVariable LocalDate upperLimit) {
        return ResponseEntity.ok(creditService.countCreditsByFinanceTypeAndDateRange(financeType, lowerLimit, upperLimit));
    }

    @GetMapping("/by-types/{productType}/{financeType}")
    public ResponseEntity<Flux<CreditDTO>> findCreditsByProductTypeAndFinanceType(@PathVariable String productType, @PathVariable String financeType){
        return ResponseEntity.ok(creditService.findCreditsByProductTypeAndFinanceType(productType, financeType));
    }
}
