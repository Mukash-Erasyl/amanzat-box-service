package org.example.task_07_train.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentsDTO {

    private Long paymentId;

    private Long creditId;

    private String paymentGoal;

    private String paymentSource;

    private Integer paymentCount;
}
