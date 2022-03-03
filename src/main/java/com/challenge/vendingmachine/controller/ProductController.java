package com.challenge.vendingmachine.controller;

import com.challenge.vendingmachine.model.Product;
import com.challenge.vendingmachine.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        Product p = productService.create(product);
        return ResponseEntity.ok(p);
    }

    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<Product> update(@PathVariable("id") Long id, @Valid @RequestBody Product product) {
        return ResponseEntity.ok(productService.update(id, product));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        productService.delete(id);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Product>> findAllPaged(Pageable pageable) {
        return ResponseEntity.ok(productService.findAllPaged(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Product> findByProductName(@PathVariable("name") String name) {
        return ResponseEntity.ok(productService.findByProductName(name));
    }
}
