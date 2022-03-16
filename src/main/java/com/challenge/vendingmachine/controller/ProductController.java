package com.challenge.vendingmachine.controller;

import com.challenge.vendingmachine.exception.EntityNotExistException;
import com.challenge.vendingmachine.model.Product;
import com.challenge.vendingmachine.model.User;
import com.challenge.vendingmachine.security.VMUserDetails;
import com.challenge.vendingmachine.service.ProductService;
import com.challenge.vendingmachine.model.dto.ProductDTO;
import com.challenge.vendingmachine.service.UserService;
import com.challenge.vendingmachine.utils.mapper.ProductMapper;
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
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('SELLER')")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<ProductDTO> create(@AuthenticationPrincipal VMUserDetails principal,
                                             @Valid @RequestBody ProductDTO product) {

        Product toSave = productMapper.toEntity(product);
        User user = userService.findByUsername(principal.getUsername());
        toSave.setSeller(user);

        Product p = productService.create(toSave);
        return ResponseEntity.ok(productMapper.toDTO(p));
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<ProductDTO> update(@AuthenticationPrincipal VMUserDetails principal,
                                             @PathVariable("id") Long id, @Valid @RequestBody ProductDTO product) {

        if (id == null || productService.findById(id) == null)
            throw new EntityNotExistException("Invalid product id : " + id);

        Product p = productService.findById(id);
        User user = userService.findByUsername(principal.getUsername());

        if (p.getSeller() != null && !p.getSeller().getId().equals(user.getId()))
            throw new IllegalArgumentException("Seller not authorized on product id : " + id);

        Product saved = productService.update(id, productMapper.toEntity(product));
        return ResponseEntity.ok(productMapper.toDTO(saved));
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @DeleteMapping("/{id}")
    public void delete(@AuthenticationPrincipal VMUserDetails principal, @PathVariable("id") Long id) {

        if (id == null || productService.findById(id) == null)
            throw new EntityNotExistException("Invalid product id : " + id);

        Product p = productService.findById(id);
        User user = userService.findByUsername(principal.getUsername());

        if (p.getSeller() != null && !p.getSeller().getId().equals(user.getId()))
            throw new IllegalArgumentException("Seller not authorized on product id : " + id);

        productService.delete(id);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
        return ResponseEntity.ok(productService.findAll().stream()
                .filter(Objects::nonNull)
                .map(productMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<ProductDTO>> findAllPaged(Pageable pageable) {
        return ResponseEntity.ok(productService.findAllPaged(pageable).map(productMapper::toDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productMapper.toDTO(productService.findById(id)));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductDTO> findByProductName(@PathVariable("name") String name) {
        return ResponseEntity.ok(productMapper.toDTO(productService.findByProductName(name)));
    }
}
