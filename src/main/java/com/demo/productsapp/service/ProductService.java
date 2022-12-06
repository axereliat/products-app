package com.demo.productsapp.service;

import com.demo.productsapp.common.exceptions.ProductNotFoundException;
import com.demo.productsapp.domain.entities.Product;
import com.demo.productsapp.domain.entities.User;
import com.demo.productsapp.domain.models.binding.ProductBindingModel;
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

    public void store(Principal principal, ProductBindingModel bindingModel) {
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

    public Product getById(Integer id) {
        return this.productRepository.findById(id).orElse(null);
    }

    public void update(Principal principal, ProductBindingModel bindingModel, Integer id) {
        Product product = this.getById(id);

        product.setName(bindingModel.getName());
        product.setPrice(bindingModel.getPrice());
        product.setQuantity(bindingModel.getQuantity());

        User user = this.userRepository.findByUsername(principal.getName());
        product.setPublisher(user);

        this.productRepository.save(product);
    }

    public void destroy(Integer id) {
        if (!this.productRepository.existsById(id)) {
            throw new ProductNotFoundException();
        }

        this.productRepository.deleteById(id);
    }
}
