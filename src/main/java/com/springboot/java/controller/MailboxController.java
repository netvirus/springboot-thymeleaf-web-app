package com.springboot.java.controller;

import com.springboot.java.entity.Mailbox;
import com.springboot.java.repository.MailboxRepository;
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
@RequestMapping("/")
public class MailboxController {

    @Autowired
    private MailboxRepository mailboxRepository;

    @GetMapping("/showForm")
    public String mailForm(Mailbox mailbox) {

        return "add-mailbox";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Mailbox mailbox = this.mailboxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mailbox id: " + id));
        model.addAttribute("mailbox", mailbox);
        return "update-mailbox";
    }

    @GetMapping("/list")
    public String mailList(Model model) {
        model.addAttribute("mailbox", this.mailboxRepository.findAll());
        return "index";
    }

    @PostMapping("/add")
    public String addMail(@Validated Mailbox mailbox, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-mailbox";
        }

        this.mailboxRepository.save(mailbox);
        return "redirect:list";
    }

    @GetMapping("/delete/{id}")
    public String deleteMailbox(@PathVariable("id") int id, Model model) {
        Mailbox mailbox = this.mailboxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid mailbox id: " + id));

        this.mailboxRepository.delete(mailbox);
        model.addAttribute("mailbox", this.mailboxRepository.findAll());
        return "index";
    }

    @GetMapping("/changestate/{id}")
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
        return "index";
    }

    @PostMapping("/update/{id}")
    public String updateMailbox(@PathVariable("id") int id, @Validated Mailbox mailbox, BindingResult result, Model model) {
        if (result.hasErrors()) {
            mailbox.setId(id);
            return "update-mailbox";
        }
        this.mailboxRepository.save(mailbox);
        model.addAttribute("mailbox", this.mailboxRepository.findAll());
        return "index";
    }
}
