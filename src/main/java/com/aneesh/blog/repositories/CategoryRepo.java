package com.aneesh.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aneesh.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer>{

}
