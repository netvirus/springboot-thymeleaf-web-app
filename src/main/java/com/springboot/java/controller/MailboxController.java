package com.springboot.java.controller;

import com.springboot.java.entity.Mailbox;
import com.springboot.java.entity.User;
import com.springboot.java.repository.MailboxRepository;
import com.springboot.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MailboxController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailboxRepository mailboxRepository;

    @GetMapping("/admin/showForm")
    public String mailForm(Mailbox mailbox) {
        return "/admin/add-mailbox";
    }

    @GetMapping("/admin/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Mailbox mailbox = this.mailboxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mailbox id: " + id));
        model.addAttribute("mailbox", mailbox);
        return "/admin/update-mailbox";
    }

    @GetMapping("/admin/list")
    public String mailList(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        model.addAttribute("userName", "Welcome " + user.getUserName() + "/" + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        model.addAttribute("adminMessage","Content Available Only for Users with Admin Role");
        model.addAttribute("mailbox", this.mailboxRepository.findAll());
        return "/admin/index";
    }

    @PostMapping("admin/add")
    public String addMail(@Validated Mailbox mailbox, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/admin/add-mailbox";
        }

        this.mailboxRepository.save(mailbox);
        return "redirect:/admin/list";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteMailbox(@PathVariable("id") int id, Model model) {
        Mailbox mailbox = this.mailboxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mailbox id: " + id));

        this.mailboxRepository.delete(mailbox);
        model.addAttribute("mailbox", this.mailboxRepository.findAll());
        return "/admin/index";
    }

    @GetMapping("/admin/changestate/{id}")
    public String blockMailbox(@PathVariable("id") int id, Model model) {
        Mailbox mailbox = this.mailboxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mailbox id: " + id));
        if(mailbox.isActive())
        {
            mailbox.setActive(false);
        }
        else
        {
            mailbox.setActive(true);
        }
        this.mailboxRepository.save(mailbox);
        model.addAttribute("mailbox", this.mailboxRepository.findAll());
        return "/admin/index";
    }

    @PostMapping("admin/update/{id}")
    public String updateMailbox(@PathVariable("id") int id, @Valid Mailbox mailbox, BindingResult result, Model model) {
        if (result.hasErrors()) {
            mailbox.setId(id);
            return "/admin/update-mailbox";
        }
        this.mailboxRepository.save(mailbox);
        model.addAttribute("mailbox", this.mailboxRepository.findAll());
        return "/admin/index";
    }
}
