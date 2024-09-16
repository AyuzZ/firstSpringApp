package com.example.simplecrudproject.service;

import com.example.simplecrudproject.model.Product;

import java.util.List;

public interface ProductService {
    public abstract Product createProduct(Product product);

    public abstract List<Product> getProducts();

    public abstract Product getProductById(int id);

    public abstract void updateProduct(Product product);

    public abstract void deleteProduct(int id);

    public abstract boolean doesProductExist(int id);

}
