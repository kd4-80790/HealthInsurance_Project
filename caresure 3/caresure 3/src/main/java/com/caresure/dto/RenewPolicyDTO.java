package com.caresure.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RenewPolicyDTO {
    private Long policyId;
    private Long mobileNumber;
    private String dateOfBirth;
    private String username;

}
