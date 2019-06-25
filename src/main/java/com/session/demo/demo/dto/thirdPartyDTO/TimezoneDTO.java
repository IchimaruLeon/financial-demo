package com.session.demo.demo.dto.thirdPartyDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TimezoneDTO {

    @JsonProperty("offset")
    private String offset;

    @JsonProperty("description")
    private String description;
}
