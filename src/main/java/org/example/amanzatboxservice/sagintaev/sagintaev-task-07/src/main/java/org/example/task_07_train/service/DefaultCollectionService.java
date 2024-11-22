package org.example.task_07_train.service;

import org.example.task_07_train.domain.CollectorDTO;
import org.example.task_07_train.repository.DefaultCollectionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class DefaultCollectionService implements CollectionService {
    private final DefaultCollectionRepository collectionRepository;

    public DefaultCollectionService(DefaultCollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    public Flux<CollectorDTO> findCollectorsWithoutCredit() {
        return collectionRepository.findWithoutCredit();
    }

    public Flux<CollectorDTO> findCollectorByInteractionTypeAndInteractionStatus(String interactionType, String interactionStatus) {
        return collectionRepository.findByInteraction(interactionType, interactionStatus);
    }

    public Flux<CollectorDTO> countCollectorsByActivityStatus() {
        return collectionRepository.countByActivityStatus();
    }

    public Flux<CollectorDTO> findCollectorsWithCreditAndNoInteractions(String creditStatus){
        return collectionRepository.findWithCreditNoInteractions(creditStatus);
    }
}
