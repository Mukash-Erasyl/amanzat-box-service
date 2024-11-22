package org.example.task_07_train.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CollectorDTO {

    private String firstName;

    private String lastName;

    private String collectorActivityStatus;

    private Integer collectorCount;
}
