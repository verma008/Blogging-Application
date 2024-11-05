package com.springboot.blogging.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.blogging.DTO.CategoryRequestDTO;
import com.springboot.blogging.DTO.CategoryResponseDTO;
import com.springboot.blogging.DTO.CommonResponse;
import com.springboot.blogging.entities.Category;
import com.springboot.blogging.entities.Post;
import com.springboot.blogging.exceptions.AlreadyExistException;
import com.springboot.blogging.exceptions.ApiMessageException;
import com.springboot.blogging.exceptions.ResourceNotFoundException;
import com.springboot.blogging.repository1.CategoryRepository;
import com.springboot.blogging.repository1.PostRepository;
import com.springboot.blogging.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	 
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public CommonResponse createCategory(CategoryRequestDTO categoryDto) {
		
		
	    Category dbcategory=this.categoryRepository.findByCategoryTitle(categoryDto.getCategoryTitle());
	    Category category=this.dtoToCategory(categoryDto);
	    CommonResponse response=new CommonResponse();
	    try {
	    	  if(dbcategory!=null)
	  	    {
	  	    	throw new AlreadyExistException("This category is already exist. Please create another category");
	  	    }
	    	  else{
	    		  this.categoryRepository.save(category);
	    		  response.setMessage("Category created successfully");
	    	  }
	    }
	    catch(AlreadyExistException e)
	    {
	    	e.printStackTrace();
	    	response.setMessage("This category is already exist. Please create another category");
	    }catch(Exception e)
	    {
	    	e.printStackTrace();
	    	response.setMessage("Unknown error occur connect with tech team...");
	    }
		return response;
	}
	
	
	
	private Category dtoToCategory(CategoryRequestDTO categoryDto)
	{
		Category category=this.modelMapper.map(categoryDto,Category.class);
		return category;
	}



	@Override
	public List<CategoryResponseDTO> getAllCategory(Integer skip, Integer limit) {
		
		// Adding Pagination
		 Pageable pageable=PageRequest.of(skip, limit);
		
		 List<Category> allCategory= this.categoryRepository.findAll(pageable).getContent();
		 List<CategoryResponseDTO> categoryDto = allCategory.stream().map((category)->this.modelMapper.map(category, CategoryResponseDTO.class)).collect(Collectors.toList());
		 return categoryDto; 
	}



	@Override
	public CategoryResponseDTO getSingleCategory(Integer categoryId) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		
		return this.modelMapper.map(category, CategoryResponseDTO.class);
	}



	/* category can only be update or delete if there is no any post exists for that category : instead of deleting permanent deactivate the account only */
	@Override
	public CommonResponse deleteCategory(Integer categoryId) {
		
		Category deletedCategory=this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));	
	
		List<Post> post = this.postRepository.findByCategoryId(categoryId);
		if(post.isEmpty())
		{
			throw new ApiMessageException("This category doesn't have any post.");
		}else {
		
		this.postRepository.deleteAll(post);
		this.categoryRepository.delete(deletedCategory);
	    return new CommonResponse("Category deleted successfully");
	}}


	// category can only be update or delete if there is no any post exists for that category
	@Override
	public CommonResponse updateCategory(CategoryRequestDTO categoryRequestDTO, Integer categoryId) {
		
		Category byId = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		List<Post> post = this.postRepository.findByCategoryId(categoryId);
		if(post.isEmpty())
		{
			byId.setCategoryTitle(categoryRequestDTO.getCategoryTitle());
			byId.setCategoryDescription(categoryRequestDTO.getCategoryDescription());
			this.categoryRepository.save(byId);
			return new CommonResponse("Category updated successfully");
		}else {
			throw new ApiMessageException("Can't update this category. Please delete post associated the this category first");
		}
	}	
}
