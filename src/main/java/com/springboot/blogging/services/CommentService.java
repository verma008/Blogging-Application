package com.springboot.blogging.services;

import com.springboot.blogging.DTO.CommentRequestDTO;
import com.springboot.blogging.DTO.CommentResponseDTO;
import com.springboot.blogging.DTO.CommonResponse;
import com.springboot.blogging.DTO.PostResponseDTO;
import com.springboot.blogging.DTO.UserResponseDTO;

public interface CommentService {

	CommentResponseDTO createComment(CommentRequestDTO commentDto);

	CommonResponse deleteComment(Integer commentId, Integer userId);

	PostResponseDTO getPostDetails(Integer commentId);

	UserResponseDTO getUserDetails(Integer commentId);

	UserResponseDTO userDetails(Integer commentId);

}
