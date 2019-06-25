package com.session.demo.demo.dto.thirdPartyDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DobDTO {
    @JsonProperty("date")
    private String date;

    @JsonProperty("age")
    private Integer age;
}
