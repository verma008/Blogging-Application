package com.springboot.blogging.repository1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.blogging.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

	Category findByCategoryTitle(String categoryDto);

}
