package org.example.sagintaevtask16.controller;

import lombok.RequiredArgsConstructor;
import org.example.sagintaevtask16.dto.BorrowerDTO;
import org.example.sagintaevtask16.service.BorrowerFeignService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/borrower")
public class BorrowerFeignController {
    private final BorrowerFeignService borrowerFeignService;

    @GetMapping("/not-blocked")
    public ResponseEntity<Flux<BorrowerDTO>> findNotBlockedBorrower(){
        return ResponseEntity.ok(borrowerFeignService.findNotBlockedBorrowers());
    }

    @GetMapping("/by-city-industry/{city}/{industry}")
    public ResponseEntity<Flux<BorrowerDTO>> findBySelectedCityAndIndustryBorrowers(@PathVariable String city, @PathVariable String industry){
        return ResponseEntity.ok(borrowerFeignService.findBySelectedCityAndIndustryBorrowers(city, industry));
    }

    @GetMapping("/account-ids")
    public ResponseEntity<Flux<Long>> findNotCreatedYetUsers(){
        return ResponseEntity.ok(borrowerFeignService.findNotCreatedYetUsers());
    }

    @GetMapping("/education-count")
    public ResponseEntity<Flux<BorrowerDTO>> countBorrowerByEducation(){
        return ResponseEntity.ok(borrowerFeignService.countBorrowerByEducation());
    }
}
