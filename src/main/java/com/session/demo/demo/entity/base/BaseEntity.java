package com.session.demo.demo.entity.base;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @CreationTimestamp
    @Column(name = "created_date")
    private Instant createdDate;

    @PrePersist
    public void prePersist() {
        id = String.format("%s%s", getPrefix(), UUID.randomUUID().toString().toUpperCase());
    }

    public abstract String getPrefix();
}
