package org.example.task_07_train.repository;

import org.example.task_07_train.domain.BorrowerDTO;
import reactor.core.publisher.Flux;

public interface BorrowerRepository {
    Flux<BorrowerDTO> findActive();
    Flux<BorrowerDTO> findByCityAndIndustry(String city, String industry);
    Flux<Long> findUncreatedUsers();
    Flux<BorrowerDTO> countByEducation();
}
