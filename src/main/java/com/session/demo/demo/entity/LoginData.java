package com.session.demo.demo.entity;

import com.session.demo.demo.entity.base.Historical;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@ToString
@Table(name = "login_data")
public class LoginData extends Historical {

    private static final String PREFIX = "LGD";

    private String uuid;

    private String username;

    private String password;

    private String salt;

    private String md5;

    private String sha1;

    private String sha256;

    @Override
    public String getPrefix() {
        return PREFIX;
    }
}
