package com.session.demo.demo.dto.thirdPartyDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InfoDTO {

    @JsonProperty("seed")
    private String seed;

    @JsonProperty("results")
    private Integer results;

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("version")
    private String version;
}
