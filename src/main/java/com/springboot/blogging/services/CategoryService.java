package com.springboot.blogging.services;

import java.util.List;

import com.springboot.blogging.DTO.CategoryRequestDTO;
import com.springboot.blogging.DTO.CategoryResponseDTO;
import com.springboot.blogging.DTO.CommonResponse;
import com.springboot.blogging.entities.Category;

public interface CategoryService {

	CommonResponse createCategory(CategoryRequestDTO categoryDto);

	List<CategoryResponseDTO> getAllCategory(Integer skip, Integer limit);

	CategoryResponseDTO getSingleCategory(Integer categoryId);

	CommonResponse deleteCategory(Integer categoryId);

	CommonResponse updateCategory(CategoryRequestDTO categoryRequestDTO, Integer categoryId);

}
