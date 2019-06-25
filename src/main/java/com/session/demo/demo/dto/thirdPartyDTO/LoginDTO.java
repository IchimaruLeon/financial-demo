package com.session.demo.demo.dto.thirdPartyDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDTO {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("salt")
    private String salt;

    @JsonProperty("md5")
    private String md5;

    @JsonProperty("sha1")
    private String sha1;

    @JsonProperty("sha256")
    private String sha256;
}
