package com.demo.apidemo.repository;

import com.demo.apidemo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    //Usando Query Method (Keywords)
    boolean existsByName(String name);
}
