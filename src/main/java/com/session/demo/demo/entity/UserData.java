package com.session.demo.demo.entity;

import com.session.demo.demo.entity.base.Historical;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@Table(name = "user_data")
public class UserData extends Historical {

    private static final String PREFIX = "USD";

    private String firstName;

    private String lastName;

    private String email;

    private String gender;

    private String phoneNo;

    @OneToOne(targetEntity = LoginData.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private LoginData loginData;

    @Override
    public String getPrefix() {
        return PREFIX;
    }
}
