package com.aneesh.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aneesh.blog.entities.Category;
import com.aneesh.blog.exceptions.ResourceNotFoundException;
import com.aneesh.blog.payloads.CategoryDto;
import com.aneesh.blog.repositories.CategoryRepo;
import com.aneesh.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public CategoryDto createCategory(CategoryDto catDto) {
		Category cat = modelMapper.map(catDto, Category.class);
		
		Category savedCat =  categoryRepo.save(cat);
		
		return modelMapper.map(savedCat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> allCats = categoryRepo.findAll();
		List<CategoryDto> catDtos = allCats.stream().map(category->modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return catDtos;
	}

	@Override
	public CategoryDto getCategoryById(Integer catId) {
		Category cat = categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category","Id",catId));
		return modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto catDto,Integer catId) {
		Category cat = categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category","Id",catId));
		cat.setCategoryDes(catDto.getCategoryDes());
		cat.setCategoryTitle(catDto.getCategoryTitle());
		
		Category updatedCat = categoryRepo.save(cat);
		
		
		return modelMapper.map(updatedCat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer catId) {
		Category cat = categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category","Id",catId));
		categoryRepo.delete(cat);
	}

}
