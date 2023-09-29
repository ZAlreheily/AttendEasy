package com.example.AttendEasy.User.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserResponse {
    private String firstName;

    private String lastName;

    private String mobileNumber;

    private String email;

    private String roles;

}
