package org.example.task_07_train.repository;

import org.example.task_07_train.domain.VerifierDTO;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface VerifierRepository {
    Flux<VerifierDTO> findVerifiersByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    Flux<VerifierDTO> findVerifiersWithoutCredit();
    Flux<VerifierDTO> countVerifiersByStatus();
    Flux<VerifierDTO> listCreditsByStatus(String creditStatus, String callStatus);
}
