package com.session.demo.demo.entity.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Setter
@Getter
@MappedSuperclass
public abstract class Parameterized extends OptimisticEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

}
