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
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blogging.DTO.CategoryRequestDTO;
import com.springboot.blogging.DTO.CategoryResponseDTO;
import com.springboot.blogging.DTO.CommonResponse;
import com.springboot.blogging.entities.Category;
import com.springboot.blogging.services.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController{
	
	@Autowired
	private CategoryService categoryService; 
	
	
	// create category
	@PostMapping("/create")
	public ResponseEntity<CommonResponse> createCategory(@RequestBody CategoryRequestDTO categoryDto){
		CommonResponse categoryCreated=this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CommonResponse>(categoryCreated, HttpStatus.CREATED);
	}
	
	
	
	// API to get all category
	@GetMapping("/{skip}/{limit}")
	public List<CategoryResponseDTO> getAllCategory(@PathVariable Integer skip, @PathVariable Integer limit)
	{
		return this.categoryService.getAllCategory(skip,limit);
	}
	
	
	// API to get single category
	@GetMapping("/{categoryId}")
	public CategoryResponseDTO getSingleCategory(@PathVariable Integer categoryId)
	{
		return this.categoryService.getSingleCategory(categoryId);
	}
	
	
	
	// API to delete category
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<CommonResponse> deleteCategory(@PathVariable Integer categoryId)
	{
		CommonResponse result=this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<CommonResponse>(result, HttpStatus.NOT_FOUND);
	}
	
	
	// API to update category
	@PutMapping("/{categoryId}")
	public ResponseEntity<CommonResponse> updateCategory(@RequestBody CategoryRequestDTO categoryRequestDTO, @PathVariable Integer categoryId)
	{
		CommonResponse updatedCategory = this.categoryService.updateCategory(categoryRequestDTO,categoryId);
		return new ResponseEntity<CommonResponse>(updatedCategory,HttpStatus.OK);
	}
	

}
