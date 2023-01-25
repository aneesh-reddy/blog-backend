package com.aneesh.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.aneesh.blog.payloads.CommentDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class PostDto {

	private String title;
	
	private String content;
	
	private String imageName;
	
	private Date addedDate;	
	
	private UserDto user;
	
	private CategoryDto category;
	
	private Set<CommentDto> comments = new HashSet<>();
	
	
	

}
