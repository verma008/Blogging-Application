package com.springboot.blogging.services;


import java.util.List;

import com.springboot.blogging.DTO.CommonResponse;
import com.springboot.blogging.DTO.UserRequestDTO;
import com.springboot.blogging.DTO.UserResponseDTO;

public interface UserService {
	
	public CommonResponse createUser(UserRequestDTO userDto);

	public List<UserResponseDTO> getAllUsers(Integer skip, Integer limit);

	public UserResponseDTO getSingleUser(Integer userId);

	public CommonResponse deleteUser(Integer userId);

	public CommonResponse updateUser(UserRequestDTO userRequestDTO, Integer userId);

	public UserResponseDTO getUserByUsername(String username);
}
