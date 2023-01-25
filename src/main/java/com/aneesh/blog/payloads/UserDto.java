package com.aneesh.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
  
	private int id;

	private String name;
	@Email(message="Email id is not valid")
	private String email;
	@NotNull
	@Size(min=4,message="Minimum length of password should be 4")
	private String password;
	private String about;
}
