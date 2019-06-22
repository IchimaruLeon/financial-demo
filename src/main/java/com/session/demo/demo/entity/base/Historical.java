package com.session.demo.demo.entity.base;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Setter
@Getter
@MappedSuperclass
public abstract class Historical extends OptimisticEntity {

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean deleted = Boolean.FALSE;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private Instant updatedDate;

}
