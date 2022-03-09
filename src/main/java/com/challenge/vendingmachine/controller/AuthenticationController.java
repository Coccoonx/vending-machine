package com.challenge.vendingmachine.controller;

import com.challenge.vendingmachine.exception.UserSessionExistException;
import com.challenge.vendingmachine.model.User;
import com.challenge.vendingmachine.model.UserSession;
import com.challenge.vendingmachine.model.dto.AuthRequest;
import com.challenge.vendingmachine.model.dto.JwtResponse;
import com.challenge.vendingmachine.service.UserService;
import com.challenge.vendingmachine.service.UserSessionService;
import com.challenge.vendingmachine.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserSessionService userSessionService;

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest authRequest) throws BadCredentialsException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        User user = userService.findByUsername(authRequest.getUsername());

        UserSession userSession = userSessionService.findByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(authRequest.getUsername());
        if (userSession == null) {
            userSession = new UserSession();
            userSession.setUserId(user.getId());
            userSession.setUsername(user.getUsername());
            userSession.setCurrentToken(token);
            userSession.setSessionExpiration(jwtTokenUtil.extractExpiration(token));
            userSession.setActive(true);
            userSessionService.save(userSession);
        } else {
            if (userSession.isActive()) {
                authentication.setAuthenticated(false);
                throw new UserSessionExistException("There is already an active session using your account");
            } else {
                userSession.setCurrentToken(token);
                userSession.setSessionExpiration(jwtTokenUtil.extractExpiration(token));
                userSession.setActive(true);
                userSessionService.save(userSession);
            }
        }

        LOGGER.info("Login of user {}", authRequest.getUsername());
        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);

    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(@RequestBody @Valid AuthRequest authRequest) throws BadCredentialsException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        Map<String, String> response = new HashMap<>();

        UserSession userSession = userSessionService.findByUsername(authRequest.getUsername());
        if (userSession != null) {
            userSession.setActive(false);
            userSessionService.save(userSession);
            authentication.setAuthenticated(false);
            LOGGER.info("Logout of user {}", authRequest.getUsername());
            response.put("message", "All sessions cleared for user : "+authRequest.getUsername());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put("message", "Verify your credentials");
        return ResponseEntity.badRequest().body(response);

    }
}
