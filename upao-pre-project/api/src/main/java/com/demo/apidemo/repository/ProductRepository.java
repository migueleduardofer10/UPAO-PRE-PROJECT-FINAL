package com.demo.apidemo.repository;

import com.demo.apidemo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    //Usando Query (JPQL) Java Persistence Query Language
    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    List<Product> findByNameLike(String name);
    //Usando Query Method (Keywords)
    List<Product> findByNameContainingIgnoreCase(String name);
    boolean existsByName(String name);
}

