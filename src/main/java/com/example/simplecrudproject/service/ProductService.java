package com.example.simplecrudproject.service;

import com.example.simplecrudproject.model.Product;

import java.util.List;

public interface ProductService {
    public abstract void createProduct(Product product);

    public abstract List<Product> getProducts();

    public abstract Product getProductById(int id);

    public abstract List<Product> getProductByName(String keyword);

    public abstract List<Product> getProductByPrice(Double price);

    public abstract List<Product> getProductByNameAndPrice(String keyword, Double price);

    public abstract void updateProduct(Product product);

    public abstract void deleteProduct(int id);

    public abstract boolean doesProductExist(int id);


}
