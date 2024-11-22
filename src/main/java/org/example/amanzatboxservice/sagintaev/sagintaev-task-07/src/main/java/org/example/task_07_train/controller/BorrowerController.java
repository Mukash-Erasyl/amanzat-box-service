package org.example.task_07_train.controller;

import org.example.task_07_train.domain.BorrowerDTO;
import org.example.task_07_train.service.DefaultBorrowerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/borrower")
public class BorrowerController {
    private final DefaultBorrowerService borrowerService;

    public BorrowerController(DefaultBorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @GetMapping("/not-blocked")
    public ResponseEntity<Flux<BorrowerDTO>> findNotBlockedBorrower(){
        return ResponseEntity.ok(borrowerService.findNotBlockedBorrowers());
    }

    @GetMapping("/by-city-industry/{city}/{industry}")
    public ResponseEntity<Flux<BorrowerDTO>> findBySelectedCityAndIndustryBorrowers(@PathVariable String city, @PathVariable String industry){
        return ResponseEntity.ok(borrowerService.findBySelectedCityAndIndustryBorrowers(city, industry));
    }

    @GetMapping("/account-ids")
    public ResponseEntity<Flux<Long>> findNotCreatedYetUsers(){
        return ResponseEntity.ok(borrowerService.findNotCreatedYetUsers());
    }

    @GetMapping("/education-count")
    public ResponseEntity<Flux<BorrowerDTO>> countBorrowerByEducation(){
        return ResponseEntity.ok(borrowerService.countBorrowerByEducation());
    }
}
