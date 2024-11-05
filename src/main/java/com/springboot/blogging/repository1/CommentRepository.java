package com.springboot.blogging.repository1;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.blogging.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer>{

	List<Comment> findByUserId(Integer userId);

}
