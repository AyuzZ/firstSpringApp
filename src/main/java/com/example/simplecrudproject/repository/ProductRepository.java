package com.example.simplecrudproject.repository;

import com.example.simplecrudproject.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {

}
