package com.session.demo.demo.entity.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@Setter
@Getter
@MappedSuperclass
public abstract class OptimisticEntity extends BaseEntity {

    @Version
    @Column(name = "version")
    private Integer version;
}
