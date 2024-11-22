package org.example.task_07_train.controller;

import org.example.task_07_train.domain.VerifierDTO;
import org.example.task_07_train.service.DefaultVerifierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@RestController
@RequestMapping("verification")
public class VerificationController {
    private final DefaultVerifierService verifierService;

    public VerificationController(DefaultVerifierService verifierService) {
        this.verifierService = verifierService;
    }

    @GetMapping("/by-call-date/{startDate}/{endDate}")
    public ResponseEntity<Flux<VerifierDTO>> findVerifiersInDateRange(@PathVariable LocalDateTime startDate, @PathVariable LocalDateTime endDate) {
        return ResponseEntity.ok(verifierService.findVerifiersByDateRange(startDate, endDate));
    }

    @GetMapping("/without-verification-credit")
    public ResponseEntity<Flux<VerifierDTO>> findVerifiersWithNoVerificationCredit() {
        return ResponseEntity.ok(verifierService.findVerifiersWithNoVerificationCredit());
    }

    @GetMapping("/count/activity-status")
    public ResponseEntity<Flux<VerifierDTO>> countVerifiersByActivityStatus() {
        return ResponseEntity.ok(verifierService.countVerifiersByActivityStatus());
    }

    @GetMapping("/by-verification-status/{creditStatus}/{callStatus}")
    public ResponseEntity<Flux<VerifierDTO>> listCreditsWithStatus(@PathVariable String creditStatus, @PathVariable String callStatus){
        return ResponseEntity.ok(verifierService.listCreditsByStatus(creditStatus, callStatus));
    }
}
