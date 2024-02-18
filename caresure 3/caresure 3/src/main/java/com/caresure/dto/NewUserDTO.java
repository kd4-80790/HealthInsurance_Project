package com.caresure.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NewUserDTO {
    private String name;
    private String username;
    private String password;
    private String email;
    private int age;
    private String dateOfBirth;
    private Long mobileNumber;
    private String gender;
    private String address;
    private String city;
    private String country;
}
