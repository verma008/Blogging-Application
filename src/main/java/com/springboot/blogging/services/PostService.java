package com.springboot.blogging.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.blogging.DTO.CategoryResponseDTO;
import com.springboot.blogging.DTO.CommonResponse;
import com.springboot.blogging.DTO.PostRequestDTO;
import com.springboot.blogging.DTO.PostResponseDTO;
import com.springboot.blogging.DTO.UserResponseDTO;

public interface PostService {

	CommonResponse createPost(PostRequestDTO postRequestDTO, MultipartFile file) throws IOException;

	List<PostResponseDTO> getAllPost(Integer skip, Integer limit);

	PostResponseDTO getSinglePost(Integer postId);

	CommonResponse deletePost(Integer postId);

	List<PostResponseDTO> getAllPostByUser(Integer userId);

	List<PostResponseDTO> getAllPostByCategory(Integer categoryId);

	List<PostResponseDTO> searchPost(String keyword);

	CommonResponse updatePost(PostRequestDTO postRequestDTO, MultipartFile file, Integer userId,Integer postId);
	
	// ek user ke pass ek category ki kitni post hai
	List<PostResponseDTO> getAllPostByUserWithSameCategory(Integer userId, Integer categoryId);

	CategoryResponseDTO getCategoryDetails(Integer postId);

	UserResponseDTO getUserDetails(Integer postId);

	List<PostResponseDTO> postByKeywords(String keywords);

}
