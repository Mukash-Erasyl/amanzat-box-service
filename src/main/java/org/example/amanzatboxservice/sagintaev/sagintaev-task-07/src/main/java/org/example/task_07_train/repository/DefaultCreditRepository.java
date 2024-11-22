package org.example.task_07_train.repository;

import com.example.jooq.enums.CreditStatus;
import com.example.jooq.enums.ProductFinanceType;
import com.example.jooq.enums.ProductType;
import org.example.task_07_train.domain.CreditDTO;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.example.jooq.Tables.BORROWER;
import static com.example.jooq.tables.Credit.CREDIT;
import static com.example.jooq.tables.Product.PRODUCT;

@Repository
public class DefaultCreditRepository implements CreditRepository {
    private final DSLContext dsl;

    public DefaultCreditRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Flux<CreditDTO> findCreditCountByBorrowerId(Long borrowerId) {
        return Flux.defer(() -> {
            List<CreditDTO> result = dsl.select
                            (
                                    BORROWER.ID.as("borrower_id"), DSL.count(CREDIT.ID).as("count_credit")
                            ).from(BORROWER).join(CREDIT)
                    .on(BORROWER.ID.eq(CREDIT.BORROWER_ID))
                    .where(BORROWER.ID.eq(borrowerId))
                    .groupBy(BORROWER.ID)
                    .fetchInto(CreditDTO.class);

            return Flux.fromIterable(result);
        }).onErrorResume(e -> Flux.error(new RuntimeException("Ошибка при получений данных")));
    }

    public Mono<Integer> countCreditsByStatus(String status) {
        return Mono.fromCallable(() -> dsl.selectCount().from(CREDIT)
                .where(CREDIT.STATUS.eq(CreditStatus.valueOf(status)))
                .fetchOptional()
                .map(Record1::value1)
                .orElse(0)
        );
    }

    public Mono<Integer> countCreditsByFinanceTypeAndDate(String financeType, LocalDate
            lowerLimit, LocalDate upperLimit) {
        return Mono.fromCallable(() -> dsl.selectCount()
                .from(CREDIT).join(PRODUCT).on(CREDIT.PRODUCT_ID.eq(PRODUCT.ID))
                .where(PRODUCT.FINANCE_TYPE.eq(ProductFinanceType.valueOf(financeType))
                        .and(CREDIT.DATE_REQUESTED.between(lowerLimit.atStartOfDay(), upperLimit.atTime(LocalTime.MAX))))
                .fetchOptional()
                .map(Record1::value1)
                .orElse(0)
        );
    }

    public Flux<CreditDTO> findCreditsByProductAndFinanceType(String productType, String financeType) {
        return Flux.defer(() -> {
            List<CreditDTO> credits = dsl.select
                            (
                                    CREDIT.ID.as("credit_id"),
                                    PRODUCT.FINANCE_TYPE.as("financeType"),
                                    PRODUCT.TYPE.as("productType")
                            ).from(CREDIT)
                    .join(PRODUCT).on(PRODUCT.ID.eq(CREDIT.PRODUCT_ID))
                    .where(PRODUCT.FINANCE_TYPE.eq(ProductFinanceType.valueOf(financeType))
                            .and(PRODUCT.TYPE.eq(ProductType.valueOf(productType))))
                    .fetchInto(CreditDTO.class);

            return Flux.fromIterable(credits);
        }).onErrorResume(e -> Flux.error(new RuntimeException()));
    }
}
