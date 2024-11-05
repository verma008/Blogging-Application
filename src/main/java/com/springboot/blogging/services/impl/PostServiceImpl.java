package com.springboot.blogging.services.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.blogging.DTO.CategoryResponseDTO;
import com.springboot.blogging.DTO.CommonResponse;
import com.springboot.blogging.DTO.PostRequestDTO;
import com.springboot.blogging.DTO.PostResponseDTO;
import com.springboot.blogging.DTO.UserResponseDTO;
import com.springboot.blogging.entities.Category;
import com.springboot.blogging.entities.Post;
import com.springboot.blogging.entities.User;
import com.springboot.blogging.exceptions.ApiMessageException;
import com.springboot.blogging.exceptions.ResourceNotFoundException;
import com.springboot.blogging.exceptions.UnauthorizedException;
import com.springboot.blogging.repository1.CategoryRepository;
import com.springboot.blogging.repository1.PostRepository;
import com.springboot.blogging.repository1.UserRepository;
import com.springboot.blogging.services.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Value("${basefilepath}")
	private String path;
	
	
	@Override
	public CommonResponse createPost(PostRequestDTO postRequestDTO, MultipartFile file) throws IOException {
		Category category = this.categoryRepository.findById(postRequestDTO.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("Catgory","categoryId", postRequestDTO.getCategoryId()));
		Post post = this.modelMapper.map(postRequestDTO, Post.class);
		String uploadFilePath = uploadFile(file);
		post.setFileName(uploadFilePath);
		post.setAddedDate(new Date());
	    this.postRepository.save(post);
		return new CommonResponse("Post created Successfully");		
	}
	
	public String uploadFile(MultipartFile file)
	{
		try {
			   // Get original file name
	        String originalFileName = file.getOriginalFilename();
	        
	        if (originalFileName == null || originalFileName.isEmpty()) {
                throw new IOException("Invalid file name");
            }
	        
	        // Generate a random ID and concatenate it with the file extension
	        String newFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf("."));
		
	     // Construct the full path for the file
	        String fullPath = path + File.separator + newFileName;
	        System.out.println(fullPath);
		
		// create Folder if not created
		File f=new File(path);
		if(!f.exists())
		{
			f.mkdir();
		}

		 // Copy the file to the target location
        Files.copy(file.getInputStream(), Paths.get(fullPath));
        // Return the new file name
        return newFileName;
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			return " ";
		}
		
	}

	@Override
	public List<PostResponseDTO> getAllPost(Integer skip, Integer limit) {	
	
		// adding pagination
		Pageable pageable=PageRequest.of(skip, limit);
		
		List<Post> posts = this.postRepository.findAll(pageable).getContent();
		List<PostResponseDTO> list = posts.stream().map((post)->this.modelMapper.map(post, PostResponseDTO.class)).collect(Collectors.toList());
		return list;
	}

	@Override
	public PostResponseDTO getSinglePost(Integer postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		return this.modelMapper.map(post, PostResponseDTO.class);
	}

	//Add check for deleting the post comments too before deleting post.
	@Override
	public CommonResponse deletePost(Integer postId) {
		Post deletedPost = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id" , postId));
		this.postRepository.delete(deletedPost);
		return new CommonResponse("Post Deleted Successfully");
	}

	@Override
	public List<PostResponseDTO> getAllPostByUser(Integer userId) {
		
		// Adding pagination
		 
		 User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		 
		 List<Post> posts=this.postRepository.findByUserId(userId);
		 List<PostResponseDTO> postResponseDto = posts.stream().map((post) -> this.modelMapper.map(post, PostResponseDTO.class)).collect(Collectors.toList());
		 return postResponseDto;
		
		
	}

	@Override
	public List<PostResponseDTO> getAllPostByCategory(Integer categoryId) {
		
		// Adding pagination
		
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		
		List<Post> posts = this.postRepository.findByCategoryId(categoryId);
		List<PostResponseDTO> postResponseDtos = posts.stream().map((post)->this.modelMapper.map(post, PostResponseDTO.class)).collect(Collectors.toList());
				
		return postResponseDtos;
	}

	@Override
	public List<PostResponseDTO> searchPost(String keyword) {
		
		// Adding Pagination
		
		List<Post> posts=this.postRepository.findByPostTitleContaining(keyword);
		List<PostResponseDTO> list = posts.stream().map((post)->this.modelMapper.map(post, PostResponseDTO.class)).collect(Collectors.toList());
		return list;
	}



	@Override
	public CommonResponse updatePost(PostRequestDTO postRequestDTO, MultipartFile file, Integer userId,Integer postId) {
		
		// Add check for userId before performing updation, if id not matched throw unauthorized exception <DONE>
		Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		 
		if(post.getUserId()==userId)
		{
		post.setPostTitle(postRequestDTO.getPostTitle());
		post.setContant(postRequestDTO.getContant());
		String uploadFilePath = uploadFile(file);
		post.setFileName(uploadFilePath);
		post.setAddedDate(new Date());
		post.setCategoryId(postRequestDTO.getCategoryId());
		post.setUserId(postRequestDTO.getUserId());
		this.postRepository.save(post);
		return new CommonResponse("Post Details updated successfully");
		}else
		{
			throw new UnauthorizedException("This user is not authororized to update this post. User can update own post only not for other's post ");
		}
	}

	@Override
	public List<PostResponseDTO> getAllPostByUserWithSameCategory(Integer userId, Integer categoryId){

		List<Post> posts = this.postRepository.findByUserIdAndCategoryId(userId, categoryId);
		if(posts.isEmpty())
		{
			 throw new ApiMessageException("UserId " + userId + " doesn't have any post in categoryId " + categoryId);
	    }else {
	    	List<PostResponseDTO> allPost = posts.stream().map(post->this.modelMapper.map(post, PostResponseDTO.class)).collect(Collectors.toList());
	    	return allPost;
		}
		
	}

	@Override
	public CategoryResponseDTO getCategoryDetails(Integer postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId));
		int categoryId = post.getCategoryId();
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));;
		return this.modelMapper.map(category, CategoryResponseDTO.class);
}

	@Override
	public UserResponseDTO getUserDetails(Integer postId) {
		 Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		 int userId = post.getUserId();
		 User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		return this.modelMapper.map(user, UserResponseDTO.class);
	}

	
	@Override
	public List<PostResponseDTO> postByKeywords(String keywords) {
		return null;
	}}
