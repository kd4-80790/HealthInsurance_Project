package com.caresure.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "policy")
@Data
@ToString
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String status;
    private Long amount;
    private String createdDate;
    private String modifiedDate;
}
