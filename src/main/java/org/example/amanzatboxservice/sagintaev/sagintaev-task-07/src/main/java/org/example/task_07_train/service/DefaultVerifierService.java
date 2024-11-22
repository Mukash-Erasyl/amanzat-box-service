package org.example.task_07_train.service;

import org.example.task_07_train.domain.VerifierDTO;
import org.example.task_07_train.repository.DefaultVerifierRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Service
public class DefaultVerifierService implements VerifierService {
    private final DefaultVerifierRepository verifierRepository;

    public DefaultVerifierService(DefaultVerifierRepository verifierRepository) {
        this.verifierRepository = verifierRepository;
    }

    public Flux<VerifierDTO> findVerifiersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return verifierRepository.findVerifiersByDateRange(startDate, endDate);
    }

    public Flux<VerifierDTO> findVerifiersWithNoVerificationCredit() {
        return verifierRepository.findVerifiersWithoutCredit();
    }

    public Flux<VerifierDTO> countVerifiersByActivityStatus() {
        return verifierRepository.countVerifiersByStatus();
    }

    public Flux<VerifierDTO> listCreditsByStatus(String creditStatus, String callStatus){
        return verifierRepository.listCreditsByStatus(creditStatus, callStatus);
    }
}
