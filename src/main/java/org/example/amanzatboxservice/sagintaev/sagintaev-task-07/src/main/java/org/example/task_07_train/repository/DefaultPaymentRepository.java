package org.example.task_07_train.repository;

import com.example.jooq.enums.PaymentSource;
import org.example.task_07_train.domain.PaymentsDTO;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.jooq.tables.Credit.CREDIT;
import static com.example.jooq.tables.Payment.PAYMENT;

@Repository
public class DefaultPaymentRepository implements PaymentRepository {
    private final DSLContext dsl;

    public DefaultPaymentRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Flux<PaymentsDTO> countPaymentsByGoal() {
        return Flux.defer(()->{
            List<PaymentsDTO> result = dsl.select(
                            PAYMENT.GOAL.as("payment_goal"),
                            DSL.count(PAYMENT.ID).as("payment_count")
                    ).from(PAYMENT).groupBy(PAYMENT.GOAL)
                    .fetchInto(PaymentsDTO.class);

            return Flux.fromIterable(result);
        }).onErrorResume(e-> Flux.error(new RuntimeException()));
    }

    public Flux<PaymentsDTO> findCreditAndPaymentIdsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return Flux.defer(()->{
            List<PaymentsDTO> result = dsl.select(PAYMENT.ID.as("payment_id"), CREDIT.ID.as("credit_id"))
                    .from(PAYMENT).join(CREDIT).on(CREDIT.ID.eq(PAYMENT.CREDIT_ID))
                    .where(PAYMENT.RECEIVED_DATE.between(startDate, endDate))
                    .fetchInto(PaymentsDTO.class);

            return Flux.fromIterable(result);
        }).onErrorResume(e-> Flux.error(new RuntimeException()));
    }

    public Mono<Integer> countPaymentsBySource(String paymentSource) {
        return Mono.fromCallable(() -> dsl.selectCount().from(PAYMENT)
                .where(PAYMENT.SOURCE.eq(PaymentSource.valueOf(paymentSource)))
                .fetchOptional()
                .map(Record1::value1)
                .orElse(0)
        );
    }

    public Flux<PaymentsDTO> countPaymentsGroupedBySourceAndGoal() {
        return Flux.defer(()->{
            List<PaymentsDTO> result = dsl.select(
                            PAYMENT.SOURCE.as("payment_source"), PAYMENT.GOAL.as("payment_goal"), DSL.count(PAYMENT.ID).as("payment_count")
                    ).from(PAYMENT).groupBy(PAYMENT.SOURCE, PAYMENT.GOAL)
                    .fetchInto(PaymentsDTO.class);

            return Flux.fromIterable(result);
        }).onErrorResume(e-> Flux.error(new RuntimeException()));
    }
}
