package com.session.demo.demo.dto.thirdPartyDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SampleResourceDTO {

    @JsonProperty("results")
    private List<SampleUserDTO> results;

    @JsonProperty("info")
    private InfoDTO info;

}
