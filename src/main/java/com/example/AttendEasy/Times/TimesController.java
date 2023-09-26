package com.example.AttendEasy.Times;

import com.example.AttendEasy.User.UserService;
import com.example.AttendEasy.utilities.MessageResponse;
import com.example.AttendEasy.utilities.ReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessions")
public class TimesController {
    private TimesService timesService;
    private UserService userService;
    @Autowired
    public TimesController(TimesService timesService, UserService userService) {
        this.timesService = timesService;
        this.userService = userService;
    }

    @GetMapping("/report/{employeeId}")
    ResponseEntity<ReportResponse> getReportForEmployee(@PathVariable long employeeId){
        return null;
    }

    @GetMapping("/report")
    List<ReportResponse> getAllReport(){
        return null;
    }

    @PostMapping("/checkin")
    ResponseEntity<MessageResponse> checkIn(@RequestHeader String Authorization){
        return null;
    }

    @PostMapping("/checkout")
    ResponseEntity<MessageResponse> checkOut(@RequestHeader String Authorization){
        return null;
    }

}
