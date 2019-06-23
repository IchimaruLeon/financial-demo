package com.session.demo.demo.dto.internalDTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;

/**
 * Base class for Api Request contains refCode
 */
@Setter
@Getter
public class RequestPageDTO {

    private Integer offset = 0;

    private Integer limit = 20;

    private String orderBy = "createdTimestamp";

    private Sort.Direction direction = Sort.Direction.DESC;

    private BigDecimal totalAmount;

    public Integer getPage() {
        return offset == null ? 0 : offset / limit;
    }

    public Pageable getPageable() {
        return direction == Sort.Direction.ASC ? PageRequest.of(getPage(), limit, Sort.by(orderBy).ascending()) : PageRequest.of(getPage(), limit, Sort.by(orderBy).descending());
    }
}
