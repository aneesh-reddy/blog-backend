package com.aneesh.blog.services.impl;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aneesh.blog.entities.Comment;
import com.aneesh.blog.entities.Post;
import com.aneesh.blog.exceptions.ResourceNotFoundException;
import com.aneesh.blog.payloads.CommentDto;
import com.aneesh.blog.repositories.CommentRepo;
import com.aneesh.blog.repositories.PostRepo;
import com.aneesh.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PostRepo postRepo;	

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
	 Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
	 Comment comment = modelMapper.map(commentDto, Comment.class);
	 Comment savedComment = commentRepo.save(comment);
	 Set<Comment> comments = post.getComments();
	 comments.add(savedComment);
	 post.setComments(comments);
	 Post updatedPost = postRepo.save(post);
		return modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Id",commentId));
        commentRepo.delete(comment);
	}

}
