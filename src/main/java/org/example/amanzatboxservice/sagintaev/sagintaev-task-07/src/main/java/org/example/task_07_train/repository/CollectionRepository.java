package org.example.task_07_train.repository;

import org.example.task_07_train.domain.CollectorDTO;
import reactor.core.publisher.Flux;

public interface CollectionRepository {
    Flux<CollectorDTO> findWithoutCredit();
    Flux<CollectorDTO> findByInteraction(String type, String status);
    Flux<CollectorDTO> countByActivityStatus();
    Flux<CollectorDTO> findWithCreditNoInteractions(String status);
}
