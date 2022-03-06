package com.challenge.vendingmachine.controller;

import com.challenge.vendingmachine.model.Role;
import com.challenge.vendingmachine.security.VMUserDetails;
import com.challenge.vendingmachine.service.DepositService;
import com.challenge.vendingmachine.service.UserService;
import com.challenge.vendingmachine.service.dto.Coin;
import com.challenge.vendingmachine.service.dto.UserDTO;
import com.challenge.vendingmachine.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/deposit")
@PreAuthorize("hasAuthority('BUYER')")
public class DepositController {

    @Autowired
    private DepositService depositService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> addDeposit(
            @AuthenticationPrincipal VMUserDetails principal,
            @Valid @RequestBody Coin coin){

        com.challenge.vendingmachine.model.User user = userService.findByUsername(principal.getUsername());

        return ResponseEntity.ok().body(userMapper.toDTO(depositService.deposit(user, coin)));

    }
}
