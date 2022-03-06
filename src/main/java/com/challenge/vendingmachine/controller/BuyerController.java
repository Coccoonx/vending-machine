package com.challenge.vendingmachine.controller;

import com.challenge.vendingmachine.security.VMUserDetails;
import com.challenge.vendingmachine.service.BuyerService;
import com.challenge.vendingmachine.service.UserService;
import com.challenge.vendingmachine.service.dto.Coin;
import com.challenge.vendingmachine.service.dto.UserDTO;
import com.challenge.vendingmachine.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@PreAuthorize("hasAuthority('BUYER')")
public class BuyerController {

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping(value = "/deposit", consumes = "application/json")
    public ResponseEntity<UserDTO> addDeposit(
            @AuthenticationPrincipal VMUserDetails principal,
            @Valid @RequestBody Coin coin) {

        com.challenge.vendingmachine.model.User user = userService.findByUsername(principal.getUsername());

        return ResponseEntity.ok().body(userMapper.toDTO(buyerService.deposit(user, coin)));

    }

    @GetMapping("/reset")
    public ResponseEntity<UserDTO> resetDeposit(
            @AuthenticationPrincipal VMUserDetails principal) {

        com.challenge.vendingmachine.model.User user = userService.findByUsername(principal.getUsername());

        return ResponseEntity.ok().body(userMapper.toDTO(buyerService.reset(user)));

    }
}
