package com.challenge.vendingmachine.controller;

import com.challenge.vendingmachine.exception.EntityNotExistException;
import com.challenge.vendingmachine.model.Product;
import com.challenge.vendingmachine.model.Purchase;
import com.challenge.vendingmachine.model.User;
import com.challenge.vendingmachine.model.dto.PurchaseDTO;
import com.challenge.vendingmachine.security.VMUserDetails;
import com.challenge.vendingmachine.service.ProductService;
import com.challenge.vendingmachine.service.PurchaseService;
import com.challenge.vendingmachine.service.UserService;
import com.challenge.vendingmachine.utils.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PurchaseMapper purchaseMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<PurchaseDTO> create(@Valid @RequestBody PurchaseDTO purchase) {
        Purchase p = purchaseService.create(purchaseMapper.toEntity(purchase));
        return ResponseEntity.ok(purchaseMapper.toDTO(p));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<PurchaseDTO> update(@PathVariable("id") Long id, @Valid @RequestBody PurchaseDTO purchase) {
        Purchase p = purchaseService.update(id, purchaseMapper.toEntity(purchase));
        return ResponseEntity.ok(purchaseMapper.toDTO(p));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        purchaseService.delete(id);
    }

    @GetMapping
    public ResponseEntity<List<PurchaseDTO>> findAll() {
        return ResponseEntity.ok(purchaseService.findAll().stream()
                .filter(Objects::nonNull)
                .map(purchaseMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/paged")
    public ResponseEntity<Page<PurchaseDTO>> findAllPaged(Pageable pageable) {
        return ResponseEntity.ok(purchaseService.findAllPaged(pageable).map(purchaseMapper::toDTO));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(purchaseMapper.toDTO(purchaseService.findById(id)));
    }

    @GetMapping("/own")
    public ResponseEntity<List<PurchaseDTO>> findByUsersPurchases(@AuthenticationPrincipal VMUserDetails principal) {
        User user = userService.findByUsername(principal.getUsername());

        return ResponseEntity.ok(purchaseService.findByBuyer(user).stream()
                .filter(Objects::nonNull)
                .map(purchaseMapper::toDTO)
                .collect(Collectors.toList()));
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user/{username}")
    public ResponseEntity<List<PurchaseDTO>> findByUserName(@PathVariable("username") String username) {
        User user = userService.findByUsername(username);
        if (user == null)
            throw new EntityNotExistException("Invalid Username: " + username);

        return ResponseEntity.ok(purchaseService.findByBuyer(user).stream()
                .filter(Objects::nonNull)
                .map(purchaseMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SELLER')")
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<PurchaseDTO>> findByProductId(@PathVariable("productId") Long productId) {
        Product product = productService.findById(productId);
        if (product == null)
            throw new EntityNotExistException("Invalid product Id: " + productId);

        return ResponseEntity.ok(purchaseService.findByProduct(product).stream()
                .filter(Objects::nonNull)
                .map(purchaseMapper::toDTO)
                .collect(Collectors.toList()));
    }
}
