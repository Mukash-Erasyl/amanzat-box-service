package org.example.task_07_train.service;

import org.example.task_07_train.domain.BorrowerDTO;
import org.example.task_07_train.repository.DefaultBorrowerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class DefaultBorrowerService implements BorrowerService {
    private final DefaultBorrowerRepository borrowerRepository;

    public DefaultBorrowerService(DefaultBorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    public Flux<BorrowerDTO> findNotBlockedBorrowers() {
        return borrowerRepository.findActive();
    }

    public Flux<BorrowerDTO> findBySelectedCityAndIndustryBorrowers(String city, String industry){
        return borrowerRepository.findByCityAndIndustry(city, industry);
    }

    public Flux<Long> findNotCreatedYetUsers() {
        return borrowerRepository.findUncreatedUsers();
    }

    public Flux<BorrowerDTO> countBorrowerByEducation(){
        return borrowerRepository.countByEducation();
    }

}
