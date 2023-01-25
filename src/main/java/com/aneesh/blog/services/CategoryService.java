package com.aneesh.blog.services;

import java.util.List;

import com.aneesh.blog.payloads.CategoryDto;

public interface CategoryService {
	
	//create
	CategoryDto createCategory(CategoryDto catDto);

	
	//read
	List<CategoryDto> getAllCategories();
	
	CategoryDto getCategoryById(Integer catId);
	
	//update
	CategoryDto updateCategory(CategoryDto catDto,Integer catId);
	
	//delete
	void deleteCategory(Integer catId);

}
