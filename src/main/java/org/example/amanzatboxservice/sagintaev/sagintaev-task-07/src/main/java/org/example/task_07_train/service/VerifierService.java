package org.example.task_07_train.service;

import org.example.task_07_train.domain.VerifierDTO;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface VerifierService {
    Flux<VerifierDTO> findVerifiersByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    Flux<VerifierDTO> findVerifiersWithNoVerificationCredit();

    Flux<VerifierDTO> countVerifiersByActivityStatus();

    Flux<VerifierDTO> listCreditsByStatus(String creditStatus, String callStatus);
}

