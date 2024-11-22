package org.example.task_07_train.controller;

import org.example.task_07_train.domain.CollectorDTO;
import org.example.task_07_train.service.DefaultCollectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/collection")
public class CollectionController {
    private final DefaultCollectionService collectionService;

    public CollectionController(DefaultCollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping("/without-collector-credit")
    public ResponseEntity<Flux<CollectorDTO>> findCollectorsWithoutCredit() {
        return ResponseEntity.ok(collectionService.findCollectorsWithoutCredit());
    }

    @GetMapping("/with-collector-credit/{interactionType}/{interactionStatus}")
    public ResponseEntity<Flux<CollectorDTO>> findCollectorByInteractionTypeAndInteractionStatus(@PathVariable String interactionType, @PathVariable String interactionStatus) {
        return ResponseEntity.ok(collectionService.findCollectorByInteractionTypeAndInteractionStatus(interactionType, interactionStatus));
    }

    @GetMapping("/count/activity-status")
    public ResponseEntity<Flux<CollectorDTO>> countCollectorsByActivityStatus() {
        return ResponseEntity.ok(collectionService.countCollectorsByActivityStatus());
    }

    @GetMapping("/with-collector-credit/without-interaction/{creditStatus}")
    public ResponseEntity<Flux<CollectorDTO>> findCollectorsWithCreditAndNoInteractions(@PathVariable String creditStatus){
        return ResponseEntity.ok(collectionService.findCollectorsWithCreditAndNoInteractions(creditStatus));
    }
}
