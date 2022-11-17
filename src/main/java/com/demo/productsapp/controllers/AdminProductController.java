package com.demo.productsapp.controllers;

import com.demo.productsapp.domain.models.binding.ProductCreateBindingModel;
import com.demo.productsapp.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/create")
    public String create() {
        return "products/create";
    }

    @PostMapping("/create")
    public String store(Model model, Principal principal, @Valid ProductCreateBindingModel bindingModel,
                        BindingResult bindingResult) {
        if (bindingResult.getFieldErrorCount() > 0) {
            model.addAttribute("error", bindingResult.getAllErrors().get(0).getDefaultMessage());
            model.addAttribute("model", bindingModel);

            return "products/create";
        }

        this.productService.store(principal, bindingModel);

        return "redirect:/";
    }
}
