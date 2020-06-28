package com.springboot.java.controller;

import com.springboot.java.entity.Mail;
import com.springboot.java.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mail/")
public class MailController {

    @Autowired
    private MailRepository mailRepository;

    @GetMapping("showForm")
    public String mailForm() {
        return "add-mail";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Mail mail = this.mailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mail id: " + id));
        model.addAttribute("mail", mail);
        return "update-mail";
    }

    @GetMapping("list")
    public String mailList(Model model) {
        model.addAttribute("mail", this.mailRepository.findAll());
        return "index";
    }

    @PostMapping("add")
    public String addMail(@Validated Mail mail, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-mail";
        }

        this.mailRepository.save(mail);
        return "redirect:list";
    }

    @GetMapping("update/{id}")
    public String updateMail(@PathVariable("id") int id, @Validated Mail mail, BindingResult result, Model model) {
        if (result.hasErrors()) {
            mail.setId(id);
            return "update-mail";
        }

        this.mailRepository.save(mail);
        model.addAttribute("mail", this.mailRepository.findAll());
        return "index";
    }
}
