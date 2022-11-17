package com.demo.productsapp.domain.models.binding;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserRegisterBindingModel {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;
}
