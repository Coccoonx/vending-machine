package com.challenge.vendingmachine.controller;

import com.challenge.vendingmachine.model.Product;
import com.challenge.vendingmachine.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Product> create(@Valid  @RequestBody Product product) {

        Product p = productService.create(product);
        System.out.println(p);
        return ResponseEntity.ok(p);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<Product> update(@Valid @RequestBody Product product) {
        return ResponseEntity.ok(productService.update(product));
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


    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(final ValidationException ex) {

        Map<String, String> errors = new HashMap<>();
        errors.put("message", ex.getLocalizedMessage());
        return errors;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidExceptions(final MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
