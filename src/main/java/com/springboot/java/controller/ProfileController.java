package com.springboot.java.controller;

import com.springboot.java.entity.Mailbox;
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
import java.util.ArrayList;
import java.util.List;

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
        List<Profile> profiles = new ArrayList<>();
        profileRepository.findAll().forEach(profiles::add);
        model.addAttribute("profiles", profiles);
        return "/admin/profiles";
    }

    @GetMapping("/admin/profile/showForm")
    public String profileForm(Profile profile) {
        return "/admin/add-profile";
    }

    @PostMapping("admin/addProfile")
    public String addMail(@Valid Profile profile, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/admin/add-profile";
        }
        profileRepository.save(profile);
        return "redirect:/admin/list-profiles";
    }
}
