package org.example.task_07_train.repository;

import com.example.jooq.tables.*;
import org.example.task_07_train.domain.BorrowerDTO;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.List;

import static com.example.jooq.Tables.*;
import static com.example.jooq.tables.Address.ADDRESS;
import static com.example.jooq.tables.Borrower.BORROWER;


@Repository
public class DefaultBorrowerRepository implements BorrowerRepository {
    private final DSLContext dsl;

    public DefaultBorrowerRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Flux<BorrowerDTO> findActive() {
        return Flux.defer(()->{
            List<BorrowerDTO> borrowers = dsl.select(
                            BORROWER.ID.as("id"),
                            PERSONAL_DATA.FIRST_NAME.as("first_name"),
                            PERSONAL_DATA.LAST_NAME.as("last_name")
                    )
                    .from(BORROWER).join(UserAccount.USER_ACCOUNT)
                    .on(BORROWER.USER_ACCOUNT_ID.eq(UserAccount.USER_ACCOUNT.ID))
                    .join(PersonalData.PERSONAL_DATA)
                    .on(PersonalData.PERSONAL_DATA.BORROWER_ID.eq(BORROWER.ID))
                    .where(UserAccount.USER_ACCOUNT.BLOCKED.eq((byte) 0))
                    .fetchInto(BorrowerDTO.class);

            return Flux.fromIterable(borrowers);
        }).onErrorResume(e -> Flux.error(new RuntimeException()));
    }

    public Flux<BorrowerDTO> findByCityAndIndustry(String city, String industry) {
       return Flux.defer(()->{
           List<BorrowerDTO> borrowers = dsl.select(
                           BORROWER.ID.as("id"),
                           PERSONAL_DATA.FIRST_NAME.as("first_name"),
                           PERSONAL_DATA.LAST_NAME.as("last_name")
                   )
                   .from(PERSONAL_DATA)
                   .join(BORROWER)
                   .on(PersonalData.PERSONAL_DATA.BORROWER_ID.eq(BORROWER.ID))
                   .join(ADDRESS).on(ADDRESS.BORROWER_ID.eq(BORROWER.ID))
                   .join(WORK).on(WORK.BORROWER_ID.eq(BORROWER.ID))
                   .where(ADDRESS.CITY.eq(city).and(WORK.INDUSTRY.eq(industry)))
                   .fetchInto(BorrowerDTO.class);

           return Flux.fromIterable(borrowers);
       }).onErrorResume(e-> Flux.error(new RuntimeException()));
    }

    public Flux<Long> findUncreatedUsers() {
        try {
            List<Long> userAccountIds = dsl.select(USER_ACCOUNT.ID)
                    .from(UserAccount.USER_ACCOUNT)
                    .leftJoin(BORROWER).on(UserAccount.USER_ACCOUNT.ID.eq(BORROWER.USER_ACCOUNT_ID))
                    .where(BORROWER.ID.isNull())
                    .fetchInto(Long.class);

            return Flux.fromIterable(userAccountIds);
        } catch (DataAccessException e) {
            return Flux.error(new RuntimeException("Ошибка при получений данных"));
        } catch (Exception e) {
            return Flux.error(new RuntimeException("Произошла неизвестная ошибка"));
        }
    }

    @Transactional()
    public Flux<BorrowerDTO> countByEducation() {
        return Flux.defer(()->{
            List<BorrowerDTO> borrowers = dsl
                    .select(WORK.EDUCATION.as("education"), DSL.count(BORROWER.ID).as("count"))
                    .from(WORK)
                    .join(BORROWER).on(WORK.BORROWER_ID.eq(BORROWER.ID))
                    .groupBy(WORK.EDUCATION)
                    .fetchInto(BorrowerDTO.class);

            return Flux.fromIterable(borrowers);
        }).onErrorResume(e-> Flux.error(new RuntimeException()));
    }
}
