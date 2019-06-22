package com.session.demo.demo.dto.thirdPartyDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class NamesDTO {

    @JsonProperty("title")
    private String title;

    @JsonProperty("first")
    private String first;

    @JsonProperty("last")
    private String last;
}
