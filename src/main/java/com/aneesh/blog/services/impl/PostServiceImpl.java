package com.aneesh.blog.services.impl;

import java.util.Date;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.aneesh.blog.entities.Category;
import com.aneesh.blog.entities.Post;
import com.aneesh.blog.entities.User;
import com.aneesh.blog.exceptions.ResourceNotFoundException;
import com.aneesh.blog.payloads.PostDto;
import com.aneesh.blog.payloads.PostResponse;
import com.aneesh.blog.repositories.CategoryRepo;
import com.aneesh.blog.repositories.PostRepo;
import com.aneesh.blog.repositories.UserRepo;
import com.aneesh.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userID, Integer catId) {
        
		User user = this.userRepo.findById(userID).orElseThrow(()->new ResourceNotFoundException("User","Id", userID));
		
		Category cat = categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category","Id",catId));
		
		Post post = modelMapper.map(postDto,Post.class);
		
		post.setAddedDate(new Date());
		post.setUser(modelMapper.map(user,User.class));
		post.setCategory(modelMapper.map(cat, Category.class));
		post.setImageName("default.png");
		
		Post newPost = postRepo.save(post);
		
		return modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		  Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
		  post.setTitle(postDto.getTitle());
		  post.setContent(postDto.getContent());
		  post.setImageName(postDto.getImageName());
		  
		  Post updatedPost = postRepo.save(post);
		  
		return modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		  Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
		  postRepo.delete(post);
		  
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort =null;
		if(sortDir.equalsIgnoreCase("des"))
		{
			sort=Sort.by(sortBy).descending();
		}
		else
		{
			sort= Sort.by(sortBy).ascending();
		}
		Pageable p =  PageRequest.of(pageNumber,pageSize,sort);
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> posts = pagePost.getContent();
		List<PostDto> postDtos = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
	    Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
	    
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id", userId));
		List<Post> posts= postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer catId) {
		Category cat = categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category","Id",catId));
		List<Post> posts= postRepo.findByCategory(cat);
		List<PostDto> postDtos = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts= postRepo.searchByTitle("%"+keyword+"%");
		List<PostDto> postDtos = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
