package com.springboot.blogging.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.blogging.DTO.CategoryResponseDTO;
import com.springboot.blogging.DTO.CommonResponse;
import com.springboot.blogging.DTO.PostRequestDTO;
import com.springboot.blogging.DTO.PostResponseDTO;
import com.springboot.blogging.DTO.UserResponseDTO;
import com.springboot.blogging.services.PostService;

@RestController
@RequestMapping("api/post")
public class PostController{
	
	@Autowired
	private PostService postService;
	
	
	
	
	// create post
	
	@PostMapping(value="/create", consumes= {"multipart/form-data", "application/json"}, produces= {"application/json"})
	public ResponseEntity<CommonResponse> createPost(@RequestPart PostRequestDTO postRequestDTO,@RequestPart(value="file",required=false) MultipartFile file) throws IOException
	{
		 return new ResponseEntity<CommonResponse>(postService.createPost(postRequestDTO,file),HttpStatus.CREATED);
	}
	
	
	// get Single post
	@GetMapping("/{postId}")
	public PostResponseDTO getSinglePost(@PathVariable Integer postId)
	{
		return this.postService.getSinglePost(postId);
	}
	
	
	// API to update post
	@PutMapping(value="user/{userId}/update/{postId}", consumes= {"multipart/form-data", "application/json"}, produces= {"application/json"})
	public ResponseEntity<CommonResponse> updatePost(@RequestPart PostRequestDTO postRequestDTO,@RequestPart(value="file",required=false) MultipartFile file,@PathVariable Integer userId, @PathVariable Integer postId)
	{
		return new ResponseEntity<CommonResponse>(postService.updatePost(postRequestDTO,file,userId,postId),HttpStatus.OK);
	}
	
	// delete post
	@DeleteMapping("/{postId}")
	public ResponseEntity<CommonResponse> deletePost(@PathVariable Integer postId)
	{
		CommonResponse response=this.postService.deletePost(postId);
		return new ResponseEntity<CommonResponse>(response,HttpStatus.NOT_FOUND);
	}
	
	// get all post
	@GetMapping("/{skip}/{limit}")
	public List<PostResponseDTO> getAllPost(@PathVariable Integer skip, @PathVariable Integer limit)
	{
		List<PostResponseDTO> posts = this.postService.getAllPost(skip,limit);	
		return posts;
	}
	
	// get all post by user(ek user ke pass kitne post hai)
	@GetMapping("/user/{userId}")
	public List<PostResponseDTO> getAllPostByUser(@PathVariable Integer userId)
	{
		List<PostResponseDTO> allPostByUser = this.postService.getAllPostByUser(userId);
		return allPostByUser;
	}
	
	
	// get all post by user and category (ek user ke pass ek category ki kitni post hai)
	@GetMapping("/user/{userId}/category/{categoryId}")
	public List<PostResponseDTO> getAllPostByUserWithSameCategory(@PathVariable Integer userId, @PathVariable Integer categoryId)
	{
		List<PostResponseDTO> allPostOfUserWithSameCategory=this.postService.getAllPostByUserWithSameCategory(userId,categoryId);
		return allPostOfUserWithSameCategory;
	}
	
	
	
	// get all post by category (ek category me kitne post hai)
	@GetMapping("/category/{categoryId}")
	public List<PostResponseDTO> getAllPostByCategory(@PathVariable Integer categoryId)
	{ 
		List<PostResponseDTO> allPostByCategory = this.postService.getAllPostByCategory(categoryId);
		return allPostByCategory;
	}
	
	
	// Api to get category details from postId  
	@GetMapping("/{postId}/category")
	public CategoryResponseDTO getCategoryDetails(@PathVariable Integer postId)
	{
		return this.postService.getCategoryDetails(postId);
	}
	
	
	// search post
	@GetMapping("/search/{keyword}")
	public List<PostResponseDTO> searchPost(@PathVariable("keyword") String keyword){
		List<PostResponseDTO> searchPosts = this.postService.searchPost(keyword);
		return searchPosts;
	}
	
	

	// api to get user details from postId.
	@GetMapping("/{postId}/user")
	public UserResponseDTO getUserDetails(@PathVariable Integer postId)
	{
		return this.postService.getUserDetails(postId);
	}
	

	
	
	// api to get the list of posts using keywords. as discussed.
	/*ye nhi ho payega nhi aa rha hai */
	
	@GetMapping("/postByKeyword/{keywords}")
	public List<PostResponseDTO> postByKeywords(@PathVariable("keywords") String keywords)
	{
		return this.postService.postByKeywords(keywords);
	}
	
}
