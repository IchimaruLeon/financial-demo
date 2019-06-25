package com.session.demo.demo.dto.thirdPartyDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LocationDTO {

    @JsonProperty("street")
    private String street;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("postcode")
    private String postcode;

    @JsonProperty("coordinates")
    private CoordinatesDTO coordinates;

    @JsonProperty("timezone")
    private TimezoneDTO timezone;


}
