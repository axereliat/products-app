package com.demo.productsapp.domain.models.binding;

import lombok.Data;

@Data
public class UserRegisterBindingModel {

    private String username;

    private String password;

    private String confirmPassword;
}
