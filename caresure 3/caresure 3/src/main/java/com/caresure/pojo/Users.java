package com.caresure.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@ToString
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private Long mobileNumber;
    private String dateOfBirth;
    private int age;
    private String gender;
    private String address;
    private String city;
    private String country;
    private String role;
    private String active;
    private Long policyId;
    private String token;
    private String modifiedTime;
    private String createdTime;

}
