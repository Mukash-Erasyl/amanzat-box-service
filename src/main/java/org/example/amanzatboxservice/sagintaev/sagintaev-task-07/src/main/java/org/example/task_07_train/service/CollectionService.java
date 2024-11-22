package org.example.task_07_train.service;

import org.example.task_07_train.domain.CollectorDTO;
import reactor.core.publisher.Flux;

public interface CollectionService {
    Flux<CollectorDTO> findCollectorsWithoutCredit();

    Flux<CollectorDTO> findCollectorByInteractionTypeAndInteractionStatus(String interactionType, String interactionStatus);

    Flux<CollectorDTO> countCollectorsByActivityStatus();

    Flux<CollectorDTO> findCollectorsWithCreditAndNoInteractions(String creditStatus);
}
