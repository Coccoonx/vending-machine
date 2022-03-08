package com.challenge.vendingmachine.controller;

import com.challenge.vendingmachine.model.Product;
import com.challenge.vendingmachine.service.ProductService;
import com.challenge.vendingmachine.model.dto.ProductDTO;
import com.challenge.vendingmachine.utils.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('SELLER')")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductDTO product) {
        Product p = productService.create(productMapper.toEntity(product));
        return ResponseEntity.ok(productMapper.toDTO(p));
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<ProductDTO> update(@PathVariable("id") Long id, @Valid @RequestBody ProductDTO product) {
        Product p = productService.update(id, productMapper.toEntity( product));
        return ResponseEntity.ok(productMapper.toDTO(p));
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
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
