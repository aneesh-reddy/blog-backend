package com.aneesh.blog.services;

import java.util.List;

import com.aneesh.blog.payloads.PostDto;
import com.aneesh.blog.payloads.PostResponse;

public interface PostService {

	//create
	PostDto createPost(PostDto postDto,Integer userID,Integer catId);
	
	//update
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//get all posts
	PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	// get single post
	PostDto getPostById(Integer postId);
	
	// get all post by user
	List<PostDto> getPostsByUser(Integer userId);
	
	//get all post by category
	List<PostDto> getPostsByCategory(Integer catId);
	
	//search posts
	List<PostDto> searchPosts(String keyword);
	
}
