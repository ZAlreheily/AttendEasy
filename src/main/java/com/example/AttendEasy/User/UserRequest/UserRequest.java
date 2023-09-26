package com.example.AttendEasy.User.UserRequest;


import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserRequest {
    private String firstName;

    private String lastName;

    private String password;

    private String mobileNumber;

    private String email;

    private String roles;

    private String managerId;

}
