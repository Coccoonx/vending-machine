package com.challenge.vendingmachine.service;

import com.challenge.vendingmachine.model.UserSession;
import com.challenge.vendingmachine.repository.UserSessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSessionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSessionService.class);


    @Autowired
    UserSessionRepository userSessionRepository;


    public UserSession findbyId(Long id) {
        LOGGER.info("findbyId id: " + id);
        if (userSessionRepository.findById(id).isPresent())
            return userSessionRepository.findById(id).get();
        return null;
    }

    public UserSession findByUsername(String username) {
        LOGGER.info("findByUsername username: " + username);
        return userSessionRepository.findByUsername(username);
    }

    public UserSession save(UserSession userSession) {
        LOGGER.info("saving user session: " + userSession);
        return userSessionRepository.save(userSession);
    }

    public List<UserSession> findByActiveTrue() {
        LOGGER.info("findByActiveTrue");
        return userSessionRepository.findByActiveTrue();
    }

    public Iterable<UserSession> saveAll(List<UserSession> sessions) {
        LOGGER.info("saveAll");
        return userSessionRepository.saveAll(sessions);
    }

}
