package com.springboot.blogging.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blogging.DTO.CommonResponse;
import com.springboot.blogging.DTO.UserRequestDTO;
import com.springboot.blogging.DTO.UserResponseDTO;
import com.springboot.blogging.services.UserService;

@RestController
//no need to add api as prefix
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
//	// testing api
//	@GetMapping("/data")
//	public String getData()
//	{
//		return "Hey";
//	}
	
	
	// API to create user
	@PostMapping("/create")
	public ResponseEntity<CommonResponse> createUser(@RequestBody UserRequestDTO userDto)
	{
		CommonResponse createdUser = this.userService.createUser(userDto);
		return new ResponseEntity<CommonResponse>(createdUser,HttpStatus.OK);
	}
	

	
	// API to get single users
	@GetMapping("/{userId}")
	public ResponseEntity<UserResponseDTO> getSingleUser(@PathVariable("userId") Integer userId)
	{
		return new ResponseEntity<UserResponseDTO>(this.userService.getSingleUser(userId),HttpStatus.OK);
	}
	
	
	// Update user
	@PutMapping("/{userId}")
	public ResponseEntity<CommonResponse> updateUser(@RequestBody UserRequestDTO userRequestDTO, @PathVariable Integer userId)
	{
		CommonResponse updatedUser=this.userService.updateUser(userRequestDTO,userId);
		return new ResponseEntity<CommonResponse>(updatedUser,HttpStatus.OK);
	}
	
	
	// API to delete user
	@DeleteMapping("/{userId}")
	public ResponseEntity<CommonResponse> deleteUser(@PathVariable Integer userId)
	{
		CommonResponse deletedUser=this.userService.deleteUser(userId);
		return new ResponseEntity<CommonResponse>(deletedUser,HttpStatus.NOT_FOUND);
	}
	
	// API to get all users
	@GetMapping("/all-users/{skip}/{limit}")
	public List<UserResponseDTO> getAllUsers(@PathVariable Integer skip, @PathVariable Integer limit)
	{
		List<UserResponseDTO> users = this.userService.getAllUsers(skip,limit);	
		return users;
	}
	
	
	// API to get user by username
	@GetMapping("/ByUsername")
	public ResponseEntity<UserResponseDTO> getUserByUsername(@RequestParam("username") String username)
	{
		UserResponseDTO byUsername = this.userService.getUserByUsername(username);
		return new ResponseEntity<UserResponseDTO>(byUsername, HttpStatus.OK);
		
	}
		
	
		
	
 }
