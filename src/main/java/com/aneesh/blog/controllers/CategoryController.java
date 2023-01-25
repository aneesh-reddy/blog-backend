package com.aneesh.blog.controllers;

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

import com.aneesh.blog.payloads.ApiResponse;
import com.aneesh.blog.payloads.CategoryDto;
import com.aneesh.blog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories()
	{
		List<CategoryDto> catDtos = categoryService.getAllCategories();
		return  new ResponseEntity<List<CategoryDto>>(catDtos,HttpStatus.OK);
	}
	
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto>  getCategoryById(@PathVariable Integer catId)
	{
		CategoryDto catDto = categoryService.getCategoryById(catId);
		return new ResponseEntity<CategoryDto>(catDto,HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto catDto)
	{
		CategoryDto createdCatDto = categoryService.createCategory(catDto);
		return new ResponseEntity<CategoryDto>(createdCatDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@PathVariable Integer catId,@RequestBody CategoryDto catDto)
	{
		CategoryDto updatedCatDto = categoryService.updateCategory(catDto,catId);
		return new ResponseEntity<CategoryDto>(updatedCatDto,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId)
	{
		categoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponse> (new ApiResponse("Deleted Category Successfully",true),HttpStatus.OK);
	}

}
