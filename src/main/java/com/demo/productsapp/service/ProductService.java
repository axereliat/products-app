package com.demo.productsapp.service;

import com.demo.productsapp.domain.entities.Product;
import com.demo.productsapp.domain.entities.User;
import com.demo.productsapp.domain.models.binding.ProductCreateBindingModel;
import com.demo.productsapp.repository.ProductRepository;
import com.demo.productsapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public void store(Principal principal, ProductCreateBindingModel bindingModel) {
        User user = this.userRepository.findByUsername(principal.getName());

        Product product = Product.builder()
                .name(bindingModel.getName())
                .price(bindingModel.getPrice())
                .quantity(bindingModel.getQuantity())
                .publisher(user)
                .build();

        this.productRepository.save(product);
    }

    public List<Product> getAll() {
        return this.productRepository.findAll();
    }
}
