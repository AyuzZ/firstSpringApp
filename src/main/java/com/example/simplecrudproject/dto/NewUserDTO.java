package com.example.simplecrudproject.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NewUserDTO {

    @Valid

    @NotNull(message = "Username is mandatory.")
    @Size(min = 4, max = 20, message = "Username must be 4 to 20 characters long.")
    private String username;

    @NotNull(message = "Password is mandatory.")
    @Size(min = 6, max = 20, message = "Password must be 6 to 20 characters long.")
    private String password;

    @NotNull(message = "Gender is mandatory.")
    @NotBlank(message = "Gender is mandatory.")
    private String gender;

    @NotNull(message = "Age is mandatory.")
    @NotBlank(message = "Age is mandatory.")
    private String age;

    public NewUserDTO(){
    }

    public @Valid @NotNull(message = "Username is mandatory.") String getUsername() {
        return username;
    }

    public void setUsername(@Valid @NotNull(message = "Username is mandatory.") String username) {
        this.username = username;
    }

    public @NotNull(message = "Password is mandatory.") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull(message = "Password is mandatory.") String password) {
        this.password = password;
    }

    public @NotNull(message = "Gender is mandatory.") String getGender() {
        return gender;
    }

    public void setGender(@NotNull(message = "Gender is mandatory.") String gender) {
        this.gender = gender;
    }

    public @NotNull(message = "Age is mandatory.") String getAge() {
        return age;
    }

    public void setAge(@NotNull(message = "Age is mandatory.") String age) {
        this.age = age;
    }
}
