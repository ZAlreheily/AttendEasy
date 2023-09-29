package com.example.AttendEasy.Times;

import com.example.AttendEasy.User.UserService;
import com.example.AttendEasy.exceptions.AccessDeniedException;
import com.example.AttendEasy.exceptions.EmployeeNotFoundException;
import com.example.AttendEasy.exceptions.TimeMissmatchException;
import com.example.AttendEasy.security.JwtService;
import com.example.AttendEasy.utilities.MessageResponse;
import com.example.AttendEasy.utilities.ReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessions")
public class TimesController {
    private final TimesService timesService;
    private final UserService userService;

    @Autowired
    public TimesController(TimesService timesService, UserService userService) {
        this.timesService = timesService;
        this.userService = userService;
    }

    @GetMapping("/report/{employeeId}")
    @PreAuthorize("hasAuthority('MANAGER')")
    ResponseEntity<ReportResponse> getReportForEmployee(@RequestHeader String Authorization, @PathVariable long employeeId) throws EmployeeNotFoundException, TimeMissmatchException, AccessDeniedException {
        String mobileNumber = JwtService.extractMobileWithOutBearer(Authorization);
        Long id = userService.getIdByMobile(mobileNumber);
        return new ResponseEntity<>(userService.generateReportForEmployee(employeeId,id),HttpStatus.OK);
    }

    @GetMapping("/report")
    @PreAuthorize("hasAuthority('MANAGER')")
    List<ReportResponse> getAllReport(@RequestHeader String Authorization) throws EmployeeNotFoundException {
        String mobileNumber = JwtService.extractMobileWithOutBearer(Authorization);
        Long id = userService.getIdByMobile(mobileNumber);
        return userService.generateReportForAllEmployees(id);
    }

    @PostMapping("/checkin")
    ResponseEntity<MessageResponse> checkIn(@RequestHeader String Authorization) throws EmployeeNotFoundException, TimeMissmatchException {
        String mobileNumber = JwtService.extractMobileWithOutBearer(Authorization);
        Long id = userService.getIdByMobile(mobileNumber);
        userService.checkIn(id);
        return new ResponseEntity<>(new MessageResponse("Checked In Successfully"), HttpStatus.OK);
    }

    @PostMapping("/checkout")
    ResponseEntity<MessageResponse> checkOut(@RequestHeader String Authorization) throws EmployeeNotFoundException, TimeMissmatchException {
        String mobileNumber = JwtService.extractMobileWithOutBearer(Authorization);
        Long id = userService.getIdByMobile(mobileNumber);
        userService.checkOut(id);
        return new ResponseEntity<>(new MessageResponse("Checked Out Successfully"), HttpStatus.OK);
    }

}
