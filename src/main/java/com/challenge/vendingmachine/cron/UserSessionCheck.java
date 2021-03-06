package com.challenge.vendingmachine.cron;

import com.challenge.vendingmachine.model.UserSession;
import com.challenge.vendingmachine.service.UserSessionService;
import com.challenge.vendingmachine.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class UserSessionCheck {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSessionCheck.class);

    @Autowired
    private UserSessionService userSessionService;

    @Autowired
    private JwtUtil jwtUtil;

    @Scheduled(fixedDelay = (10 * 60 * 1000))
    public void updateExpiredUserSession() {
        LOGGER.info("Scheduled update task start. Time : " + new Date());

        List<UserSession> userSessionList = userSessionService.findByActiveTrue();

        List<UserSession> expiredSessions = new ArrayList<>();

        for (UserSession session : userSessionList) {
            if (jwtUtil.isTokenExpired(session.getCurrentToken())) {
                session.setActive(false);
                expiredSessions.add(session);
            }
        }
        if (!expiredSessions.isEmpty()) {
            LOGGER.info("update of expired session. Count : " + expiredSessions.size());
            userSessionService.saveAll(expiredSessions);
        }
        LOGGER.info("Scheduled update task finish. Time : " + new Date());
    }
}
