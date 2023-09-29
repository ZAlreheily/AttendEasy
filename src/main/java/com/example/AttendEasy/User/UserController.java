package com.example.AttendEasy.User;

import com.example.AttendEasy.User.UserRequest.UserRequest;
import com.example.AttendEasy.User.UserResponse.UserResponse;
import com.example.AttendEasy.exceptions.EmployeeNotFoundException;
import com.example.AttendEasy.security.JwtService;
import com.example.AttendEasy.utilities.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserResponse> getUser(@RequestHeader String Authorization) throws EmployeeNotFoundException {
        String mobileNumber = JwtService.extractMobileWithOutBearer(Authorization);
        Long id = userService.getIdByMobile(mobileNumber);
        UserResponse user = userService.getEmployee(id);
        return new ResponseEntity<>(user, HttpStatus.FOUND);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('MANAGER')")
    ResponseEntity<MessageResponse> addUser(@RequestBody UserRequest userReq) throws EmployeeNotFoundException {
        userService.addUser(userReq);
        return new ResponseEntity<>(new MessageResponse("User has been successfully created"), HttpStatus.FOUND);
    }


}
