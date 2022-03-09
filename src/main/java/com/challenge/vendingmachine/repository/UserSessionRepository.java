package com.challenge.vendingmachine.repository;

import com.challenge.vendingmachine.model.UserSession;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserSessionRepository extends CrudRepository<UserSession, Long> {

    UserSession findByUsername(String username);

    List<UserSession> findByActiveTrue();
}
