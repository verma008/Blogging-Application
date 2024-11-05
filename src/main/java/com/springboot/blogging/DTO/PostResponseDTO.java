package com.springboot.blogging.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDTO {
	
	
	private int postId;
	
	private String postTitle;
	private String content;
	private String imageName;
	private String addedDate;

}
