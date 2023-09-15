package com.example.AttendEasy.Employee;


import com.example.AttendEasy.Times.Times;
import com.example.AttendEasy.Times.TimesRepository;
import com.example.AttendEasy.exceptions.EmployeeNotFoundException;
import com.example.AttendEasy.exceptions.TimeMissmatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


@Service
public class EmployeeServiceImp implements EmployeeService{

    EmployeeRepository employeeRepository;

    TimesRepository timesRepository;

    @Autowired
    public EmployeeServiceImp(EmployeeRepository employeeRepository, TimesRepository timesRepository) {
        this.employeeRepository = employeeRepository;
        this.timesRepository = timesRepository;
    }


    @Override
    public void checkIn(long employeeId) throws EmployeeNotFoundException, TimeMissmatchException {
        Optional<Employee> found = employeeRepository.findById(employeeId);
        if (found.isEmpty())
            throw new EmployeeNotFoundException("This employee does not exist.");

        Optional<Times> timeFound = timesRepository.getLatestActivity(employeeId);
        if (timeFound.isPresent() && timeFound.get().getType().equalsIgnoreCase("IN")){
            throw new TimeMissmatchException("The employee must Check Out before Checking In.");
        }

        Times checkIn = new Times();
        Date currentTime = new Date();
        checkIn.setEmployee(found.get());
        checkIn.setType("IN");
        checkIn.setTime(currentTime);

        timesRepository.save(checkIn);

    }

    @Override
    public void checkOut(long employeeId) throws EmployeeNotFoundException, TimeMissmatchException {
        Optional<Employee> employeeFound = employeeRepository.findById(employeeId);
        if (employeeFound.isEmpty())
            throw new EmployeeNotFoundException("This employee does not exist.");

        Optional<Times> timeFound = timesRepository.getLatestActivity(employeeId);
        if (timeFound.isPresent() && timeFound.get().getType().equalsIgnoreCase("OUT")){
            throw new TimeMissmatchException("The employee must Check In before Checking Out.");
        }

        Times checkOut = new Times();
        Date currentTime = new Date();
        checkOut.setEmployee(employeeFound.get());
        checkOut.setType("OUT");
        checkOut.setTime(currentTime);

        timesRepository.save(checkOut);

    }
}
