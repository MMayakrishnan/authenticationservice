package com.hotel.authenticationservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.hotel.authenticationservice.exception.UserIncorrectException;
import com.hotel.authenticationservice.pojo.User;
import com.hotel.authenticationservice.repository.UserRepository;
import com.hotel.authenticationservice.service.impl.AuthenticationServiceImpl;


@SpringBootTest
public class AuthenticationServiceImplTest {


	@Mock
	UserRepository userRepo;
	
	@InjectMocks
	AuthenticationServiceImpl authenticationServiceImpl;

	@BeforeEach
	    public  void init() {
	        MockitoAnnotations.initMocks(this);
	    }

	@Test
	public void getUserRoleExceptionPathTest() {
		User input=new User(1, "admin", "adminas","admin@gmaill.com" , null);
		User user=new User(2, "admin", "YWRtaW4=","admin@gmaill.com" , null);
		Optional<User> userOpt=Optional.of(user);
		when(userRepo.findByEmailId(Mockito.anyString())).thenReturn(userOpt);
		try {
			authenticationServiceImpl.getUserRole(input);
		}catch(UserIncorrectException ex) {
			assertEquals("Username or password entered is Incorrect", ex.getMessage());
		}
	}
	
	@Test
	public void getUserRoleHappyPathTest() {
		User input=new User(1, "admin", "admin","admin@gmaill.com" , null);
		User user=new User(2, "admin", "YWRtaW4=","admin@gmaill.com" , null);
		Optional<User> userOpt=Optional.of(user);
		when(userRepo.findByEmailId(Mockito.anyString())).thenReturn(userOpt);
		User result=authenticationServiceImpl.getUserRole(input);
		assertEquals(2,result.getRegistrationId());
		
	}
	
	@Test
	public void addNewUserExceptionPathTest() {
		User user=new User(2, "admin", "YWRtaW4=","admin@gmaill.com" , null);
		Optional<User> userOpt=Optional.ofNullable(user);
		when(userRepo.findByEmailId(Mockito.anyString())).thenReturn(userOpt);
			authenticationServiceImpl.addNewUser(user);
			assertEquals("User Already Registered, Please Sign In", authenticationServiceImpl.addNewUser(user));
	}
	
	@Test
	public void addNewUserHappyPathTest() {
		User input=new User(2, "admin", "YWRtaW4=","admin@gmaill.com" , null);
		Optional<User> userOpt=Optional.ofNullable(null);
		when(userRepo.findByEmailId(Mockito.anyString())).thenReturn(userOpt);
		when(userRepo.save(Mockito.any())).thenReturn(input);
		assertEquals("User Registered", authenticationServiceImpl.addNewUser(input));
	}

}
