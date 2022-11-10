package com.demo.productsapp.service;

import com.demo.productsapp.domain.entities.Role;
import com.demo.productsapp.repository.RoleRepository;
import org.springframework.stereotype.Service;

import static com.demo.productsapp.common.Constants.ADMIN_ROLE;
import static com.demo.productsapp.common.Constants.USER_ROLE;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void storeDefault() {
        if (!this.roleRepository.findAll().isEmpty()) {
            return;
        }

        Role adminRole = Role.builder()
                .authority(ADMIN_ROLE)
                .build();
        Role userRole = Role.builder()
                .authority(USER_ROLE)
                .build();

        this.roleRepository.save(adminRole);
        this.roleRepository.save(userRole);
    }
}
