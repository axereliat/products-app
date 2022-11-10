package com.demo.productsapp;

import com.demo.productsapp.domain.models.binding.UserRegisterBindingModel;
import com.demo.productsapp.service.RoleService;
import com.demo.productsapp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.demo.productsapp.common.Constants.ADMIN_PASSWORD;
import static com.demo.productsapp.common.Constants.ADMIN_USERNAME;

@SpringBootApplication
public class ProductsAppApplication {

    private final UserService userService;

    private final RoleService roleService;

    public ProductsAppApplication(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProductsAppApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(){
        return args -> {
            this.roleService.storeDefault();
            this.userService.registerAdmin();
        };
    }
}
