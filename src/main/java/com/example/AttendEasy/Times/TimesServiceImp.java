package com.example.AttendEasy.Times;

import com.example.AttendEasy.exceptions.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimesServiceImp implements TimesService {

    TimesRepository timesRepository;

    @Autowired
    public TimesServiceImp(TimesRepository timesRepository) {
        this.timesRepository = timesRepository;
    }

    @Override
    public List<Times> getAllTimes(long employeeId) throws EmployeeNotFoundException {
        return timesRepository.getAllTimes(employeeId);
    }

}
