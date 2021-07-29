package com.hotel.authenticationservice.controller;

import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.authenticationservice.pojo.User;
import com.hotel.authenticationservice.service.AuthenticationService;



@RestController
@RequestMapping(value="/AuthenticationAPI")
public class AuthenicationController {

	@Autowired
	AuthenticationService authService;
	
	private static Logger log=Logger.getLogger(AuthenicationController.class);
	
	// to determine if the user is valid registered user
	
	@PostMapping("/getUser")
	public ResponseEntity<User> findUserDetail(@RequestBody @Valid User user){
		
		log.debug(user.toString());
		User userResult=authService.getUserRole(user);
		log.debug("user details retrieved"+userResult.toString());
		return new ResponseEntity<>(userResult,HttpStatus.OK);
	}

	
	// to register a new user 
	
	@PostMapping("/addUser")
	public ResponseEntity<String> addUser(@RequestBody @Valid User user){
		user.setRole("user");
		log.debug(user.toString());
		String result=authService.addNewUser(user);
		
		return new ResponseEntity<String>(result,HttpStatus.OK);
	}
	
	@GetMapping("/getUserInfo/{registrationId}")
	public ResponseEntity<User> getUserInfo(int registrationId){
		User result=authService.getuserInfo(registrationId);
		return new ResponseEntity<User>(result,HttpStatus.OK);
	}

//	@PostMapping("/resetPassword")
//	public ResponseEntity<String> resetUser(@RequestBody Response user){
//		System.out.println(user.toString());
//		String result=authService.resetPassword(user);
//		return new ResponseEntity<String>(HttpStatus.OK);
//	}
//	
	
}
