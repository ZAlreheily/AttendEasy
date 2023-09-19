package com.example.AttendEasy.utilities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;
import java.util.Date;

@Data
@AllArgsConstructor
public class SessionResponse {
    private Date timeIn;
    private Date timeOut;
    private Duration duration;
}
