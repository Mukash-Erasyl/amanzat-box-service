package org.example.task_07_train.repository;

import com.example.jooq.enums.VerificationCallStatus;
import com.example.jooq.enums.VerificationCreditStatus;
import org.example.task_07_train.domain.VerifierDTO;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.jooq.Tables.*;
import static com.example.jooq.tables.Verifier.VERIFIER;

@Repository
public class DefaultVerifierRepository implements VerifierRepository {
    private final DSLContext dsl;

    public DefaultVerifierRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Flux<VerifierDTO> findVerifiersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return Flux.defer(()->{
            List<VerifierDTO> result = dsl.select(VERIFIER.FIRST_NAME.as("first_name"), VERIFIER.LAST_NAME.as("last_name"))
                    .from(VERIFIER).join(VERIFICATION_CREDIT)
                    .on(VERIFIER.ID.eq(VERIFICATION_CREDIT.VERIFIER_ID))
                    .join(VERIFICATION_CALL).on(VERIFICATION_CALL.VERIFICATION_CREDIT_ID
                            .eq(VERIFICATION_CREDIT.ID))
                    .where(VERIFICATION_CALL.CALL_DATE.between(startDate, endDate))
                    .fetchInto(VerifierDTO.class);

            return Flux.fromIterable(result);
        }).onErrorResume(e-> Flux.error(new RuntimeException()));
    }

    public Flux<VerifierDTO> findVerifiersWithoutCredit() {
        return Flux.defer(()->{
            List<VerifierDTO> result = dsl.select(VERIFIER.FIRST_NAME.as("first_name"), VERIFIER.LAST_NAME.as("last_name"))
                    .from(VERIFIER).leftJoin(VERIFICATION_CREDIT)
                    .on(VERIFICATION_CREDIT.VERIFIER_ID.eq(VERIFIER.ID))
                    .where(VERIFICATION_CREDIT.ID.isNull())
                    .fetchInto(VerifierDTO.class);

            return Flux.fromIterable(result);
        }).onErrorResume(e-> Flux.error(new RuntimeException()));
    }

    public Flux<VerifierDTO> countVerifiersByStatus() {
        return Flux.defer(()->{
            List<VerifierDTO> result = dsl.select(
                            VERIFIER.ACTIVITY_STATUS.as("verifier_activity_status"),
                            DSL.count(VERIFIER.ID).as("verifier_count")
                    ).from(VERIFIER).groupBy(VERIFIER.ACTIVITY_STATUS)
                    .fetchInto(VerifierDTO.class);

            return Flux.fromIterable(result);
        }).onErrorResume(e-> Flux.error(new RuntimeException()));
    }

    public Flux<VerifierDTO> listCreditsByStatus(String creditStatus, String callStatus) {
        return Flux.defer(()->{
            List<VerifierDTO> result = dsl.select(
                            VERIFICATION_CREDIT.CREDIT_ID.as("credit_id"), VERIFICATION_CREDIT.STATUS.as("verification_credit_status"),
                            VERIFICATION_CALL.STATUS.as("verification_call_status")

                    ).from(VERIFICATION_CREDIT)
                    .join(VERIFICATION_CALL).on(VERIFICATION_CREDIT.ID
                            .eq(VERIFICATION_CALL.VERIFICATION_CREDIT_ID))
                    .where(VERIFICATION_CREDIT.STATUS
                            .eq(VerificationCreditStatus.valueOf(creditStatus))
                            .and(VERIFICATION_CALL.STATUS.eq(VerificationCallStatus.valueOf(callStatus))))
                    .fetchInto(VerifierDTO.class);

            return Flux.fromIterable(result);
        }).onErrorResume(e-> Flux.error(new RuntimeException()));
    }
}
