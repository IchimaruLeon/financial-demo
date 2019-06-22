package com.session.demo.demo.dto.thirdPartyDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SampleUserDTO {

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("name")
    private NamesDTO name;

    @JsonProperty("location")
    private LocationDTO location;

    @JsonProperty("email")
    private String email;

    @JsonProperty("login")
    private LoginDTO login;

    @JsonProperty("dob")
    private DobDTO dob;

    @JsonProperty("registered")
    private RegisteredDTO registered;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("cell")
    private String cell;

    @JsonProperty("id")
    private IdSampleDTO id;

    @JsonProperty("picture")
    private PictureDTO picture;

    @JsonProperty("nat")
    private String nat;



}
