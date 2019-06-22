package com.session.demo.demo.dto.thirdPartyDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PictureDTO {
    @JsonProperty("large")
    private String large;

    @JsonProperty("medium")
    private String medium;

    @JsonProperty("thumbnail")
    private String thumbnail;
}
