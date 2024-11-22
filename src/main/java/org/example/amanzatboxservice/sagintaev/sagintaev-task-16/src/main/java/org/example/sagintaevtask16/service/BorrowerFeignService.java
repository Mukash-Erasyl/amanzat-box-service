package org.example.sagintaevtask16.service;

import org.example.sagintaevtask16.client.BorrowerFeignClient;
import org.example.sagintaevtask16.dto.BorrowerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class BorrowerFeignService {
    private final BorrowerFeignClient borrowerFeignClient;
    private static final Logger log = LoggerFactory.getLogger(BorrowerFeignService.class);

    public BorrowerFeignService(BorrowerFeignClient borrowerFeignClient) {
        this.borrowerFeignClient = borrowerFeignClient;
    }

    public Flux<BorrowerDTO> findNotBlockedBorrowers() {
        return borrowerFeignClient.findNotBlockedBorrowers()
                .onErrorResume(e -> {
                    log.error("Error in findNotBlockedBorrowers: {}", e.getMessage());
                    return Flux.empty();
                });
    }

    public Flux<BorrowerDTO> findBySelectedCityAndIndustryBorrowers(String city, String industry) {
        return borrowerFeignClient.findBySelectedCityAndIndustryBorrowers(city, industry)
                .onErrorResume(e -> {
                    log.error("Error in findBySelectedCityAndIndustryBorrowers: {}", e.getMessage());
                    return Flux.empty();
                });
    }

    public Flux<Long> findNotCreatedYetUsers() {
        return borrowerFeignClient.findNotCreatedYetUsers()
                .onErrorResume(e -> {
                    log.error("Error in findNotCreatedYetUsers: {}", e.getMessage());
                    return Flux.empty();
                });
    }

    public Flux<BorrowerDTO> countBorrowerByEducation() {
        return borrowerFeignClient.countBorrowerByEducation()
                .onErrorResume(e -> {
                    log.error("Error in countBorrowerByEducation: {}", e.getMessage());
                    return Flux.empty();
                });
    }
}
