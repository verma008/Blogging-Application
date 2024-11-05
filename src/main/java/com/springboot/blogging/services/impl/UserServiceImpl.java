package com.springboot.blogging.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.blogging.DTO.CommonResponse;
import com.springboot.blogging.DTO.UserRequestDTO;
import com.springboot.blogging.DTO.UserResponseDTO;
import com.springboot.blogging.entities.Comment;
import com.springboot.blogging.entities.Post;
import com.springboot.blogging.entities.User;
import com.springboot.blogging.exceptions.AlreadyExistException;
import com.springboot.blogging.exceptions.ApiMessageException;
import com.springboot.blogging.exceptions.ResourceNotFoundException;
import com.springboot.blogging.exceptions.UnauthorizedException;
import com.springboot.blogging.repository1.CommentRepository;
import com.springboot.blogging.repository1.PostRepository;
import com.springboot.blogging.repository1.UserRepository;
import com.springboot.blogging.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	
	@Override
	public CommonResponse createUser(UserRequestDTO userDto) {
		
		User dbuser = this.userRepository.findByEmail(userDto.getEmail());
		User user=this.dtoToUser(userDto);
		CommonResponse response=new CommonResponse();
		try {
			if(dbuser!=null)
			{
				throw new AlreadyExistException("User is already exist in database");
			}
			else{
				user.setPassword(this.bCryptPasswordEncoder.encode(userDto.getPassword()));
				
				User savedUser = this.userRepository.save(user);
				response.setMessage("User Created Successfully");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.setMessage("User is already exist in database.Please create another user");
		}
		return response;
	}


	
	private User dtoToUser(UserRequestDTO userDto)
	{
		User user=this.modelMapper.map(userDto,User.class);
		return user;
	}

	

	@Override
	public List<UserResponseDTO> getAllUsers(Integer skip,Integer limit) {
		
		// Adding Pagination
		
		Pageable pageable=PageRequest.of(skip, limit);
		List<User> user1=this.userRepository.findAll(pageable).getContent();
		
		List<UserResponseDTO> response=new ArrayList<>();
		for(User u:user1)
		{
			UserResponseDTO u1=this.modelMapper.map(u, UserResponseDTO.class);
			response.add(u1);
		}
		return response;
	}


	@Override
	public UserResponseDTO getSingleUser(Integer userId){
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		return this.modelMapper.map(user, UserResponseDTO.class);
	}


	/*Before delete the user check for their post,post should be deleted first before deleteing the user and instead of deleting permanent deactivate the account only 
	Also add check for deleting the post comments too before deleting post. */
	@Override
	public CommonResponse deleteUser(Integer userId) {	
		
		User user= this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		CommonResponse response=new CommonResponse();
		List<Post> post = this.postRepository.findByUserId(userId);
		List<Comment> comment = this.commentRepository.findByUserId(userId);
		
		if(post.isEmpty()) {
			response.setMessage("Not able to delete");
		}else {
		this.commentRepository.deleteAll(comment);
		this.postRepository.deleteAll(post);
//		this.userRepository.delete(user);
		
		user.setActive(false);
		this.userRepository.save(user);

		response.setMessage("User deleted successfully");
		}
		return response;
	}



	@Override
	public CommonResponse updateUser(UserRequestDTO userRequestDTO, Integer userId) {
		
		// Add check for username after adding spring security : frist grep the userId from token and then match with it < PENDING HAI>
		
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		user.setName(userRequestDTO.getName());
		user.setPassword(userRequestDTO.getPassword());
//		user.setEmail(userRequestDTO.getEmail());
		user.setAbout(userRequestDTO.getAbout());
		user.setRole(userRequestDTO.getRole());
		this.userRepository.save(user);
		return new CommonResponse("User updated successfully");
	}



	@Override
	public UserResponseDTO getUserByUsername(String username) {
			
		User user = this.userRepository.findByEmail(username);
		if(user!=null)
		{
		UserResponseDTO userResponseDTO = this.modelMapper.map(user, UserResponseDTO.class);
	    return userResponseDTO;}
		else {
			throw new ApiMessageException("Something is wrong. Please varify once.....");
		}
		
}}
