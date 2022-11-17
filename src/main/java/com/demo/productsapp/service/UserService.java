package com.demo.productsapp.service;

import com.demo.productsapp.domain.entities.Role;
import com.demo.productsapp.domain.entities.User;
import com.demo.productsapp.domain.models.binding.UserRegisterBindingModel;
import com.demo.productsapp.repository.RoleRepository;
import com.demo.productsapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.demo.productsapp.common.Constants.*;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public boolean register(UserRegisterBindingModel bindingModel) {
        if (!bindingModel.getPassword().equals(bindingModel.getConfirmPassword())) {
            return false;
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPass = passwordEncoder.encode(bindingModel.getPassword());
        Role role;
        if (this.userRepository.findAll().isEmpty()) {
            role = this.roleRepository.findByAuthority(ADMIN_ROLE);
        } else {
            role = this.roleRepository.findByAuthority(USER_ROLE);
        }

        User user = User.builder()
                .username(bindingModel.getUsername())
                .password(encryptedPass)
                .role(role)
                .build();

        this.userRepository.save(user);

        return true;
    }

    public void registerAdmin() {
        if (!this.userRepository.findAll().isEmpty()) {
            return;
        }

        UserRegisterBindingModel bindingModel = new UserRegisterBindingModel();
        bindingModel.setUsername(ADMIN_USERNAME);
        bindingModel.setPassword(ADMIN_PASSWORD);
        bindingModel.setConfirmPassword(ADMIN_PASSWORD);

        this.register(bindingModel);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!this.userRepository.existsByUsername(username)) {
            throw new UsernameNotFoundException("User with username " + username + " could not be found");
        }

        return this.userRepository.findByUsername(username);
    }
}
