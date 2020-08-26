package com.springboot.java.controller;

import com.springboot.java.entity.Profile;
import com.springboot.java.entity.User;
import com.springboot.java.repository.ProfileRepository;
import com.springboot.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ProfileController {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    private UserService userService;

    @GetMapping(name = "/admin/list-profiles")
    public String showProfiles(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        model.addAttribute("userName", "Welcome " + user.getUserName() + "/" + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        model.addAttribute("adminMessage","Content Available Only for Users with Admin Role");
        model.addAttribute("profiles", this.profileRepository.findAll());
        return "/admin/profiles";
    }

    @GetMapping("/admin/profile/showForm")
    public String profileForm() {
        return "/admin/add-profile";
    }

    @PostMapping("/admin/addProfile")
    public String addProfile(@Valid Profile profile, BindingResult result) {
        if (result.hasErrors()) {
            return "/admin/add-profile";
        }
        this.profileRepository.save(profile);
        return "redirect:/admin/list-profiles";
    }
}
