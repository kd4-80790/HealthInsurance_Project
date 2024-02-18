package com.caresure.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "claims")
@Data
@ToString
public class Claims {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String policyName;
    private String policyId;
    private String transactionId;
    private Double amount;
    private int validity;
    private Double coverageAmount;
    private String status;
    private String claimType;
    private String policyHolderName;
    private String policyHolderUsername;
    private String policyNumber;
    private String dateOfService;
    private String Diagnosis;
    private String createdDate;


}
