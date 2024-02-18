package com.caresure.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "admin")
@Data
@ToString
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String createdTime;
}
