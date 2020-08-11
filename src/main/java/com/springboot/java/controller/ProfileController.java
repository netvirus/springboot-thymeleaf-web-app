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
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    private UserService userService;

    @GetMapping(name = "/admin/profiles")
    public String showProfiles(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        model.addAttribute("userName", "Welcome " + user.getUserName() + "/" + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        model.addAttribute("adminMessage","Content Available Only for Users with Admin Role");
        List<Profile> profiles = new ArrayList<>();
        profileRepository.findAll().forEach(profiles::add);
        model.addAttribute("profiles", profiles);
        return "profiles";
    }

    @GetMapping("/admin/profile/showForm")
    public String profileForm(Profile profile) {
        return "/admin/add-profile";
    }

}
