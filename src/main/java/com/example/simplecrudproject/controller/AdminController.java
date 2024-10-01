package com.example.simplecrudproject.controller;

import com.example.simplecrudproject.exception.ProductNotFoundException;
import com.example.simplecrudproject.model.Exceptions;
import com.example.simplecrudproject.model.Product;
import com.example.simplecrudproject.model.User;
import com.example.simplecrudproject.service.ProductService;
import com.example.simplecrudproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class AdminController {
    
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    //Get all products into admin home page
    @GetMapping({"/admin", "/admin/", "admin/admin", "admin/admin/"})
    public String getProductsAndUsers(Model model, @Param("keyword") String keyword, @Param("price") Double price) {
        if(keyword == null && price == null){
            List<Product> productList = productService.getProducts();
            model.addAttribute("productList", productList);
        } else if (keyword != null && price == null) {
            List<Product> productList = productService.getProductByName(keyword);
            model.addAttribute("productList", productList);
        } else if (keyword == null) {
            List<Product> productList = productService.getProductByPrice(price);
            model.addAttribute("productList", productList);
        } else{
            List<Product> productList = productService.getProductByNameAndPrice(keyword, price);
            model.addAttribute("productList", productList);
        }
        List<User> userList = null;
        try{
            userList = userService.getUsers();
        }catch(Exception e){
            Exceptions exception = new Exceptions(e.toString());
            return "/exception";
        }
        model.addAttribute("userList", userList);
        return "admin/admin";
    }

    //Get a product with id in url
    @GetMapping("/products/{id}")
    public String getProductById(@PathVariable int id, Model model){
        boolean doesProductExist = productService.doesProductExist(id);
        if(doesProductExist){
            Product product = null;
            try {
                product = productService.getProductById(id);
            } catch (Exception e) {
                Exceptions exception = new Exceptions(e.toString());
                return "/exception";
            }
            List<Product> productList = new ArrayList<>();
            productList.add(product);
            model.addAttribute("productList", productList);
            return "admin/admin";
        }else{
            throw new ProductNotFoundException("The requested page doesn't exist.");
        }
    }

    @GetMapping("/products/createProduct")
    public String createProductForm(Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        return "products/createProduct";
    }

    @PostMapping({"/products","/products/"})
    public String createProduct(@ModelAttribute("product") Product product){
        try {
            productService.createProduct(product);
        } catch (Exception e) {
            Exceptions exception = new Exceptions(e.toString());
            return "/exception";
        }
        return "redirect:/admin";
    }

    @GetMapping("/products/editProduct/{id}")
    public String updateProductForm(@PathVariable int id, Model model){
        Product product = null;
        try {
            product = productService.getProductById(id);
        } catch (Exception e) {
            Exceptions exception = new Exceptions(e.toString());
            return "/exception";
        }
        model.addAttribute("product", product);
        return "products/editProduct";
    }

    @PostMapping("/products/{id}")
    public String updateProduct(@PathVariable int id, @ModelAttribute("product") Product product, Model model){
        try {
            productService.updateProduct(product);
        } catch (Exception e) {
            Exceptions exception = new Exceptions(e.toString());
            return "/exception";
        }
        return "redirect:/admin";
    }

    //Exception not needed here
    @GetMapping("/products/deleteProduct")
    public String deleteProduct(@RequestParam int id) {
        boolean doesProductExist = productService.doesProductExist(id);
        if (doesProductExist){
            try {
                productService.deleteProduct(id);
            } catch (Exception e) {
                Exceptions exception = new Exceptions(e.toString());
                return "/exception";
            }
            return "redirect:/admin";
        }else{
            throw new ProductNotFoundException("Product Not Found");
        }
    }
}
