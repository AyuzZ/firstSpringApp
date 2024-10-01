package com.example.simplecrudproject.controller;

import com.example.simplecrudproject.exception.UserNotFoundException;
import com.example.simplecrudproject.model.Exceptions;
import com.example.simplecrudproject.model.Product;
import com.example.simplecrudproject.model.User;
import com.example.simplecrudproject.service.ProductService;
import com.example.simplecrudproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    //Displaying products on the home page
    @GetMapping({"/products", "/products/"})
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

    //Create User Form
    @GetMapping("/users/createUser")
    public String createUserForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "users/createUser";
    }

    //Create User Method
    @PostMapping({"/users","/users/"})
    public String createUser(@ModelAttribute("user") User user){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptedPassword = encoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        try {
            userService.createUser(user);
        } catch (Exception e) {
            Exceptions exception = new Exceptions(e.toString());
            return "/exception";
        }
        return "redirect:products";
    }

    //Get user profile
    @GetMapping("users/profile")
    public String getUserByUsername(Model model){
        String username; //getting username from session
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        //After getting username
        boolean doesUserExist = userService.doesUsernameExist(username);
        if(doesUserExist){
            User user = null;
            try {
                user = userService.getUser(username);
            } catch (Exception e) {
                Exceptions exception = new Exceptions(e.toString());
                return "/exception";
            }
            List<User> userList = new ArrayList<>();
            userList.add(user);
            model.addAttribute("userList", userList);
            return "users/profile";
        }else{
            throw new UserNotFoundException("User Not Found.");
        }
    }

    //Update User Form
    @GetMapping("/users/updateUser/{username}")
    public String updateUserForm(@PathVariable String username, Model model){
        User user = null;
        try {
            user = userService.getUser(username);
        } catch (Exception e) {
            Exceptions exception = new Exceptions(e.toString());
            return "/exception";
        }
        model.addAttribute("user", user);
        return "users/updateUser";
    }

    //Update User Profile
    @PostMapping("/users/{username}")
    public String updateUser(@PathVariable String username, @ModelAttribute("user") User user, Model model){
        try {
            userService.updateUser(user);
        } catch (Exception e) {
            Exceptions exception = new Exceptions(e.toString());
            return "/exception";
        }
        return "redirect:/users/profile";
    }

//    //Delete User
//    @GetMapping("/users/delete")
//    public String deleteUser(@RequestParam String username) {
//        boolean doesUserExist = userService.doesUsernameExist(username);
//        if (doesUserExist){
//            userService.deleteUser(username);
//            return "redirect:/user";
//        }else{
//            throw new UserNotFoundException("User Not Found");
//        }
//    }

}
