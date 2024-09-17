package com.example.simplecrudproject.service.impl;

import com.example.simplecrudproject.model.Product;
import com.example.simplecrudproject.repository.ProductRepository;
import com.example.simplecrudproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getProducts() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product getProductById(int id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product product = optionalProduct.get();
        return product;
    }

    @Override
    public List<Product> getProductByName(String name) {
        List<Product> productList = productRepository.getSearchedProductByName(name);
        return productList;
    }

    @Override
    public List<Product> getProductByPrice(Double price) {
        Double lowerLimit = price - 100;
        Double upperLimit = price + 100;
        List<Product> productList = productRepository.getSearchedProductByPrice(lowerLimit, upperLimit);
        return productList;
    }

    @Override
    public List<Product> getProductByNameAndPrice(String keyword, Double price) {
        Double lowerLimit = price - 100;
        Double upperLimit = price + 100;
        List<Product> productList = productRepository.getSearchedProductByNameAndPrice(keyword, lowerLimit, upperLimit);
        return productList;
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public boolean doesProductExist(int id) {
        return productRepository.existsById(id);
    }
}
