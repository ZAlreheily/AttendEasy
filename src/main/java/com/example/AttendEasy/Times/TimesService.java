package com.example.AttendEasy.Times;

import com.example.AttendEasy.exceptions.EmployeeNotFoundException;

import java.util.List;

public interface TimesService {

    List<Times> getAllTimes(long id) throws EmployeeNotFoundException;

    List<Times> getAllCheckIns(long id) throws EmployeeNotFoundException;

    List<Times> getAllCheckOuts(long id) throws EmployeeNotFoundException;

}
