package com.springboot.blogging.repository1;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.blogging.entities.Post;


@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{

	List<Post> findByUserId(Integer userId);

	List<Post> findByCategoryId(Integer categoryId);

	List<Post> findByPostTitleContaining(String keyword);

	List<Post> findByUserIdAndCategoryId(Integer userId, Integer categoryId);



}
