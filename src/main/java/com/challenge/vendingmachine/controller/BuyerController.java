package com.challenge.vendingmachine.controller;

import com.challenge.vendingmachine.exception.EntityNotExistException;
import com.challenge.vendingmachine.model.Product;
import com.challenge.vendingmachine.security.VMUserDetails;
import com.challenge.vendingmachine.service.BuyerService;
import com.challenge.vendingmachine.service.ProductService;
import com.challenge.vendingmachine.service.UserService;
import com.challenge.vendingmachine.model.dto.BuyRequest;
import com.challenge.vendingmachine.model.dto.BuyResponse;
import com.challenge.vendingmachine.model.dto.Coin;
import com.challenge.vendingmachine.model.dto.UserDTO;
import com.challenge.vendingmachine.utils.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@PreAuthorize("hasAuthority('BUYER')")
public class BuyerController {

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping(value = "/deposit", consumes = "application/json")
    public ResponseEntity<UserDTO> addDeposit(
            @AuthenticationPrincipal VMUserDetails principal,
            @Valid @RequestBody Coin coin) {

        com.challenge.vendingmachine.model.User user = userService.findByUsername(principal.getUsername());

        return ResponseEntity.ok().body(userMapper.toDTO(buyerService.deposit(user, coin)));

    }

    @PostMapping(value = "/buy", consumes = "application/json")
    public ResponseEntity<BuyResponse> buyProduct(
            @AuthenticationPrincipal VMUserDetails principal,
            @Valid @RequestBody BuyRequest buyRequest) {

        com.challenge.vendingmachine.model.User user = userService.findByUsername(principal.getUsername());

        Product product = productService.findById(buyRequest.getProductId());
        if (product == null) {
            throw new EntityNotExistException("Invalid product id: " + buyRequest.getProductId());
        }

        if (buyRequest.getQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }

        return ResponseEntity.ok().body(buyerService.buyProduct(user, product, buyRequest.getQuantity()));

    }

    @GetMapping("/reset")
    public ResponseEntity<UserDTO> resetDeposit(
            @AuthenticationPrincipal VMUserDetails principal) {

        com.challenge.vendingmachine.model.User user = userService.findByUsername(principal.getUsername());

        return ResponseEntity.ok().body(userMapper.toDTO(buyerService.reset(user)));

    }
}
