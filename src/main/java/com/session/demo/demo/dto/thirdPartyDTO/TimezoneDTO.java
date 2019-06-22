package com.session.demo.demo.dto.thirdPartyDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TimezoneDTO {

    @JsonProperty("offset")
    private String offset;

    @JsonProperty("description")
    private String description;
}
