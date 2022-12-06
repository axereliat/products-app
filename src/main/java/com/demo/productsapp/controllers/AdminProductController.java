package com.demo.productsapp.controllers;

import com.demo.productsapp.common.exceptions.ProductNotFoundException;
import com.demo.productsapp.domain.entities.Product;
import com.demo.productsapp.domain.models.binding.ProductBindingModel;
import com.demo.productsapp.domain.models.view.ResponseViewModel;
import com.demo.productsapp.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    private static final String PRODUCT_DESTROY_MESSAGE = "Product was successfully deleted";

    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product could not be found";

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/create")
    public String create() {
        return "products/create";
    }

    @PostMapping("/create")
    public String store(Model model, Principal principal, @Valid ProductBindingModel bindingModel,
                        BindingResult bindingResult) {
        if (bindingResult.getFieldErrorCount() > 0) {
            model.addAttribute("error", bindingResult.getAllErrors().get(0).getDefaultMessage());
            model.addAttribute("model", bindingModel);

            return "products/create";
        }

        this.productService.store(principal, bindingModel);

        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id) {
        Product product = this.productService.getById(id);

        if (product == null) {
            return "error/404";
        }

        model.addAttribute("productId", product.getId());
        model.addAttribute("model", product);

        return "products/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(Model model, @PathVariable Integer id, Principal principal, @Valid ProductBindingModel bindingModel, BindingResult bindingResult) {
        Product product = this.productService.getById(id);

        if (product == null) {
            return "error/404";
        }

        if (bindingResult.getFieldErrorCount() > 0) {
            model.addAttribute("error", bindingResult.getAllErrors().get(0).getDefaultMessage());
            model.addAttribute("productId", product.getId());
            model.addAttribute("model", bindingModel);

            return "products/edit";
        }

        this.productService.update(principal, bindingModel, id);

        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<ResponseViewModel> destroy(@PathVariable Integer id) {
        try {
            this.productService.destroy(id);

            ResponseViewModel responseViewModel = new ResponseViewModel(PRODUCT_DESTROY_MESSAGE);

            return ResponseEntity.ok(responseViewModel);
        } catch (ProductNotFoundException exception) {
            ResponseViewModel responseViewModel = new ResponseViewModel(PRODUCT_NOT_FOUND_MESSAGE);

            return ResponseEntity.badRequest().body(responseViewModel);
        }
    }
}
