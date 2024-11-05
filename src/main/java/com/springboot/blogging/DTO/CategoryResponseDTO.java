package com.springboot.blogging.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryResponseDTO {
	
	private int categoryId;
	
	private String categoryTitle;
	private String categoryDescription;

}