package com.hotel.authenticationservice.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.hotel.authenticationservice.exception.UserIncorrectException;
import com.hotel.authenticationservice.exception.UserNameIsNullException;
import com.hotel.authenticationservice.pojo.User;
import com.hotel.authenticationservice.repository.UserRepository;
import com.hotel.authenticationservice.service.AuthenticationService;
import java.util.Base64;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
	
	@Autowired
	UserRepository userRepo;
	
	private static Logger log=LoggerFactory.getLogger(AuthenticationServiceImpl.class);
	
	
	// retrieve user with the email id and base 64 decode the password saved in db and compare with the password passed in parameter
	@Override
	public User getUserRole(User user) {
		Optional<User> userOpt=userRepo.findByEmailId(user.getEmailId());
		if(userOpt.isPresent()&&
				new String(Base64.getDecoder().decode(userOpt.get().getPassword())).equalsIgnoreCase(user.getPassword())){
			log.info("User detail available for "+user.getEmailId());
			return userOpt.get();
		}else {
			log.error("Username or password entered is Incorrect ");
			throw new UserIncorrectException("Username or password entered is Incorrect");
		}
	}

	// checks if user is already register if not save the user after encoding the password 
	@Override
	@Transactional(rollbackOn = DataAccessException.class)
	public String addNewUser(User user) {
		if(user.getUserName()==null||user.getUserName().isBlank()) {
			log.error("Username is blank for the user with mail id "+user.getEmailId());
			throw new UserNameIsNullException("Enter a valid username");
		}
		Optional<User> userOpt=userRepo.findByEmailId(user.getEmailId());
		if(userOpt.isPresent()){
			log.info("User Already Registered"+user.getEmailId());
			return "User Already Registered, Please Sign In";
		}else{
				user.setPassword(Base64.getEncoder().encodeToString(user.getPassword().getBytes()));
				userRepo.save(user);
				log.info("New User registered Succesfully"+user.getEmailId());
				return "User Registered";
		}
	}

	// retrieves user detail using registration id
	@Override
	public User getuserInfo(int registrationId) {
		Optional<User> userOpt=userRepo.findById(registrationId);
		if(userOpt.isPresent())
			return userOpt.get();
		else
			throw new UserIncorrectException("No User Available present for the given registration Number");
	}


}
