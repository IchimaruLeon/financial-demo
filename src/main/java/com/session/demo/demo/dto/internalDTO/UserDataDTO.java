package com.session.demo.demo.dto.internalDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserDataDTO {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNo;

    private String uuid;

    private String username;

    private String password;

    private String salt;

    private String md5;

    private String sha1;

    private String sha256;

    private String accountId;

    private String accountType;

}
