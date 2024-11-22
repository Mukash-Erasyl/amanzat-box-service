package org.example.sagintaevtask16.client;

import org.example.sagintaevtask16.dto.BorrowerDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;


@ReactiveFeignClient(name = "borrowerFeignClient", url = "http://localhost:8080/borrower")
public interface BorrowerFeignClient {

    @GetMapping("/not-blocked")
    Flux<BorrowerDTO> findNotBlockedBorrowers();

    @GetMapping("/by-city-industry/{city}/{industry}")
    Flux<BorrowerDTO> findBySelectedCityAndIndustryBorrowers(@PathVariable("city") String city, @PathVariable("industry") String industry);

    @GetMapping("/account-ids")
    Flux<Long> findNotCreatedYetUsers();

    @GetMapping("/education-count")
    Flux<BorrowerDTO> countBorrowerByEducation();
}