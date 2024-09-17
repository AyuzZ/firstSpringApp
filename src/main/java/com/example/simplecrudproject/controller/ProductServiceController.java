package com.example.simplecrudproject.controller;

import com.example.simplecrudproject.exception.ProductNotFoundException;
import com.example.simplecrudproject.model.Product;
import com.example.simplecrudproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductServiceController {
    @Autowired
    private ProductService productService;

    @GetMapping("/create")
    public String createProductForm(Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        return "products/create";
    }

    @PostMapping({"","/"})
    public String createProduct(@ModelAttribute("product") Product product){
        product = productService.createProduct(product);
        return "redirect:/products";
    }

    @GetMapping({"", "/"})
    public String getProducts(Model model, @Param("keyword") String keyword, @Param("price") Double price) {
        if(keyword == null && price == null){
            List<Product> productList = productService.getProducts();
            model.addAttribute("productList", productList);
            return "products/index";
        } else if (keyword != null && price == null) {
            List<Product> productList = productService.getProductByName(keyword);
            model.addAttribute("productList", productList);
            return "products/index";
        } else if (keyword == null) {
            List<Product> productList = productService.getProductByPrice(price);
            model.addAttribute("productList", productList);
            return "products/index";
        } else{
            List<Product> productList = productService.getProductByNameAndPrice(keyword, price);
            model.addAttribute("productList", productList);
            return "products/index";
        }

    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable int id, Model model){
        boolean doesProductExist = productService.doesProductExist(id);
        if(doesProductExist){
            Product product = productService.getProductById(id);
            List<Product> productList = new ArrayList<>();
            productList.add(product);
            model.addAttribute("productList", productList);
            return "products/index";
        }else{
            throw new ProductNotFoundException();
        }
    }



    @GetMapping("/edit/{id}")
    public String updateProductForm(@PathVariable int id, Model model){
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "products/edit";
    }

    @PostMapping("/{id}")
    public String updateProduct(@PathVariable int id, @ModelAttribute("product") Product product, Model model){
        productService.updateProduct(product);
        return "redirect:/products";
    }

    //Exception not needed here
    @GetMapping("/delete")
    public String deleteProduct(@RequestParam int id) {
        boolean doesProductExist = productService.doesProductExist(id);
        if (doesProductExist){
            productService.deleteProduct(id);
            return "redirect:/products";
        }else{
            throw new ProductNotFoundException();
        }
    }

}
