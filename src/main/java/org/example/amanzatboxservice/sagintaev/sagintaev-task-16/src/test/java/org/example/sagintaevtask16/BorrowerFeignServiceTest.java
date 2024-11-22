package org.example.sagintaevtask16;

import org.example.sagintaevtask16.client.BorrowerFeignClient;
import org.example.sagintaevtask16.dto.BorrowerDTO;
import org.example.sagintaevtask16.service.BorrowerFeignService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BorrowerFeignServiceTest {
    @Mock
    private BorrowerFeignClient borrowerFeignClient;

    @InjectMocks
    private BorrowerFeignService borrowerFeignService;

    @Test
    void testFindNotBlockedBorrowers_ShouldReturnEmptyOnError() {
        when(borrowerFeignClient.findNotBlockedBorrowers())
                .thenReturn(Flux.error(new RuntimeException("Test Exception")));

        Flux<BorrowerDTO> result = borrowerFeignService.findNotBlockedBorrowers();

        assertTrue(result.collectList().block().isEmpty(), "Expected empty list when error occurs");
    }

    @Test
    void testFindNotBlockedBorrowers_ShouldReturnResults() {
        BorrowerDTO borrower1 = new BorrowerDTO();
        borrower1.setFirstName("John");
        borrower1.setLastName("Doe");
        borrower1.setEducation("Engineer");

        BorrowerDTO borrower2 = new BorrowerDTO();
        borrower2.setFirstName("Jane");
        borrower2.setLastName("Smith");
        borrower2.setEducation("Doctor");

        when(borrowerFeignClient.findNotBlockedBorrowers())
                .thenReturn(Flux.just(borrower1, borrower2));

        Flux<BorrowerDTO> result = borrowerFeignService.findNotBlockedBorrowers();

        assertEquals(2, result.collectList().block().size(), "Expected two borrowers");
    }

    @Test
    void testFindBySelectedCityAndIndustryBorrowers_ShouldReturnResults() {

        BorrowerDTO borrower = new BorrowerDTO();
        borrower.setFirstName("Alice");
        borrower.setLastName("Johnson");
        borrower.setEducation("Engineer");
        when(borrowerFeignClient.findBySelectedCityAndIndustryBorrowers("City1", "Tech"))
                .thenReturn(Flux.just(borrower));

        Flux<BorrowerDTO> result = borrowerFeignService.findBySelectedCityAndIndustryBorrowers("City1", "Tech");

        assertEquals(1, result.collectList().block().size(), "Expected one borrower");
    }

    @Test
    void testFindNotCreatedYetUsers_ShouldReturnEmptyOnError() {
        when(borrowerFeignClient.findNotCreatedYetUsers())
                .thenReturn(Flux.error(new RuntimeException("Test Exception")));

        Flux<Long> result = borrowerFeignService.findNotCreatedYetUsers();

        assertTrue(result.collectList().block().isEmpty(), "Expected empty list when error occurs");
    }

    @Test
    void testCountBorrowerByEducation_ShouldReturnEmptyOnError() {
        when(borrowerFeignClient.countBorrowerByEducation())
                .thenReturn(Flux.error(new RuntimeException("Test Exception")));

        Flux<BorrowerDTO> result = borrowerFeignService.countBorrowerByEducation();

        assertTrue(result.collectList().block().isEmpty(), "Expected empty list when error occurs");
    }
}
