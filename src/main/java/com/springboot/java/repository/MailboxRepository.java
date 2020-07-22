package com.springboot.java.repository;

import com.springboot.java.entity.Mailbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailboxRepository extends JpaRepository<Mailbox, Integer> {

}
