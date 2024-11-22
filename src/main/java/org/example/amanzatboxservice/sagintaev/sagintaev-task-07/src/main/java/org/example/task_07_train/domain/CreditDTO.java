package org.example.task_07_train.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditDTO {
    private Long creditId;

    private String productType;

    private String financeType;

    private Long borrowerId;

    private Integer countCredit;


}
