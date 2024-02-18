package com.caresure.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@ToString
public class SubmitClaimDTO {
    private Double totalAmount;
    private String status;
    private String claimType;
    private String policyHolderName;
    private String policyHolderUsername;
    private String policyId;
    private String dateOfService;
    private String Diagnosis;
}
