package com.springboot.blogging.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDTO {
	
	private int id;
	private String name;
	private String password;
	private String email;
	private String about;
	private String role;
}
