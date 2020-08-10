package com.springboot.java.controller;

import com.springboot.java.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    ProfileRepository profileRepository;

    @GetMapping(name = "/admin/profiles")
    public String showProfiles(Model model) {
        List<String> profiles = new ArrayList<>();
        profileRepository.findAll().forEach(profiles::add);
        model.addAttribute("profiles", profiles);
        return "profiles";
    }
}
