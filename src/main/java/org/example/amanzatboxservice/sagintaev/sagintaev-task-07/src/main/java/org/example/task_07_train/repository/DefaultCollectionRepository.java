package org.example.task_07_train.repository;

import com.example.jooq.enums.*;
import org.example.task_07_train.domain.CollectorDTO;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

import static com.example.jooq.Tables.COLLECTOR_CREDIT;
import static com.example.jooq.Tables.COLLECTOR_INTERACTION;
import static com.example.jooq.tables.Collector.COLLECTOR;

@Repository
public class DefaultCollectionRepository implements CollectionRepository {
    private final DSLContext dsl;

    public DefaultCollectionRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Flux<CollectorDTO> findWithoutCredit() {
        return Flux.defer(()->{
            List<CollectorDTO> collectors = dsl.select(COLLECTOR.FIRST_NAME.as("first_name"), COLLECTOR.LAST_NAME.as("last_name"))
                    .from(COLLECTOR).leftJoin(COLLECTOR_CREDIT)
                    .on(COLLECTOR_CREDIT.COLLECTOR_ID.eq(COLLECTOR.ID))
                    .where(COLLECTOR_CREDIT.ID.isNull())
                    .fetchInto(CollectorDTO.class);

            return Flux.fromIterable(collectors);
        }).onErrorResume(e-> Flux.error(new RuntimeException()));
    }

    public Flux<CollectorDTO> findByInteraction(String interactionType, String interactionStatus) {
        return Flux.defer(()->{
            List<CollectorDTO> collectors = dsl.select(COLLECTOR.FIRST_NAME.as("first_name"), COLLECTOR.LAST_NAME.as("last_name"))
                    .from(COLLECTOR).join(COLLECTOR_CREDIT)
                    .on(COLLECTOR_CREDIT.COLLECTOR_ID.eq(COLLECTOR.ID))
                    .join(COLLECTOR_INTERACTION).on(COLLECTOR_INTERACTION.COLLECTOR_CREDIT_ID.eq(COLLECTOR_CREDIT.ID))
                    .where(COLLECTOR_INTERACTION.INTERACTION_STATUS.eq(CollectorInteractionInteractionStatus.valueOf(interactionStatus))
                            .and(COLLECTOR_INTERACTION.INTERACTION_TYPE.eq(CollectorInteractionInteractionType.valueOf(interactionType))))
                    .fetchInto(CollectorDTO.class);

            return Flux.fromIterable(collectors);
        }).onErrorResume(e-> Flux.error(new RuntimeException()));
    }

    public Flux<CollectorDTO> countByActivityStatus() {
        return Flux.defer(()->{
            List<CollectorDTO> result = dsl.select(COLLECTOR.ACTIVITY_STATUS.as("collector_activity_status"), DSL.count(COLLECTOR.ID).as("collector_count"))
                    .from(COLLECTOR).groupBy(COLLECTOR.ACTIVITY_STATUS)
                    .fetchInto(CollectorDTO.class);

            return Flux.fromIterable(result);
        }).onErrorResume(e-> Flux.error(new RuntimeException()));
    }

    public Flux<CollectorDTO> findWithCreditNoInteractions(String creditStatus) {
        return Flux.defer(()->{
            List<CollectorDTO> collectors = dsl.select(COLLECTOR.FIRST_NAME.as("first_name"), COLLECTOR.LAST_NAME.as("last_name"))
                    .from(COLLECTOR).join(COLLECTOR_CREDIT)
                    .on(COLLECTOR_CREDIT.COLLECTOR_ID.eq(COLLECTOR.ID))
                    .leftJoin(COLLECTOR_INTERACTION).on(COLLECTOR_INTERACTION.COLLECTOR_CREDIT_ID.eq(COLLECTOR_CREDIT.ID))
                    .where(COLLECTOR_INTERACTION.ID.isNull().and(COLLECTOR_CREDIT.STATUS.eq(CollectorCreditStatus.valueOf(creditStatus))))
                    .fetchInto(CollectorDTO.class);

            return Flux.fromIterable(collectors);
        }).onErrorResume(e-> Flux.error(new RuntimeException()));
    }
}
