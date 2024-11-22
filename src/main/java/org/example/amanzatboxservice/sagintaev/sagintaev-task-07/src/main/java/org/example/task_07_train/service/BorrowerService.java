package org.example.task_07_train.service;

import org.example.task_07_train.domain.BorrowerDTO;
import reactor.core.publisher.Flux;

public interface BorrowerService {
    Flux<BorrowerDTO> findNotBlockedBorrowers();

    Flux<BorrowerDTO> findBySelectedCityAndIndustryBorrowers(String city, String industry);

    Flux<Long> findNotCreatedYetUsers();

    Flux<BorrowerDTO> countBorrowerByEducation();
}
