package com.springboot.java.repository;

import com.springboot.java.entity.Mailbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailboxRepository extends JpaRepository<Mailbox, Integer> {
    Mailbox findByLogin(String login);
}
