package com.dange.tanmay.controller;

import com.dange.tanmay.FeatureToggleApplication;
import com.dange.tanmay.dao.User;
import com.dange.tanmay.dao.ValidateCodeDao;
import com.dange.tanmay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    public UserService service;

    @Autowired
    InMemoryUserDetailsManager userDetailsManager;


    @GetMapping("/admin/users")
    private List<User> getAllUsers()
    {
        return service.getAllUsers();
    }


    @GetMapping("/admin/user/{id}")
    private User getUser(@PathVariable("id") int id)
    {
        return service.getUserById(id);
    }


    @GetMapping("/admin/deleteUser/{id}")
    private String deleteUser(@PathVariable("id") int id)
    {
        service.delete(id);
        return  "successful";
    }

    @PostMapping("/admin/createUser")
    private String saveStudent(@ModelAttribute("user") @RequestBody User user)
    {
        service.saveOrUpdate(user);
        userDetailsManager.createUser(user.castUserToUserDetails());
        return "successful";
    }

    @PostMapping("/admin/force_enable")
    public void forceEnable(@ModelAttribute("allUserlist") @RequestBody ArrayList<User> users) {
        System.out.println("=====Sanjay:Force_Enable:" + users);
    }

    @GetMapping("/admin/manage-users")
    public String viewHomePage(Model model) {
        model.addAttribute("allUserlist", service.getAllUsers());
        return "manage-users";
    }

    @GetMapping("/login")
    public String login(Model model) {
        User user = new User();
        user.setUsername("sanjay");
        model.addAttribute("user", user);
        System.out.println("=====Sanjay:Login:" + model);
        return "login";
    }

    @GetMapping("/user")
    public String viewUserPage(Principal principal, Model model) {
        System.out.println("=====Sanjay:/user:principal:" + principal);

        //User user = ((User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal());
        String userName = ((User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUsername();
        User user = service.getUserByUsername(userName);

        model.addAttribute("user", user);

        return "user";
    }

    @GetMapping("/admin/create-user")
    public String createUserPage(Model model) {
        User user = new User();
        user.setUsername("Sanjay");
        user.setMfaEnabled(false);
        model.addAttribute("user", user);
        return "create-user";
    }


    @GetMapping("/admin/register-mfa/{username}")
    public String registerMFA(@PathVariable String username, Model model) {
        User user = new User();
        user.setUsername(username);

        model.addAttribute("user", user);

        ValidateCodeDao dto = new ValidateCodeDao();
        dto.setUsername(username);
        model.addAttribute("dto", dto);

        return "register-mfa";
    }


    @GetMapping("/validate/otp/{username}")
    public String viewHomePage(@PathVariable String username, Model model) {
        ValidateCodeDao dto = new ValidateCodeDao();
        dto.setUsername(username);
        model.addAttribute("dto", dto);
        return "validate-otp";
    }




    @GetMapping("/error")
    public String error(Model model) {
        return "error";
    }

}
