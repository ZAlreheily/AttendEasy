package com.example.AttendEasy.User.UserInfo;

import com.example.AttendEasy.User.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserInfoDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails getUserByPhoneNumber(String phoneNumber) throws UsernameNotFoundException {
        Optional<User> userInfo = userRepository.getUserByMobileNumber(phoneNumber);
        return userInfo.map( UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Wrong mobile number"));
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        return getUserByPhoneNumber( phoneNumber );
    }
}
