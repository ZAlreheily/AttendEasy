package com.example.AttendEasy.User;


import com.example.AttendEasy.User.UserRequest.UserRequest;
import com.example.AttendEasy.User.UserResponse.UserResponse;
import com.example.AttendEasy.exceptions.AccessDeniedException;
import com.example.AttendEasy.exceptions.EmployeeNotFoundException;
import com.example.AttendEasy.exceptions.TimeMissmatchException;
import com.example.AttendEasy.utilities.ReportResponse;

import java.util.List;

public interface UserService {

    void checkIn(long employeeId) throws EmployeeNotFoundException, TimeMissmatchException;

    void checkOut(long employeeId) throws EmployeeNotFoundException, TimeMissmatchException;

    ReportResponse generateReportForEmployee(long employeeId, int month, int year) throws EmployeeNotFoundException, TimeMissmatchException;

    ReportResponse generateReportForEmployee(long employeeId,long managerId) throws EmployeeNotFoundException, AccessDeniedException, TimeMissmatchException;

    List<ReportResponse> generateReportForAllEmployees(long managerId) throws EmployeeNotFoundException;

    UserResponse getEmployee(long employeeId) throws EmployeeNotFoundException;


    void addUser(UserRequest userReq) throws EmployeeNotFoundException;

    long getIdByMobile(String mobile) throws EmployeeNotFoundException;
}
