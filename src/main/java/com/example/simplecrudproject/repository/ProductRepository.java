package com.example.simplecrudproject.repository;

import com.example.simplecrudproject.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    public List<Product> getSearchedProductByName(String keyword);

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN ?1 AND ?2")
    public List<Product> getSearchedProductByPrice(Double lowerLimit, Double upperLimit);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% AND p.price BETWEEN ?2 AND ?3")
    public List<Product> getSearchedProductByNameAndPrice(String keyword, Double lowerLimit, Double upperLimit);
}
