package com.hotel.authenticationservice.service;

import org.springframework.stereotype.Service;

import com.hotel.authenticationservice.pojo.User;


@Service
public interface AuthenticationService {

	User getUserRole(User user);

	String addNewUser(User user);

	User getuserInfo(int registrationId);

}
