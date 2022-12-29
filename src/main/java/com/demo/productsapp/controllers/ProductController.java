package com.demo.productsapp.controllers;

import com.demo.productsapp.domain.entities.Product;
import com.demo.productsapp.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String list(Model model) {
        List<Product> products = this.productService.getAll();

        model.addAttribute("products", products);

        return "products/list";
    }
}
