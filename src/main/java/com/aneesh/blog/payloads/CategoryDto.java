package com.aneesh.blog.payloads;

import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor


public class CategoryDto {
	
	private int categoryId;
	
	@NotEmpty
	@Size(min=4,message="Minimum length of user name shoudl be 4")
	private String categoryTitle;
	@NotEmpty
	private String categoryDes;

}
