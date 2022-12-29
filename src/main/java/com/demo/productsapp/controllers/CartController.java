package com.demo.productsapp.controllers;

import com.demo.productsapp.domain.entities.Product;
import com.demo.productsapp.domain.models.binding.AddToCartBindingModel;
import com.demo.productsapp.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class CartController {

    private static final String CART_KEY_NAME = "cart";
    private static final String FLASH_MESSAGE_NAME = "msg";
    private static final String FLASH_MESSAGE = "Product was successfully added to cart";

    private final ProductRepository productRepository;

    public CartController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping("/carts/products/{id}")
    public String store(@PathVariable Integer id, AddToCartBindingModel bindingModel, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        List<Product> cartList = new ArrayList<>();
        if (request.getSession().getAttribute(CART_KEY_NAME) != null) {
            cartList = (List<Product>) request.getSession().getAttribute(CART_KEY_NAME);
        }

        request.getSession().setAttribute(CART_KEY_NAME, cartList);

        redirectAttributes.addFlashAttribute(FLASH_MESSAGE_NAME, FLASH_MESSAGE);

        return "redirect:/";
    }

    @GetMapping("/my-cart")
    public String myCart(Model model, HttpServletRequest request) {
        List<Product> products = new ArrayList<>();
        if (request.getSession().getAttribute(CART_KEY_NAME) != null) {
            Integer[] productIds = (Integer[]) request.getSession().getAttribute(CART_KEY_NAME);
            products = this.productRepository.findByIdIn(productIds);
        }

        model.addAttribute("products", products);

        return "users/my-cart";
    }
}
