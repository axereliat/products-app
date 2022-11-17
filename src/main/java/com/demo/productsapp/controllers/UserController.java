package com.demo.productsapp.controllers;

import com.demo.productsapp.domain.models.binding.UserRegisterBindingModel;
import com.demo.productsapp.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.demo.productsapp.common.Constants.PASSWORDS_MISMATCH_MESSAGE;
import static com.demo.productsapp.common.Constants.REQUIRED_FIELDS_MESSAGE;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login?logout";
    }

    @GetMapping("/login")
    public String login() {
        return "users/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("model", new UserRegisterBindingModel());

        return "users/register";
    }

    @PostMapping("/register")
    public String store(Model model, @Valid UserRegisterBindingModel bindingModel, BindingResult bindingResult) {
        if (bindingResult.getFieldErrorCount() > 0) {
            model.addAttribute("error", REQUIRED_FIELDS_MESSAGE);
            model.addAttribute("model", bindingModel);

            return "users/register";
        }

        boolean result = this.userService.register(bindingModel);

        if (!result) {
            model.addAttribute("error", PASSWORDS_MISMATCH_MESSAGE);
            model.addAttribute("model", bindingModel);

            return "users/register";
        }

        return "redirect:/";
    }
}
