package com.springboot.blogging.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blogging.DTO.CommentRequestDTO;
import com.springboot.blogging.DTO.CommentResponseDTO;
import com.springboot.blogging.DTO.CommonResponse;
import com.springboot.blogging.DTO.PostResponseDTO;
import com.springboot.blogging.DTO.UserResponseDTO;
import com.springboot.blogging.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController{
	
	
	@Autowired
	private CommentService commentService;
	
	
	// create comment
	@PostMapping("/comment")
	public ResponseEntity<CommentResponseDTO> createComment(@RequestBody CommentRequestDTO commentDto)
	{
		CommentResponseDTO createdComment = this.commentService.createComment(commentDto);
		return new ResponseEntity<CommentResponseDTO>(createdComment,HttpStatus.CREATED);
	}
	
	
	// delete comment
	@DeleteMapping("/comment/{commentId}/{userId}")
	public ResponseEntity<CommonResponse> deleteComment(@PathVariable Integer commentId,@PathVariable Integer userId)
	{
		CommonResponse deleteComment = this.commentService.deleteComment(commentId,userId);
		return new ResponseEntity<CommonResponse>(deleteComment,HttpStatus.OK);
	}
	
	
	// api to fetch post details using commentId.
	@GetMapping("/{commentId}/post")
	public PostResponseDTO getPostDetails(@PathVariable Integer commentId){
		return this.commentService.getPostDetails(commentId);
	}
	
	
	//api to fetch user details using commentId.
	@GetMapping("/{commentId}/user")
	public UserResponseDTO getUserDetails(@PathVariable Integer commentId){
		return this.commentService.getUserDetails(commentId);
	}
		
	
   // api to fetch user details that comments on post using commentId.
	@GetMapping("/{commentId}/users")
	public UserResponseDTO userDetails(@PathVariable Integer commentId)
	{
		return this.commentService.userDetails(commentId);
	}

	
}
