package com.session.demo.demo.dto.thirdPartyDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class IdSampleDTO {
    @JsonProperty("name")
    private String name;

    @JsonProperty("value")
    private String value;
}
