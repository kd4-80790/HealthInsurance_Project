package com.caresure.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "transactions")
@Data
@ToString
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private Long policyId;
    private Long amount;
    private String status;
    private String transactionDate;
}
