package com.session.demo.demo.dto.internalDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;

@Setter
@Getter
@ToString
public class AccountDTO {

    String id;

    BigDecimal activeBalance;

    String userDataId;

    String firstName;

    String lastName;

    String email;

    String phoneNo;

    String gender;

    Integer totalActivity;

    String accountTypeName;

    String createdDate;

    String updatedDate;

}
