package com.aneesh.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aneesh.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment,Integer> {

}