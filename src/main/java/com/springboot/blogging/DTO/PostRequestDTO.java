package com.springboot.blogging.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDTO {
	
	
	private int postId;
	
	private String postTitle;
	private String contant;
	private String fileName;
	private String addedDate;
	private int categoryId;
	private int userId;
}
