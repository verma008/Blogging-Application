package com.springboot.blogging.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blogging.DTO.CommentRequestDTO;
import com.springboot.blogging.DTO.CommentResponseDTO;
import com.springboot.blogging.DTO.CommonResponse;
import com.springboot.blogging.DTO.PostResponseDTO;
import com.springboot.blogging.DTO.UserResponseDTO;
import com.springboot.blogging.entities.Comment;
import com.springboot.blogging.entities.Post;
import com.springboot.blogging.entities.User;
import com.springboot.blogging.exceptions.ResourceNotFoundException;
import com.springboot.blogging.exceptions.UnauthorizedException;
import com.springboot.blogging.repository1.CommentRepository;
import com.springboot.blogging.repository1.PostRepository;
import com.springboot.blogging.repository1.UserRepository;
import com.springboot.blogging.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentResponseDTO createComment(CommentRequestDTO commentDto) {
		Comment comment = this.dtoToComment(commentDto);
		Comment newComment = this.commentRepository.save(comment);
		return this.modelMapper.map(newComment, CommentResponseDTO.class);
	}

	
	private Comment dtoToComment(CommentRequestDTO commentDto) {
		return this.modelMapper.map(commentDto, Comment.class);
	}
	

	
	@Override
	public CommonResponse deleteComment(Integer commentId, Integer userId) {

		Comment comment = this.commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));

		try {
			if (userId == comment.getUserId()) {
				this.commentRepository.delete(comment);
			} else {
				throw new UnauthorizedException("User is not valid to delete this comment.");
			}
		} catch (UnauthorizedException e) {
			e.printStackTrace();
			return new CommonResponse("User is not valid to delete this comment.");
		} catch (Exception e) {
			e.printStackTrace();
			return new CommonResponse("User is not valid to delete this comment.");
		}
		return new CommonResponse("User deleted successfully");
	}

	
	
	@Override
	public PostResponseDTO getPostDetails(Integer commentId) {
	
		Comment comment = this.commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Id", commentId));
		int postId = comment.getPostId();
		Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId));
		return this.modelMapper.map(post, PostResponseDTO.class);
	}


	@Override
	public UserResponseDTO getUserDetails(Integer commentId) {
		Comment comment = this.commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Id", commentId));
		int userId = comment.getUserId();
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		return this.modelMapper.map(user, UserResponseDTO.class);
	}


	@Override
	public UserResponseDTO userDetails(Integer commentId) {
		Comment comment = this.commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Id", commentId));
		int postId = comment.getPostId();
		Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId));
		int userId = post.getUserId();
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		return this.modelMapper.map(user, UserResponseDTO.class);
	}
}

